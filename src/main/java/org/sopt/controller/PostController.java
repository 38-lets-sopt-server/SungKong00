package org.sopt.controller;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.CustomException;
import org.sopt.exception.post.PostNotFoundException;
import org.sopt.service.PostService;

import javax.swing.undo.CannotUndoException;
import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public CreatePostResponse createPost(CreatePostRequest request) {
        try {
            return postService.createPost(request);
        } catch (IllegalArgumentException e) {
            return new CreatePostResponse(null, "🚫 " + e.getMessage());
        }
    }

    // GET /posts 📝 과제
    public List<PostResponse> getAllPosts() {
        try {
            return postService.getAllPosts();
        } catch (CustomException e) {
            System.out.println("🚫 입력 오류: " + e.getMessage());
            return List.of();
        }
    }

    // GET /posts/{id} 📝 과제
    public PostResponse getPost(Long id) {
        try {
            return postService.getPost(id);
        } catch (CannotUndoException e) {
            System.out.println("🚫 입력 오류: " + e.getMessage());
            return null;
        }
    }

    // PUT /posts/{id} 📝 과제
    public void updatePost(Long id, String newTitle, String newContent) {
        try {
            postService.updatePost(id, newTitle, newContent);
        } catch (CustomException e) {
            System.out.println("🚫 입력 오류: " + e.getMessage());
        }
    }

    // DELETE /posts/{id} 📝 과제
    public void deletePost(Long id) {
        try {
            postService.deletePost(id);
        } catch (CustomException e) {
            System.out.println("🚫 입력 오류: " + e.getMessage());
        }
    }
}
