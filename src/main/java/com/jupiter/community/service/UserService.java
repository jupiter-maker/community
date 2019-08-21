package com.jupiter.community.service;

import com.jupiter.community.mapper.UserMapper;
import com.jupiter.community.model.User;
import com.jupiter.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //查询数据库中是否有该用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        //没有就创建用户
        if(dbUsers.size()==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //有则更新
            User dbUser = dbUsers.get(0);
            User upDateUser = new User();

            upDateUser.setGmtModified(System.currentTimeMillis());
            upDateUser.setAvatarUrl(user.getAvatarUrl());
            upDateUser.setToken(user.getToken());
            upDateUser.setName(user.getName());

            UserExample example = userExample;
            userExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(upDateUser, example);
        }


    }
}
