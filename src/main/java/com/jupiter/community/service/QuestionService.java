package com.jupiter.community.service;

import com.jupiter.community.dto.PageInfoDto;
import com.jupiter.community.dto.QuestionDto;
import com.jupiter.community.dto.QuestionQueryDto;
import com.jupiter.community.exception.CustomizeErrorCode;
import com.jupiter.community.exception.CustomizeException;
import com.jupiter.community.mapper.QuestionExtMapper;
import com.jupiter.community.mapper.QuestionMapper;
import com.jupiter.community.mapper.UserMapper;
import com.jupiter.community.model.Question;
import com.jupiter.community.model.QuestionExample;
import com.jupiter.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    //返回所有社区问题
    public PageInfoDto getQuestionList(Integer pn, Integer rows, String search) {

        if(StringUtils.isNotBlank(search)){
            String tags = StringUtils.replace(search, " ", "|");
        }


        PageInfoDto<QuestionDto> pageInfoDto = new PageInfoDto<>();

        QuestionQueryDto questionQueryDto = new QuestionQueryDto();
        if(StringUtils.isNotBlank(search)){
            questionQueryDto.setSearch(search);
        }
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDto);
        pageInfoDto.setPageInfo(totalCount,pn,rows);
        if(pn<1){
            pn=1;
        }
        if(pn>pageInfoDto.getPageSize()){
            pn=pageInfoDto.getPageSize();
        }
        questionQueryDto.setPn((pn-1)*rows);
        questionQueryDto.setRows(rows);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDto);
        if(questions==null){
            pageInfoDto.setData(null);
            return pageInfoDto;
        }
        List<QuestionDto> questionDtos = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            if(question.getDescription().length()>20){
                questionDto.setDescription(question.getDescription().substring(0,20)+"......");
            }
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pageInfoDto.setData(questionDtos);//为pageinfo添加该页对象信息
        return pageInfoDto;
    }

    //返回用户提问列表
    public PageInfoDto getUserQuestionList(Long userId, Integer pn, Integer rows) {
        PageInfoDto<QuestionDto> pageInfoDto = new PageInfoDto<>();
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
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds((pn-1)*rows,rows));
        List<QuestionDto> questionDtos = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            if(question.getDescription().length()>20){
                questionDto.setDescription(question.getDescription().substring(0,20)+"......");
            }
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pageInfoDto.setData(questionDtos);//为pageinfo添加该页对象信息
        return pageInfoDto;
    }

    public QuestionDto getQuestionById(Long id) {
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
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
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

    //更新阅读数
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDto> selectRelated(QuestionDto queryDto) {
        if(StringUtils.isBlank(queryDto.getTag())){
            return new ArrayList<>();
        }
        String tags = StringUtils.replace(queryDto.getTag(), "&", "|");
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(tags);
        List<Question> questions = questionExtMapper.selectRelated(question);

        List<QuestionDto> questionDtos = questions.stream().map(q -> {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q,questionDto);
            return questionDto;
        }).collect(Collectors.toList());
        return questionDtos;
    }
}
