package org.sopt.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.entity.BoardType;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.domain.post.dto.response.CreatePostResponse;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.global.common.response.GlobalSuccessCode;
import org.sopt.domain.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 API", description = "게시글 생성, 조회, 수정, 삭제 관련 API")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 내용 (제목/내용 누락 또는 길이 초과)")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        return BaseResponse.success(GlobalSuccessCode.CREATED, response);
    }

    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 또는 특정 게시판의 게시글 목록을 페이징하여 조회합니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(
            @Parameter(description = "게시판 종류 (생략 시 전체 조회)", example = "FREE")
            @RequestParam(required = false) BoardType boardType,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "페이지 당 데이터 수", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponse> response = postService.getAllPosts(boardType, page, size);
        return BaseResponse.success(GlobalSuccessCode.SUCCESS, response);
    }

    @Operation(summary = "게시글 단건 조회", description = "ID를 통해 특정 게시글을 상세 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "조회할 게시글의 ID", example = "1")
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return BaseResponse.success(GlobalSuccessCode.SUCCESS, response);
    }

    @Operation(summary = "게시글 수정", description = "특정 게시글의 제목과 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 내용 (제목/내용 누락 또는 길이 초과)"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @Parameter(description = "수정할 게시글의 ID", example = "1")
            @PathVariable Long id,

            @RequestBody UpdatePostRequest updateRequest
    ) {
        postService.updatePost(id, updateRequest);
        return BaseResponse.success(GlobalSuccessCode.UPDATED);
    }

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
            @ApiResponse(responseCode = "410", description = "이미 삭제된 게시글")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @Parameter(description = "삭제할 게시글의 ID", example = "1")
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return BaseResponse.success(GlobalSuccessCode.DELETED);
    }
}