package com.jupiter.community.controller;

import com.jupiter.community.dto.AccessTokenDTO;
import com.jupiter.community.dto.GithubUser;
import com.jupiter.community.model.User;
import com.jupiter.community.provider.GithubProvider;
import com.jupiter.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github_client_id}")
    private String github_client_id;

    @Value("${github_client_secret}")
    private String github_client_secret;

    @Value("${github_redirect_uri}")
    private String github_redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest requset,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setRedirect_uri(github_redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(github_client_id);
        accessTokenDTO.setClient_secret(github_client_secret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUserInfo(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            //cookie中token
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            //登录成功,写cookie或session
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
    //退出登录
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
