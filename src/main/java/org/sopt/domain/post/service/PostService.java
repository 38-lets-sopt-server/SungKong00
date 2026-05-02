package org.sopt.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.entity.BoardType;
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.domain.post.exception.PostErrorCode;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.user.service.UserService;
import org.sopt.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    // CREATE
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {

        User user = userService.getUserById(request.userId());
        Post post = new Post(request.boardType(), request.title(), request.content(), user);

        return new PostResponse(postRepository.save(post));
    }


    // READ - 전체 목록 /  게시판 종류별 조회
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(BoardType boardType, int page, int size) {

        List<Post> posts;

        if (boardType != null) {
            posts = postRepository.findByBoardType(boardType, page, size);
        } else {
            posts = postRepository.findAll(page, size);
        }

        return posts.stream()
                .map(PostResponse::new)
                .toList();
    }

    // READ - 상세
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                 .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다. id: " + id));
            return new PostResponse(post);
    }

    // UPDATE
    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest updateRequest) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다. id: " + id));


        post.update(updateRequest.title(), updateRequest.content());

        return new PostResponse(postRepository.save(post));  // JPA의 영속성 컨텍스트 덕분에 사실 save() 안 해도 업데이트는 되지만, 인메모리에서는 save() 해줘야 업데이트가 반영된다.
    }

    // DELETE
    @Transactional
    public void deletePost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다. id: " + id));

        postRepository.delete(post);
    }
}