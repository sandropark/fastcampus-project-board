package fastcampus.projectboard.dto;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        String content,
        String createdBy,
        String modifiedBy,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleCommentDto of(String content, String createdBy, String modifiedBy, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ArticleCommentDto(content, createdBy, modifiedBy, createdAt, modifiedAt);
    }
}
