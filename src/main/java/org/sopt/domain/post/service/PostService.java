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
    private final PostRepository postRepository;

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {

        validateCreatePostRequest(request.title(), request.content());

        String createdAt = java.time.LocalDateTime.now().toString();

        Post post = new Post(request.title(), request.content(), request.author(), createdAt);

        Post savedPost = postRepository.save(post);

        return new CreatePostResponse(savedPost.getId());
    }


    // READ - 전체 목록
    public List<PostResponse> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostResponse::new)
                .toList();
    }

    // READ - 상세
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                 .orElseThrow(() -> new PostNotFoundException(id));
            return new PostResponse(post);
    }
    // UPDATE
    public void updatePost(Long id, String newTitle, String newContent) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        validateCreatePostRequest(newTitle, newContent);

        post.update(newTitle, newContent);
    }

    // DELETE
    public void deletePost(Long id) {

        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new PostNotFoundException();
        }
    }
}