package com.nedlee.finance.web;

import com.nedlee.finance.po.Type;
import com.nedlee.finance.service.ArticleService;
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

import java.util.List;

@Controller
public class typeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if(id == -1){
            id = types.get(0).getId();
        }
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setTypeId(id);
        model.addAttribute("types" ,types);
        model.addAttribute("page",articleService.listArticle(pageable,articleQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
