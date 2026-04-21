package org.sopt.domain.post.entity;

public enum PostType {
    QUESTION("질문"),
    GENERAL("일반");

    private final String description;

    PostType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
     }
}
