package com.jupiter.community.controller;

import com.jupiter.community.dto.PageInfoDto;
import com.jupiter.community.mapper.UserMapper;
import com.jupiter.community.model.User;
import com.jupiter.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
   private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="pn",defaultValue = "1") Integer pn,
                        @RequestParam(name="rows",defaultValue = "2") Integer rows){

        PageInfoDto pageInfoDto = questionService.geQuestiontList(pn,rows);
        model.addAttribute("pageInfo",pageInfoDto);
        return "index";
    }
}
