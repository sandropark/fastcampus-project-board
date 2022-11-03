package fastcampus.projectboard.dto;

import static org.springframework.util.StringUtils.hasText;

public record ArticleFormDto(
        String title,
        String content,
        String hashtag
) {

    public static ArticleFormDto of() {
        return new ArticleFormDto(null, null, null);
    }

    public static ArticleFormDto of(String title, String content, String hashtag) {
        return new ArticleFormDto(title, content, hashtag);
    }

    public static ArticleFormDto from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (hasText(nickname)) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleFormDto(
                dto.title(),
                dto.content(),
                dto.hashtag()
        );
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtag
        );
    }

}
