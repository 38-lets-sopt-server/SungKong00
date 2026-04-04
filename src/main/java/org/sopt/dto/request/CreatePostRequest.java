package org.sopt.dto.request;

public class CreatePostRequest {
    String title;
    String content;
    String author;

    public CreatePostRequest(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}