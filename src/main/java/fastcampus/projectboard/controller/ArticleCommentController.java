package fastcampus.projectboard.controller;

import fastcampus.projectboard.dto.ArticleCommentDto;
import fastcampus.projectboard.dto.UserAccountDto;
import fastcampus.projectboard.dto.request.ArticleCommentRequest;
import fastcampus.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String saveComments(@ModelAttribute ArticleCommentRequest articleCommentRequest) {
        // TODO: 인증 정보를 넣어줘야 한다.
        ArticleCommentDto articleCommentDto = articleCommentRequest.toDto(UserAccountDto.of(
                "uno", "asdf1234", "uno@mail.com", "Uno", "memo", null, null, null, null
        ));
        articleCommentService.saveArticleComment(articleCommentDto);
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId,
                                @RequestParam(required = false) Long articleId) {
        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;
    }

}
