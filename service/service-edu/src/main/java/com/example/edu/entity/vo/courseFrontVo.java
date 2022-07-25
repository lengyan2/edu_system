package com.example.edu.entity.vo;

import lombok.Data;

@Data
public class courseFrontVo {
    private String title;
    private String teacherId;
    private String subjectParentId;
    private String subjectId;
    private String buyCountSort; // 销量 排序
    private String gmtCreateSort; // 最新时间排序
    private String priceSort; // 价格排序

}
