package org.sopt.validator;

import org.sopt.exception.post.PostInvalidContentException;

public class PostValidator {

    // 제목, 내용의 길이 제한 상수
    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_CONTENT_LENGTH = 2000;

    // 게시글 작성 시 제목과 내용의 유효성을 검증하는 메서드
    public static void validateCreatePostRequest(String title, String content) {
        // 제목과 내용이 규칙에 맞지 않으면 예외 발생
        if (title == null || title.isBlank()) {
            throw new PostInvalidContentException("제목은 필수입니다.");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new PostInvalidContentException("제목은 " + MAX_TITLE_LENGTH + "자 이내로 작성해주세요.");
        }

        if (content == null || content.isBlank()) {
            throw new PostInvalidContentException("내용은 필수입니다.");
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new PostInvalidContentException("내용은 " + MAX_CONTENT_LENGTH + "자 이내로 작성해주세요.");
        }
    }
}
