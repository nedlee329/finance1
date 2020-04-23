package com.nedlee.finance.web.admin;

import com.nedlee.finance.po.Article;
import com.nedlee.finance.po.User;
import com.nedlee.finance.service.ArticleService;
import com.nedlee.finance.service.TagService;
import com.nedlee.finance.service.TypeService;
import com.nedlee.finance.vo.ArticleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ArticleController {

    private static final String INPUT = "admin/articles-input";
    private static final String LIST = "admin/articles";
    private static final String REDIRECT_LIST = "redirect:/admin/articles";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/articles")
    public String articles(@PageableDefault(size = 7, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        ArticleQuery article, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",articleService.listArticle(pageable,article));
        return LIST;
    }
    @PostMapping("/articles/search")
    public String search(@PageableDefault(size = 7, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         ArticleQuery article, Model model){
        model.addAttribute("page",articleService.listArticle(pageable,article));
        return "admin/articles :: articleList";
    }

    @GetMapping("/articles/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("article",new Article());
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/articles/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Article article = articleService.getArticle(id);
        article.init();
        model.addAttribute("article",article);
        return INPUT;
    }

    @PostMapping("/articles")
    public String post(Article article, RedirectAttributes attributes, HttpSession session){
        article.setUser((User)session.getAttribute("user"));
        article.setType(typeService.getType(article.getType().getId()));
        article.setTags(tagService.listTag(article.getTagIds()));
        Article a;
        if (article.getId() == null) {
            a = articleService.saveArticle(article);
        } else {
            a = articleService.updateArticle(article.getId(),article);
        }
        if (a == null ) {
            attributes.addFlashAttribute("message","操作失败！");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        articleService.deleteArticle(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }

}
