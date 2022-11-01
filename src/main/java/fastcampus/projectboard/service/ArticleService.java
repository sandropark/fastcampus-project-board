package fastcampus.projectboard.service;

import fastcampus.projectboard.domain.type.SearchType;
import fastcampus.projectboard.dto.ArticleDto;
import fastcampus.projectboard.dto.ArticleWithCommentsDto;
import fastcampus.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword, Pageable pageable) {
        return Page.empty();
    }

    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }

    @Transactional
    public void saveArticle(ArticleDto dto) {
    }

    @Transactional
    public void updateArticle(ArticleDto dto) {
    }

    @Transactional
    public void deleteArticle(Long articleId) {
    }

}
