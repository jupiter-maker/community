package com.jupiter.community.controller;

import com.jupiter.community.dto.NotificationDto;
import com.jupiter.community.enums.NotificationTypeEnum;
import com.jupiter.community.model.User;
import com.jupiter.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        NotificationDto notificationDto = notificationService.read(id,user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType()
                    || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDto.getType()){
            return "redirect:/question/"+notificationDto.getOuterId();
        }
        return "redirect:/";
    }
}
