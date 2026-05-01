package org.sopt.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.entity.BoardType;
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.response.CreatePostResponse;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.domain.post.exception.PostNotFoundException;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.sopt.domain.post.validator.PostValidator.validateCreatePostRequest;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    // CREATE
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {

        validateCreatePostRequest(request.title(), request.content());

        LocalDateTime createdAt = java.time.LocalDateTime.now();

        User user = userService.getUserById(request.userId());
        Post post = new Post(request.boardType(), request.title(), request.content(), user, createdAt);

        return new PostResponse( postRepository.save(post));
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
                 .orElseThrow(() -> new PostNotFoundException(id));
            return new PostResponse(post);
    }

    // UPDATE
    @Transactional
    public void updatePost(Long id, UpdatePostRequest updateRequest) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        validateCreatePostRequest(updateRequest.title(), updateRequest.content());

        post.update(updateRequest.title(), updateRequest.content());
    }

    // DELETE
    @Transactional
    public void deletePost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        postRepository.delete(post);
    }
}