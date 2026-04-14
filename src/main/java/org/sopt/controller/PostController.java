package org.sopt.controller;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.CustomException;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public ApiResponse<CreatePostResponse> createPost(CreatePostRequest request) {
        try {
            return ApiResponse.success(postService.createPost(request));
        } catch (CustomException e) {
            return ApiResponse.failure(e);
        }
    }

    // GET /posts 📝 과제
    public ApiResponse<List<PostResponse>> getAllPosts() {
        try {
            List<PostResponse> posts = postService.getAllPosts();
            return ApiResponse.success(posts);
        } catch (CustomException e) {
            return ApiResponse.failure(e);
        }
    }

    // GET /posts/{id} 📝 과제
    public ApiResponse<PostResponse> getPost(Long id) {
      try {
            return ApiResponse.success(postService.getPost(id));
        } catch (CustomException e) {
            return ApiResponse.failure(e);
        }
    }

    // PUT /posts/{id} 📝 과제
    public ApiResponse<Void> updatePost(Long id, String newTitle, String newContent) {
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success();
        } catch (CustomException e) {
            return ApiResponse.failure(e);
        }
    }

    // DELETE /posts/{id} 📝 과제
    public ApiResponse<Void> deletePost(Long id) {
       try {
           postService.deletePost(id);
           return ApiResponse.success();
        } catch (CustomException e) {
            return ApiResponse.failure(e);
       }
    }
}
