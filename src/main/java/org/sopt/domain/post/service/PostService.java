package org.sopt.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.response.CreatePostResponse;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.domain.post.exception.PostNotFoundException;
import org.sopt.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.sopt.domain.post.validator.PostValidator.validateCreatePostRequest;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository = new PostRepository();

    private Long nextId = 1L;

    // CREATE ✅ 같이 구현
    // 글쓰기 화면에서 "완료" 버튼을 누르면 이 메서드가 호출돼요
    public CreatePostResponse createPost(CreatePostRequest request) {

        validateCreatePostRequest(request.title(), request.content());

        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(nextId++, request.title(), request.content(), request.author(), createdAt);

        postRepository.save(post);
        return new CreatePostResponse(post.getId());
    }



    // READ - 전체 📝 과제
    // 자유게시판 목록 화면에서 호출돼요
    public List<PostResponse> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostResponse::new)
                .toList();
    }

    // READ - 단건 📝 과제
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                 .orElseThrow(() -> new PostNotFoundException(id));
            return new PostResponse(post);
    }
    // UPDATE 📝 과제
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    public void updatePost(Long id, String newTitle, String newContent) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        validateCreatePostRequest(newTitle, newContent);

        post.update(newTitle, newContent);
    }

    // DELETE 📝 과제
    // 게시글 상세에서 삭제를 누르면 호출돼요
    public void deletePost(Long id) {

        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new PostNotFoundException();
        }
    }
}