package com.jupiter.community.controller;

import com.jupiter.community.dto.PageInfoDto;
import com.jupiter.community.dto.QuestionDto;
import com.jupiter.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
   private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="pn",defaultValue = "1") Integer pn,
                        @RequestParam(name="rows",defaultValue = "5") Integer rows,
                        @RequestParam(name="search",required = false) String search){

        PageInfoDto<QuestionDto> pageInfoDto = questionService.getQuestionList(pn,rows,search);
        model.addAttribute("pageInfo",pageInfoDto);
        model.addAttribute("search",search);
        return "index";
    }
}
