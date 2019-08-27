package com.jupiter.community.dto;

import lombok.Data;

@Data
public class QuestionQueryDto {
    private String search;
    private Integer pn;
    private Integer rows;
}
