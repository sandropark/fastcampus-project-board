package fastcampus.projectboard.service;

import fastcampus.projectboard.domain.Article;
import fastcampus.projectboard.domain.ArticleComment;
import fastcampus.projectboard.dto.ArticleCommentDto;
import fastcampus.projectboard.repository.ArticleCommentRepository;
import fastcampus.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    @Transactional
    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            ArticleComment articleComment = dto.toEntity(article);
            articleCommentRepository.save(articleComment);
        } catch (EntityNotFoundException e) {
            log.warn("게시글을 찾을 수 없습니다 - articleId: {}", dto.articleId());
        }
    }

    @Transactional
    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            articleComment.setContent(dto.content());
        } catch (EntityNotFoundException e) {
            log.warn("댓글을 찾을 수 없습니다 - articleCommentId: {}", dto.id());
        }
    }

    @Transactional
    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }
}
