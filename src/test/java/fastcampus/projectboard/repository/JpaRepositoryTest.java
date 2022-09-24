package fastcampus.projectboard.repository;

import fastcampus.projectboard.config.JpaConfig;
import fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)    // jpaConfig클래스는 우리가 생성한 거여서 임포트해야 한다.
@DataJpaTest
class JpaRepositoryTest {

    @Autowired private ArticleRepository articleRepository;

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() throws Exception {
        // given

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles)
                .isNotNull()
                .hasSize(0);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() throws Exception {
        // given
        long previousCount = articleRepository.count();
        Article article = Article.of("hello", "hi", "#hi");

        // when
        articleRepository.save(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() throws Exception {
        // given
        Article article = Article.of("hello", "hi", "#hi");
        Article savedArticle = articleRepository.save(article);
        Article foundArticle = articleRepository.findById(savedArticle.getId()).orElseThrow();

        // when
        String updatedHashTag = "#wow";
        foundArticle.setHashtag(updatedHashTag);
        /** transaction 이 걸려있어서 update 쿼리를 날리지 않는다.
         * 이럴때 saveAndFlush 메서드를 사용하면 db에 업데이트 쿼리를 날린다.
         * 실제 반영은 X */
        Article updatedArticle = articleRepository.saveAndFlush(foundArticle);

        // then
        assertThat(updatedArticle).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashTag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDelete_thenWorksFine() throws Exception {
        // given
        Article article = Article.of("hello", "hi", "#hi");
        Article savedArticle = articleRepository.save(article);
        long previousCount = articleRepository.count();

        // when
        articleRepository.delete(savedArticle);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount - 1);
    }
}