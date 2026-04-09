package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.post.PostNotFoundException;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    private Long nextId = 1L;

    // CREATE ✅ 같이 구현
    // 글쓰기 화면에서 "완료" 버튼을 누르면 이 메서드가 호출돼요
    public CreatePostResponse createPost(CreatePostRequest request) {
        try {
            // 글쓰기 화면설계서: 제목은 필수, 최대 50자
            if (request.title == null || request.title.isBlank()) {
                throw new IllegalArgumentException("제목은 필수입니다!");
            }
            if (request.content == null || request.content.isBlank()) {
                throw new IllegalArgumentException("내용은 필수입니다!");
            }
            String createdAt = java.time.LocalDateTime.now().toString();
            Post post = new Post(nextId++, request.title, request.content, request.author, createdAt);
            postRepository.save(post);
            System.out.println("✅ 게시글 등록 완료!");
        } catch (IllegalArgumentException e) {
            System.out.println("🚫 입력 오류: " + e.getMessage());
            return new CreatePostResponse(null, "🚫 " + e.getMessage());
        }
        return new CreatePostResponse(nextId - 1, "✅ 게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    // 자유게시판 목록 화면에서 호출돼요
    public List<PostResponse> getAllPosts() {

        if (postRepository.findAll().isEmpty()) {
            throw new PostNotFoundException();
        }
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .toList();
    }

    // READ - 단건 📝 과제
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    public PostResponse getPost(Long id) {
        if (id == null || id <= 0) {
            throw new PostNotFoundException();
        } else {
            return new PostResponse(postRepository.findById(id));
        }
    }

    // UPDATE 📝 과제
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    public void updatePost(Long id, String newTitle, String newContent) {

            Post post = postRepository.findById(id);
            if (post != null) {
                post.update(newTitle, newContent);
                System.out.println("✅ 게시글 수정 완료!");
            } else {
               throw new PostNotFoundException();
            }
    }

    // DELETE 📝 과제
    // 게시글 상세에서 삭제를 누르면 호출돼요
    public void deletePost(Long id) {

        if (postRepository.deleteById(id)) {
            System.out.println("✅ 게시글 삭제 완료!");
        } else {
            throw new PostNotFoundException();
        }
    }
}