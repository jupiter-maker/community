package com.jupiter.community.mapper;

import com.jupiter.community.model.Question;
import com.jupiter.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(@Param("record") Question record);

}