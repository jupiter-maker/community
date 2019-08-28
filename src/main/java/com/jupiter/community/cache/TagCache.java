package com.jupiter.community.cache;

import com.jupiter.community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDto> get(){
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("编程相关");
        program.setTags(Arrays.asList("java","php","python","js","node","c","c++"));
        tagDtos.add(program);

        TagDto framework = new TagDto();
        framework.setCategoryName("学习交流");
        framework.setTags(Arrays.asList("考研","竞赛","专业","大学"));
        tagDtos.add(framework);

        TagDto life = new TagDto();
        life.setCategoryName("生活日常");
        life.setTags(Arrays.asList("明星","日常","追剧","动漫"));
        tagDtos.add(life);
        return tagDtos;
    }

    public static  String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, "&");
        List<TagDto> tagDtos = get();
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
//        List<String> tagList = new ArrayList<>();
//        for(TagDto tagDto:tagDtos){
//            List<String> list = tagDto.getTags();
//            for(String str:list){
//                tagList.add(str);
//            }
//        }
//        StringBuilder invalid = new StringBuilder();
//        for(int i=0;i<split.length;i++){
//            if(!tagList.contains(split[i])){
//                invalid.append(split[i]+",");
//            }
//        }
//        invalid.deleteCharAt(invalid.lastIndexOf(","));
        return invalid;
    }
}
