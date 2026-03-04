package com.study.Ex12CalcDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalcController {
    @Autowired
    private CalcService service;

    @GetMapping("/")
    public String main(){
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(
                @RequestParam Integer num1,
                @RequestParam Integer num2,
                @RequestParam String op, Model model){

        Integer result = service.calculateAndSave(num1, num2, op);
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("result", result);
        return "index";
    }
}
