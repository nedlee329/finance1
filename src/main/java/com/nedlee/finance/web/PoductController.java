package com.nedlee.finance.web;

import com.nedlee.finance.po.Product;
import com.nedlee.finance.service.CustomerService;
import com.nedlee.finance.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/cclogin")
public class PoductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);//是否严格解析时间 false则严格解析 true宽松解析
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @GetMapping("/products/{id}")
    public String products(@PageableDefault(size = 2,sort = {"jyTime"},direction = Sort.Direction.DESC) Pageable pageable,
                            Model model,@PathVariable Long id){
        model.addAttribute("page",productService.listProduct(id,pageable));
        model.addAttribute("customerId",id);
        return"product";
    }
    @PostMapping("/products/{id}/search")
    public String search(@PageableDefault(size = 2,sort = {"jyTime"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model,@PathVariable Long id){
        /*model.addAttribute("page",productService.listProduct(pageable);*/
        return"product :: productList";
    }

    @GetMapping("/products/input")
    public String input(Model model){
        model.addAttribute("product",new Product());
        return "product-input";
    }

    @PostMapping("/products/{id}")
    public String post(Product product, HttpSession session, RedirectAttributes attributes,@PathVariable Long id){



        Product p = productService.saveProduct(product);
        if (p == null ) {
            attributes.addFlashAttribute("message","新增失败！");
        }else {
            attributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/cclogin/products/{id}";
    }


}
