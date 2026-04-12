package org.sopt.dto.request;

public class CreatePostRequest {
    private final String title;
    private final String content;
    private final String author;

    public CreatePostRequest(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}