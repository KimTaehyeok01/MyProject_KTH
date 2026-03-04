package com.study.Ex13VMDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ProductController {
    @Autowired
    private ProductService service;

    // 메인
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("list", service.findAll());
        return "index";
    }

    // 추가 페이지 이동
    @GetMapping("/addForm")
    public String addForm() {
        return "addForm";
    }

    // 추가하기
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute ProductEntity entity) {
        service.save(entity);
        return "redirect:/";
    }

    // 수정 페이지 이동
    @GetMapping("/editForm")
    public String editForm(@RequestParam Integer id, Model model) {
        model.addAttribute("product", service.findById(id));
        return "editForm";
    }

    // 수정하기
    @PostMapping("/editProduct")
    public String editProduct(@RequestParam Integer id, @ModelAttribute ProductEntity entity) {
        service.update(id, entity);
        return "redirect:/";
    }

    // 삭제하기
    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/";
    }
}





















