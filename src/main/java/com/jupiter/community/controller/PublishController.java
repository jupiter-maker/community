package com.jupiter.community.controller;

import com.jupiter.community.cache.TagCache;
import com.jupiter.community.dto.QuestionDto;
import com.jupiter.community.model.Question;
import com.jupiter.community.model.User;
import com.jupiter.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{questionId}")
    public String editQuestion(@PathVariable(name="questionId") Long questionId,
                               Model model){
        QuestionDto question = questionService.getQuestionById(questionId);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("questionId",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value="title",required = false) String title,
                            @RequestParam(value="description",required = false) String description,
                            @RequestParam(value="tag",required = false) String tag,
                            @RequestParam(value="questionId",required = false) Long questionId,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("questionId",questionId);
        if(StringUtils.isBlank(title)){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(StringUtils.isBlank(description)){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(StringUtils.isBlank(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入错误或不存在标签:"+invalid+",请注意格式:tag1&tag2&tag3");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登陆");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(questionId);
        questionService.createOrUpdate(question);
        model.addAttribute("tags", TagCache.get());
        return "redirect:/";

    }
}
