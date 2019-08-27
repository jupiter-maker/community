package com.jupiter.community.service;

import com.jupiter.community.dto.NotificationDto;
import com.jupiter.community.dto.PageInfoDto;
import com.jupiter.community.enums.NotificationStatusEnum;
import com.jupiter.community.enums.NotificationTypeEnum;
import com.jupiter.community.exception.CustomizeErrorCode;
import com.jupiter.community.exception.CustomizeException;
import com.jupiter.community.mapper.NotificationMapper;
import com.jupiter.community.mapper.UserMapper;
import com.jupiter.community.model.Notification;
import com.jupiter.community.model.NotificationExample;
import com.jupiter.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public PageInfoDto list(Long userId, Integer pn, Integer rows) {

        PageInfoDto<NotificationDto> pageInfoDto = new PageInfoDto<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        pageInfoDto.setPageInfo(totalCount,pn,rows);
        if(pn<1){
            pn=1;
        }
        if(pn>pageInfoDto.getPageSize()){
            pn=pageInfoDto.getPageSize();
        }
        NotificationExample example = new NotificationExample();
        example.setOrderByClause("gmt_create desc");
        example.createCriteria()
                .andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds((pn - 1) * rows, rows));
        if(notifications.size()==0){
            return pageInfoDto;
        }
        List<NotificationDto> notificationDtos = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDtos.add(notificationDto);
        }
        pageInfoDto.setData(notificationDtos);//为pageinfo添加该页对象信息
        return pageInfoDto;
    }

    public Long unreadCount(Long userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long count = notificationMapper.countByExample(example);
        return count;
    }

    public NotificationDto read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //更新阅读状态
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;
    }
}
