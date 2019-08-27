package com.jupiter.community.service;

import com.jupiter.community.utils.FtpUtil;
import com.jupiter.community.utils.IDUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class FtpService {

    @Value("${FTP_HOST}")
    private String FTP_HOST;

    @Value("${FTP_PORT}")
    private Integer FTP_PORT;

    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;

    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;

    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;

    @Value("${PIC_BASE_URL}")
    private String PIC_BASE_URL;

    public Map<String,String> uploadPicture(MultipartFile uploadFile){
        Map<String,String> resultMap = new HashMap<>();
        try {
            //取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            //随机名字
            String newName = IDUtil.getImageName();
            newName += oldName.substring(oldName.lastIndexOf("."));
            //图片上传
            String datePath = new DateTime().toString("/yyyy/MM/dd");
            boolean result = FtpUtil.uploadFile(FTP_HOST, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
                    datePath, newName, uploadFile.getInputStream());
            //返回结果
            if(!result) {
                resultMap.put("error", "1");
                resultMap.put("message", "文件上传失败");
                return resultMap;
            }
            String url = PIC_BASE_URL+datePath+"/"+newName;
            resultMap.put("error", "0");
            resultMap.put("url", url);
            return resultMap;
        }catch(Exception e) {
            resultMap.put("error", "1");
            resultMap.put("message", "文件上传失败");
            return resultMap;
        }
    }
}
