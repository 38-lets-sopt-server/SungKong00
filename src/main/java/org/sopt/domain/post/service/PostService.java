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

    // 게시글 생성: 요청 userId가 아니라 인증된 userId 사용
    @Transactional
    public PostResponse createPost(Long userId, CreatePostRequest request) {

        User user = userService.getUserById(userId);
        Post post = new Post(request.boardType(), request.title(), request.content(), user);

        return new PostResponse(postRepository.save(post));
    }


    // 게시글 목록: boardType 있으면 필터링, 없으면 전체 조회
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

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                 .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다. id: " + id));
            return new PostResponse(post);
    }

    // 게시글 수정: 작성자만 가능
    @Transactional
    public PostResponse updatePost(Long userId, Long id, UpdatePostRequest updateRequest) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다. id: " + id));

        validateWriter(post, userId);

        post.update(updateRequest.title(), updateRequest.content());

        // JPA는 save 없이도 반영되지만, 인메모리 저장소 호환용
        return new PostResponse(postRepository.save(post));
    }

    // 게시글 삭제: 작성자만 가능
    @Transactional
    public void deletePost(Long userId, Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다. id: " + id));

        validateWriter(post, userId);

        postRepository.delete(post);
    }

    private void validateWriter(Post post, Long userId) {
        // 인증된 회원과 게시글 작성자 비교
        if (!post.isWrittenBy(userId)) {
            throw new CustomException(PostErrorCode.POST_FORBIDDEN);
        }
    }
}
