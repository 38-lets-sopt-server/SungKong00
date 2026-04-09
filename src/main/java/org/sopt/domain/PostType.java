package org.sopt.domain;

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
