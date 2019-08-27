package com.jupiter.community.mapper;

import com.jupiter.community.model.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentExtMapper {
    int incCommentCount(@Param("record") Comment record);
}
