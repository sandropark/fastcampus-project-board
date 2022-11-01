package fastcampus.projectboard.service;

import fastcampus.projectboard.domain.Article;
import fastcampus.projectboard.domain.type.SearchType;
import fastcampus.projectboard.dto.ArticleDto;
import fastcampus.projectboard.dto.ArticleWithCommentsDto;
import fastcampus.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Page<ArticleDto> searchArticles(SearchType searchType, String search_keyword, Pageable pageable) {
        if (hasText(search_keyword)) {
            return switch (searchType) {
                case TITLE -> articleRepository.findByTitleContaining(search_keyword, pageable).map(ArticleDto::from);
                case CONTENT ->
                        articleRepository.findByContentContaining(search_keyword, pageable).map(ArticleDto::from);
                case ID ->
                        articleRepository.findByUserAccount_UserIdContaining(search_keyword, pageable).map(ArticleDto::from);
                case NICKNAME ->
                        articleRepository.findByUserAccount_NicknameContaining(search_keyword, pageable).map(ArticleDto::from);
                case HASHTAG -> articleRepository.findByHashtag("#" + search_keyword, pageable).map(ArticleDto::from);
            };
        }
        return articleRepository.findAll(pageable)
                .map(ArticleDto::from);
    }

    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional
    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    @Transactional
    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if (dto.title() != null) { article.setTitle(dto.title()); }
            if (dto.content() != null) { article.setContent(dto.content()); }
            article.setHashtag(dto.hashtag());

        } catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", dto);
        }
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

}
