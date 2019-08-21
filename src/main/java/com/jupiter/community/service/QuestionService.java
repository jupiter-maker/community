package com.jupiter.community.service;

import com.jupiter.community.dto.PageInfoDto;
import com.jupiter.community.dto.QuestionDto;
import com.jupiter.community.exception.CustomizeErrorCode;
import com.jupiter.community.exception.CustomizeException;
import com.jupiter.community.mapper.QuestionExtMapper;
import com.jupiter.community.mapper.QuestionMapper;
import com.jupiter.community.mapper.UserMapper;
import com.jupiter.community.model.Question;
import com.jupiter.community.model.QuestionExample;
import com.jupiter.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    //返回所有社区问题
    public PageInfoDto geQuestiontList(Integer pn, Integer rows) {
        PageInfoDto pageInfoDto = new PageInfoDto();
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        pageInfoDto.setPageInfo(totalCount,pn,rows);
        if(pn<1){
            pn=1;
        }
        if(pn>pageInfoDto.getPageSize()){
            pn=pageInfoDto.getPageSize();
        }

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds((pn-1)*rows,rows));
        List<QuestionDto> questionDtos = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pageInfoDto.setQuestions(questionDtos);//为pageinfo添加该页对象信息
        return pageInfoDto;
    }

    //返回用户提问列表
    public PageInfoDto getUserQuestionList(Integer userId, Integer pn, Integer rows) {
        PageInfoDto pageInfoDto = new PageInfoDto();
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);
        pageInfoDto.setPageInfo(totalCount,pn,rows);
        if(pn<1){
            pn=1;
        }
        if(pn>pageInfoDto.getPageSize()){
            pn=pageInfoDto.getPageSize();
        }
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds((pn-1)*rows,rows));
        List<QuestionDto> questionDtos = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pageInfoDto.setQuestions(questionDtos);//为pageinfo添加该页对象信息
        return pageInfoDto;
    }

    public QuestionDto getQuestionById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    //创建或修改问题
    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insert(question);
        }else{
            //更新问题
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, example);
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {

        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
