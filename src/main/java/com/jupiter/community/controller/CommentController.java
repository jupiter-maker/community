package com.jupiter.community.controller;

import com.jupiter.community.dto.CommentCreateDto;
import com.jupiter.community.dto.CommentDto;
import com.jupiter.community.dto.CommunityResultDto;
import com.jupiter.community.enums.CommentTypeEnum;
import com.jupiter.community.exception.CustomizeErrorCode;
import com.jupiter.community.exception.CustomizeException;
import com.jupiter.community.model.Comment;
import com.jupiter.community.model.User;
import com.jupiter.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        if (StringUtils.isBlank(commentCreateDto.getContent())) {
            throw new CustomizeException(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModefied(System.currentTimeMillis());
        comment.setCommenttator(user.getId());
        commentService.insert(comment,user);
        return CommunityResultDto.build(200, "成功");
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{commentId}", method = RequestMethod.GET)
    public CommunityResultDto comments(@PathVariable(name = "commentId") Long commentId) {
        List<CommentDto> commentDtos = commentService.listByTargetId(commentId, CommentTypeEnum.COMMENT);
        return CommunityResultDto.ok(commentDtos);
    }
}