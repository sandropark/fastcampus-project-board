package fastcampus.projectboard.dto;

public record ArticleCommentUpdateDto(String content) {

    public static ArticleCommentUpdateDto of(String content) {
        return new ArticleCommentUpdateDto(content);
    }
}
