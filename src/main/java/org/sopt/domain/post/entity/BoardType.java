package org.sopt.domain.post.entity;

public enum BoardType {
    FREE("자유게시판"),
    HOT("인기게시판"),
    SECRET("비밀게시판");

    private final String description;

    BoardType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
