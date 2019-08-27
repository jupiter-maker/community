package com.jupiter.community.controller;

import com.jupiter.community.dto.FileDto;
import com.jupiter.community.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FtpService ftpService;

    @RequestMapping("/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request){

        FileDto fileDto = new FileDto();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        if(file==null){
            fileDto.setSuccess(0);
            fileDto.setMessage("图片上传失败");
            return fileDto;
        }
        Map<String, String> result = ftpService.uploadPicture(file);
        if("0".equals(result.get("error"))){
            //上传成功
            fileDto.setSuccess(1);
            fileDto.setMessage("图片上传成功");
            fileDto.setUrl(result.get("url"));
        }else{
            fileDto.setSuccess(0);
            fileDto.setMessage("图片上传失败");
        }
        return fileDto;
    }
}
