package fastcampus.projectboard.service;

import fastcampus.projectboard.domain.Article;
import fastcampus.projectboard.domain.type.SearchType;
import fastcampus.projectboard.dto.ArticleDto;
import fastcampus.projectboard.dto.ArticleUpdateDto;
import fastcampus.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @Disabled("구현 중")
    @DisplayName("게시글을 검색하면, 페이지 정보가 포함된 게시글 리스트를 반환한다. ")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() throws Exception {
        // Given

        // When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");

        // Then
        assertThat(articles).isNotNull();
    }

    @Disabled("구현 중")
    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle () throws Exception {
        // Given

        // When
        ArticleDto article = sut.searchArticle(1L);
        // Then
        assertThat(article).isNotNull();
    }

    @Disabled("구현 중")
    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() throws Exception {
        // Given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "san", "새 글", "안녕", "#hello"));

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @Disabled("구현 중")
    @DisplayName("게시글의 ID와 수정정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIDAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() throws Exception {
        // Given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        sut.updateArticle(1L, ArticleUpdateDto.of("새 글", "안녕", "#hello"));

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @Disabled("구현 중")
    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleID_whenDeletingArticle_thenDeletesArticle() throws Exception {
        // Given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }

}
