package org.sopt.domain.post.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시판 종류 (FREE: 자유, HOT: 인기, SECRET: 비밀)")
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