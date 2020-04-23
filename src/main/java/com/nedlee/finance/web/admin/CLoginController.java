package com.nedlee.finance.web.admin;

import com.nedlee.finance.po.Customer;
import com.nedlee.finance.service.ArticleService;
import com.nedlee.finance.service.CustomerService;
import com.nedlee.finance.service.TagService;
import com.nedlee.finance.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cclogin")
public class CLoginController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping
    private String loginPage(){
        return "clogin";
    }

    @PostMapping("/index")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        @PageableDefault(size = 8, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",articleService.listArticle(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendArticles",articleService.listRecommendArticleTop(8));
        Customer customer = customerService.checkCustomer(username,password);
        if(customer != null){
            customer.setPassword(null);
            session.setAttribute("customer",customer);
            return "index";
        }else {
            attributes.addFlashAttribute("message","用户名和密码错误！");
            return "redirect:/cclogin";
        }
    }

    @GetMapping("/clogout")
    public String logout(HttpSession session){
        session.removeAttribute("customer");
        return "redirect:/";
    }

}
