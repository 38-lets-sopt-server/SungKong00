package org.sopt.domain.post.entity;

import jakarta.persistence.*;
import org.sopt.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 게시글 상세 화면 — 특정 게시글 식별용

    @Enumerated(EnumType.STRING)
    private BoardType boardType; // 게시글 상세 화면 — 게시판 종류

    private String title;     // 목록, 상세, 글쓰기 화면 — 제목

    private String content;   // 목록(미리보기), 상세(전체) 화면 — 내용

    @ManyToOne(fetch = FetchType.LAZY)  // User : Post = 1 : N
    @JoinColumn(name = "user_id")       // post 테이블에 user_id FK 컬럼이 생겨요
    private User user;    // 목록, 상세 화면 — 글쓴이

    private LocalDateTime createdAt; // 목록, 상세 화면 — 작성 시각

    public Post(BoardType boardType, String title, String content, User user, LocalDateTime createdAt) {
        this.boardType = boardType;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Post() {

    }

    public Long getId() { return id; }
    public BoardType getBoardType() { return boardType; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getInfo() {
        return "[" + id + "] " + title + " - " + user + " (" + createdAt + ")\n" + content;
    }
}