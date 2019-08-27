package com.jupiter.community.dto;

import com.jupiter.community.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
