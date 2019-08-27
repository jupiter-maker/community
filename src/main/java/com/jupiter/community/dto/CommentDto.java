package com.jupiter.community.dto;

import com.jupiter.community.model.User;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commenttator;
    private Long gmtCreate;
    private Long gmtModefied;
    private Integer commentCount;
    private Long likeCount;
    private String content;
    private User user;
}
