package com.jupiter.community.advice;

import com.alibaba.fastjson.JSON;
import com.jupiter.community.dto.CommunityResultDto;
import com.jupiter.community.exception.CustomizeErrorCode;
import com.jupiter.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e, Model model,
                                     HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json; charset=UTF-8".equals(contentType)){
            CommunityResultDto communityResultDto = null;
            //返回json
            if(e instanceof CustomizeException){
                communityResultDto = CommunityResultDto.build((CustomizeException)e);
            }else{
                communityResultDto = CommunityResultDto.build(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(communityResultDto));
                writer.close();
            }catch (IOException ioe){
            }
            return null;

        }else{
            //页面跳转
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

    }



}
