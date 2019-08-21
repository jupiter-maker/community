package com.jupiter.community.controller;


import com.jupiter.community.dto.QuestionDto;
import com.jupiter.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDto questionDto = questionService.getQuestionById(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        return "question";
    }
}
