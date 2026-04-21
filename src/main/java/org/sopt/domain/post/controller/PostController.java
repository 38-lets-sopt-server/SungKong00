package org.sopt.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.global.common.response.ApiResponse;
import org.sopt.domain.post.dto.response.CreatePostResponse;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.global.exception.CustomException;
import org.sopt.domain.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    @RequestMapping("/create")
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
       CreatePostResponse response = postService.createPost(request);
         return ResponseEntity.success().body(ApiResponse.success(response, "게시글이 성공적으로 생성되었습니다."));
    }

    // GET /posts 📝 과제
    @RequestMapping("/all")
    public ApiResponse<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts();
        return ApiResponse.success(response, "모든 게시글이 성공적으로 조회되었습니다.");
    }

    // GET /posts/{id} 📝 과제
    @RequestMapping("/detail")
    public ApiResponse<PostResponse> getPost(Long id) {
      try {
            return ApiResponse.success(postService.getPost(id));
        } catch (CustomException e) {
            return ApiResponse.failure(e);
        }
    }

    // PUT /posts/{id} 📝 과제
    @RequestMapping("/update")
    public ApiResponse<Void> updatePost(Long id, String newTitle, String newContent) {
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success();
        } catch (CustomException e) {
            return ApiResponse.failure(e);
        }
    }

    // DELETE /posts/{id} 📝 과제
    @RequestMapping("/delete")
    public ApiResponse<Void> deletePost(Long id) {
       try {
           postService.deletePost(id);
           return ApiResponse.success();
        } catch (CustomException e) {
            return ApiResponse.failure(e);
       }
    }
}
