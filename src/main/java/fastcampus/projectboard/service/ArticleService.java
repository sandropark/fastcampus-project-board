package fastcampus.projectboard.service;

import fastcampus.projectboard.domain.type.SearchType;
import fastcampus.projectboard.dto.ArticleDto;
import fastcampus.projectboard.dto.ArticleUpdateDto;
import fastcampus.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return Page.empty();
    }

    public ArticleDto searchArticle(Long articleId) {
        return null;
    }

    @Transactional
    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(Long articleId, ArticleUpdateDto dto) {
    }

    public void deleteArticle(Long articleId) {
    }
}
