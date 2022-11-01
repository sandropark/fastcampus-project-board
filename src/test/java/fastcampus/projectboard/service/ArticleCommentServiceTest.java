package fastcampus.projectboard.service;

import fastcampus.projectboard.domain.Article;
import fastcampus.projectboard.domain.ArticleComment;
import fastcampus.projectboard.dto.ArticleCommentDto;
import fastcampus.projectboard.dto.ArticleCommentUpdateDto;
import fastcampus.projectboard.repository.ArticleCommentRepository;
import fastcampus.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {
    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;

    @Disabled("구현 중")
    @DisplayName("게시글 ID로 조회하면, 해당 게시글에 포함된 댓글을 반환한다. ")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsCommentList() throws Exception {
        // Given
        Long articleId = 1L;
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(
                        Article.of(null, null, null, null)));

        // When
        List<ArticleCommentDto> articleComments = sut.searchArticleComments(articleId);

        // Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @Disabled("구현 중")
    @DisplayName("댓글 정보를 입력하면, 저장한다.")
    @Test
    void givenCommentInfo_whenSavingComment_thenSavesComment() throws Exception {
        // Given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.saveComment(ArticleCommentDto.of(null, null, null, null, null));

        // Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @Disabled("구현 중")
    @DisplayName("댓글 ID와 수정정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenCommentIDAndModifiedInfo_whenUpdatingComment_thenUpdatesComment() throws Exception {
        // Given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.updateComment(1L, ArticleCommentUpdateDto.of(null));

        // Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @Disabled("구현 중")
    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenCommentID_whenDeletingComment_thenDeletesComment() throws Exception {
        // Given
        willDoNothing().given(articleCommentRepository).delete(any(ArticleComment.class));

        // When
        sut.deleteComment(1L);

        // Then
        then(articleCommentRepository).should().delete(any(ArticleComment.class));
    }

}
