package com.jupiter.community.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.community.exception.CustomizeException;
import com.jupiter.community.exception.ICustomizeErrorCode;

import java.util.List;

/**
 * description:自定义相应结构
 * @author 谪仙
 *
 */
public class CommunityResultDto {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static CommunityResultDto build(ICustomizeErrorCode errorCode){
        return new CommunityResultDto(errorCode.getStatus(),errorCode.getMessage(),null);
    }
    public static CommunityResultDto build(Integer status, String msg, Object data) {
        return new CommunityResultDto(status, msg, data);
    }

    public static CommunityResultDto ok(Object data) {
        return new CommunityResultDto(data);
    }

    public static CommunityResultDto ok() {
        return new CommunityResultDto(null);
    }

    public CommunityResultDto() {

    }

    public static CommunityResultDto build(Integer status, String msg) {
        return new CommunityResultDto(status, msg, null);
    }

    public CommunityResultDto(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public CommunityResultDto(Object data) {
        this.status = 200;
        this.msg = "执行成功";
        this.data = data;
    }

    public static CommunityResultDto build(CustomizeException e) {
        return new CommunityResultDto(e.getStatus(),e.getMessage(),null);
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为CommunityResult对象
     * 
     * @param jsonData json数据
     * @param clazz CommunityResult中的object类型
     * @return
     */
    public static CommunityResultDto formatToPojo(String jsonData, Class<?> clazz) {
        try {
        	JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz == null) {
                return MAPPER.readValue(jsonData, CommunityResultDto.class);
            }else {      
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static CommunityResultDto format(String json) {
        try {
            return MAPPER.readValue(json, CommunityResultDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static CommunityResultDto formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
