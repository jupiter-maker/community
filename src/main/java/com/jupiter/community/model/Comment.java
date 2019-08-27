package com.jupiter.community.model;

public class Comment {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.id
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.parent_id
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.type
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.commenttator
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Long commenttator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.gmt_create
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.gmt_modefied
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Long gmtModefied;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.like_count
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Integer likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.content
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community_comment.comment_count
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    private Integer commentCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.id
     *
     * @return the value of community_comment.id
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.id
     *
     * @param id the value for community_comment.id
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.parent_id
     *
     * @return the value of community_comment.parent_id
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.parent_id
     *
     * @param parentId the value for community_comment.parent_id
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.type
     *
     * @return the value of community_comment.type
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.type
     *
     * @param type the value for community_comment.type
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.commenttator
     *
     * @return the value of community_comment.commenttator
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Long getCommenttator() {
        return commenttator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.commenttator
     *
     * @param commenttator the value for community_comment.commenttator
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setCommenttator(Long commenttator) {
        this.commenttator = commenttator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.gmt_create
     *
     * @return the value of community_comment.gmt_create
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.gmt_create
     *
     * @param gmtCreate the value for community_comment.gmt_create
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.gmt_modefied
     *
     * @return the value of community_comment.gmt_modefied
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Long getGmtModefied() {
        return gmtModefied;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.gmt_modefied
     *
     * @param gmtModefied the value for community_comment.gmt_modefied
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setGmtModefied(Long gmtModefied) {
        this.gmtModefied = gmtModefied;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.like_count
     *
     * @return the value of community_comment.like_count
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.like_count
     *
     * @param likeCount the value for community_comment.like_count
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.content
     *
     * @return the value of community_comment.content
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.content
     *
     * @param content the value for community_comment.content
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community_comment.comment_count
     *
     * @return the value of community_comment.comment_count
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community_comment.comment_count
     *
     * @param commentCount the value for community_comment.comment_count
     *
     * @mbg.generated Sun Aug 25 17:26:59 GMT+08:00 2019
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}