package fastcampus.projectboard.controller;

import fastcampus.projectboard.domain.constant.SearchType;
import fastcampus.projectboard.dto.ArticleDto;
import fastcampus.projectboard.dto.UserAccountDto;
import fastcampus.projectboard.dto.ArticleFormDto;
import fastcampus.projectboard.dto.response.ArticleResponse;
import fastcampus.projectboard.dto.response.ArticleWithCommentsResponse;
import fastcampus.projectboard.service.ArticleService;
import fastcampus.projectboard.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable,
            ModelMap map) {
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable)
                .map(ArticleResponse::from);
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", paginationBarNumbers);
        map.addAttribute("searchTypes", SearchType.values());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        long totalCount = articleService.getArticleCount();
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());
        map.addAttribute("totalCount", totalCount);
        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable,
            ModelMap map)
    {
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<String> hashtags = articleService.getHashtags();
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(articles.getNumber(), articles.getTotalPages());
        map.addAttribute("articles", articles);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", paginationBarNumbers);
        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    public String createForm(ModelMap map) {
        map.addAttribute("articleForm", ArticleFormDto.of());
        return "articles/form";
    }

    @PostMapping("/form")
    public String saveArticle(@ModelAttribute ArticleFormDto articleFormDto) {
        articleService.saveArticle(articleFormDto.toDto(UserAccountDto.of(
                "uno", "asdf1234", "uno@mail.com", "Uno", "memo", null, null, null, null
        )));
        return "redirect:/";
    }

    @GetMapping("/{articleId}/form")
    public String editForm(@PathVariable Long articleId, ModelMap map) {
        ArticleFormDto articleForm = ArticleFormDto.from(articleService.getArticle(articleId));
        map.addAttribute("articleForm", articleForm);
        return "articles/form";
    }

    @PostMapping("/{articleId}/form")
    public String editArticle(
            @ModelAttribute ArticleFormDto articleFormDto,
            @PathVariable Long articleId,
            RedirectAttributes redirectAttributes) {
        ArticleDto articleDto = articleFormDto.toDto(UserAccountDto.of(
                "uno", "asdf1234", "uno@mail.com", "Uno", "memo", null, null, null, null
        ));
        articleService.updateArticle(articleId, articleDto);
        redirectAttributes.addAttribute("articleId", articleId);
        return "redirect:/articles/{articleId}";
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/";
    }

}
