package fastcampus.projectboard.service;

import fastcampus.projectboard.dto.ArticleCommentDto;
import fastcampus.projectboard.dto.ArticleCommentUpdateDto;
import fastcampus.projectboard.repository.ArticleCommentRepository;
import fastcampus.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return List.of();
    }

    @Transactional
    public void saveComment(ArticleCommentDto dto) {
    }

    public void updateComment(Long commentId, ArticleCommentUpdateDto dto) {

    }

    public void deleteComment(Long commentId) {
    }
}
