package org.sopt.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.entity.BoardType;
import org.sopt.global.common.response.GlobalErrorCode;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.global.common.response.GlobalSuccessCode;
import org.sopt.domain.post.service.PostService;
import org.sopt.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 API", description = "게시글 생성, 조회, 수정, 삭제 관련 API")
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "게시글 생성 성공")
    @PostMapping
    public ResponseEntity<BaseResponse<PostResponse>> createPost(
            Authentication authentication,
            @Valid @RequestBody CreatePostRequest request
    ) {
        // 인증된 userId로 작성자 지정
        PostResponse response = postService.createPost(getAuthenticatedUserId(authentication), request);
        return BaseResponse.success(GlobalSuccessCode.CREATED, response);
    }

    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 또는 특정 게시판의 게시글 목록을 페이징하여 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(
            @Parameter(description = "게시판 종류 (생략 시 전체 조회)", example = "FREE")
            @RequestParam(required = false) BoardType boardType,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "page는 0 이상이어야 합니다.") int page,

            @Parameter(description = "페이지 당 데이터 수", example = "10")
            @RequestParam(defaultValue = "10") @Positive(message = "size는 1 이상이어야 합니다.") int size
    ) {
        List<PostResponse> response = postService.getAllPosts(boardType, page, size);
        return BaseResponse.success(GlobalSuccessCode.SUCCESS, response);
    }

    @Operation(summary = "게시글 단건 조회", description = "ID를 통해 특정 게시글을 상세 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "조회할 게시글의 ID", example = "1")
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return BaseResponse.success(GlobalSuccessCode.SUCCESS, response);
    }

    @Operation(summary = "게시글 수정", description = "특정 게시글의 제목과 내용을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(
            Authentication authentication,
            @Parameter(description = "수정할 게시글의 ID", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody UpdatePostRequest updateRequest
    ) {
        // 수정 요청자 검증은 Service에서 처리
        PostResponse post = postService.updatePost(getAuthenticatedUserId(authentication), id, updateRequest);
        return BaseResponse.success(GlobalSuccessCode.UPDATED, post);
    }

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            Authentication authentication,
            @Parameter(description = "삭제할 게시글의 ID", example = "1")
            @PathVariable @Min(value = 1, message = "id는 1 이상이어야 합니다.") Long id
    ) {
        // 삭제 요청자 검증은 Service에서 처리
        postService.deletePost(getAuthenticatedUserId(authentication), id);
        return BaseResponse.success(GlobalSuccessCode.DELETED);
    }

    private Long getAuthenticatedUserId(Authentication authentication) {
        // JwtAuthFilter가 넣은 회원 id 꺼내기
        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
        }

        try {
            return Long.parseLong(authentication.getName());
        } catch (NumberFormatException e) {
            throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
        }
    }
}
