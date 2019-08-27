package com.jupiter.community.controller;

import com.jupiter.community.dto.PageInfoDto;
import com.jupiter.community.model.User;
import com.jupiter.community.service.NotificationService;
import com.jupiter.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                          @RequestParam(value = "rows", defaultValue = "5") Integer rows) {
       User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            //查询我的问题
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageInfoDto pageInfo = questionService.getUserQuestionList(user.getId(), pn, rows);
            model.addAttribute("pageInfo",pageInfo);
        } else if ("replies".equals(action)) {
            //查询回复
            PageInfoDto pageInfo = notificationService.list(user.getId(), pn, rows);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pageInfo",pageInfo);
        }

        return "profile";
    }
}
