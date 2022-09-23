package fastcampus.projectboard.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Article article;  // 해당 댓글이 소속된 게시글
    private String content;

    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
