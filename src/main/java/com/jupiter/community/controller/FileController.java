package com.jupiter.community.controller;

import com.alibaba.fastjson.JSON;
import com.jupiter.community.dto.FileDto;
import com.jupiter.community.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FtpService ftpService;

    @RequestMapping("/upload")
    public FileDto upload(HttpServletRequest request,
                          HttpServletResponse response){
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        FileDto fileDto = new FileDto();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        if(file==null){
            fileDto.setSuccess(0);
            fileDto.setMessage("upload error");
            try{
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(fileDto));
                writer.close();
            }catch(Exception e){
            }
        }else{
            Map<String, String> result = ftpService.uploadPicture(file);
            if("0".equals(result.get("error"))){
                //上传成功
                fileDto.setSuccess(1);
                fileDto.setMessage("upload success");
                fileDto.setUrl(result.get("url"));
            }else{
                fileDto.setSuccess(0);
                fileDto.setMessage("upload error");
            }
            try{
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(fileDto));
                writer.close();
            }catch(Exception e){
            }

        }
        return null;
    }
}
