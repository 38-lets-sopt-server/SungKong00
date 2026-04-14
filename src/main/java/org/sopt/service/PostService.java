package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.CustomException;
import org.sopt.exception.post.PostNotFoundException;
import org.sopt.repository.PostRepository;

import java.util.List;

import static org.sopt.validator.PostValidator.validateCreatePostRequest;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    private Long nextId = 1L;

    // CREATE ✅ 같이 구현
    // 글쓰기 화면에서 "완료" 버튼을 누르면 이 메서드가 호출돼요
    public CreatePostResponse createPost(CreatePostRequest request) {
        try {
            validateCreatePostRequest(request.getTitle(), request.getContent());

            String createdAt = java.time.LocalDateTime.now().toString();
            Post post = new Post(nextId++, request.getTitle(), request.getContent(), request.getAuthor(), createdAt);

            postRepository.save(post);

        } catch (CustomException e) {
            return new CreatePostResponse(null, "🚫 " + e.getMessage());
        }
        return new CreatePostResponse(nextId - 1, "✅ 게시글 등록 완료!");
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
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }
            return new PostResponse(post);
    }

    // UPDATE 📝 과제
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    public void updatePost(Long id, String newTitle, String newContent) {

        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }
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