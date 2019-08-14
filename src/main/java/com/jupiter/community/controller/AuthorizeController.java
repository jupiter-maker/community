package com.jupiter.community.controller;

import com.jupiter.community.dto.AccessTokenDTO;
import com.jupiter.community.dto.GithubUser;
import com.jupiter.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github_client_id}")
    private String github_client_id;
    @Value("${github_client_secret}")
    private String github_client_secret;
    @Value("${github_redirect_uri}")
    private String github_redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setRedirect_uri(github_redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(github_client_id);
        accessTokenDTO.setClient_secret(github_client_secret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUserInfo(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
