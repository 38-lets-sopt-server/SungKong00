package org.sopt.global.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Validation 에러 상세 응답")
public record ValidationErrorResponse(

        @Schema(description = "필드별 에러 목록")
        List<FieldErrorDetail> errors
) {
    @Schema(description = "개별 필드 에러 상세")
    public record FieldErrorDetail(

            @Schema(description = "에러가 발생한 필드명", example = "title")
            String field,

            @Schema(description = "해당 필드의 에러 메시지 목록", example = "[\"제목은 필수입니다.\"]")
            List<String> messages
    ) {}
}
