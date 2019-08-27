package com.jupiter.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不存在了，要不换一个试试？"),
    TARGET_PARAM__NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYS_ERROR(2004,"服务器冒烟了，稍后再试试!!!"),
    TYPE_PARAM_WARNG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在，请刷新一下页面"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你咋这么皮呢？"),
    NOTIFICATION_NOT_FOUND(2009,"该消息不存在");


    private Integer status;
    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(Integer status, String message) {
        this.message = message;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
