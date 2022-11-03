package fastcampus.projectboard.controller;

import fastcampus.projectboard.config.SecurityConfig;
import fastcampus.projectboard.dto.ArticleCommentDto;
import fastcampus.projectboard.service.ArticleCommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 댓글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleCommentController.class)
class ArticleCommentControllerTest {

    @Autowired MockMvc mvc;
    @MockBean ArticleCommentService articleCommentService;

    @DisplayName("[view][POST] 새 댓글 등록 - 정상 호출")
    @Test
    void givenNewCommentInfo_whenRequesting_thenSavesNewComment() throws Exception {
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        mvc.perform(
                        post("/comments/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection());
        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }

    @DisplayName("[view][POST] 댓글 삭제 - 정상 호출")
    @Test
    void givenCommentIdToDelete_whenRequesting_thenDeletesComment() throws Exception {
        // Given
        long commentId = 1L;
        willDoNothing().given(articleCommentService).deleteArticleComment(commentId);

        // When & Then
        mvc.perform(
                post("/comments/" + commentId + "/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection());
        then(articleCommentService).should().deleteArticleComment(commentId);
    }

}