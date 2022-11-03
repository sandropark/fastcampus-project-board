package fastcampus.projectboard.dto.request;

import fastcampus.projectboard.dto.ArticleCommentDto;
import fastcampus.projectboard.dto.UserAccountDto;

public record ArticleCommentRequest(
        Long articleId,
        String content
) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public static ArticleCommentRequest of() {
        return new ArticleCommentRequest(null, null);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );

    }

}
