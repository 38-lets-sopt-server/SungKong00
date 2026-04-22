package org.sopt.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.entity.BoardType;
import org.sopt.global.common.response.ApiResponse;
import org.sopt.domain.post.dto.response.CreatePostResponse;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.global.common.response.GlobalSuccessCode;
import org.sopt.domain.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // POST /posts
    @PostMapping
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
       CreatePostResponse response = postService.createPost(request);
         return ApiResponse.success(GlobalSuccessCode.CREATED, response);
    }

    // GET /posts (전체 조회/게시판 종류별 조회)
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(
            @RequestParam(required = false) BoardType boardType
    ) {
        List<PostResponse> response = postService.getAllPosts(boardType);
        return ApiResponse.success(GlobalSuccessCode.SUCCESS, response);
    }

    // GET /posts/{id} (단건 조회)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return ApiResponse.success(GlobalSuccessCode.SUCCESS, response);
    }

    // PUT /posts/{id} (수정)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest updateRequest
            ) {
        postService.updatePost(id, updateRequest);
        return ApiResponse.success(GlobalSuccessCode.UPDATED);
    }

    // DELETE /posts/{id} (삭제)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ApiResponse.success(GlobalSuccessCode.DLELETED);
    }
}
