package org.sopt.domain.post.entity;

import jakarta.persistence.*;
import org.sopt.domain.user.entity.User;
import org.sopt.global.common.entity.BaseTimeEntity;

@Entity
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(BoardType boardType, String title, String content, User user) {
        this.boardType = boardType;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Post() {

    }

    public Long getId() { return id; }
    public BoardType getBoardType() { return boardType; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }

    public boolean isWrittenBy(Long userId) {
        // 작성자 확인: 수정/삭제 권한 체크용
        return user != null && user.getId().equals(userId);
    }

    public void update(String title, String content) {
        // 게시글 내용 변경
        this.title = title;
        this.content = content;
    }

    public String getInfo() {
        return "[" + id + "] " + title + " - " + user + " (" + getCreatedAt() + ")\n" + content;
    }
}
