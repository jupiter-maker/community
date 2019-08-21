package com.jupiter.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageInfoDto {
    private Integer pn;//当前页数
    private Integer rows;//每页条数
    private Integer totalCount;//总记录数
    private Integer pageSize;//总页数
    private List<QuestionDto> questions;  //当前页的对象信息
    private List<Integer> pages = new ArrayList<>();//当前页应该显示的页码数组

    //12345
    //总记录数，当前页码，每页条数
    public void setPageInfo(Integer totalCount,Integer pn,Integer rows){

        //设置每页条数
        this.rows=rows;
        //设置总记录条数
        this.totalCount=totalCount;
        //计算总页数
        if(totalCount<rows){
            this.pageSize=1;
        }else{
            this.pageSize=totalCount%rows==0? totalCount/rows:totalCount/rows+1;
        }
        if(pn<1){
            pn=1;
        }
        if(pn>pageSize){
            pn=pageSize;
        }
        //设置当前页数
        this.pn=pn;
        //设置当前页应该显示的页码数组
        this.pages.add(pn);
        for(int i = 1;i<=3;i++) {
            if(pn-i>0) {
                this.pages.add(0,pn-i);
            }
            if(pn+i<=this.pageSize) {
                this.pages.add(pn+i);
            }
        }


    }
}
