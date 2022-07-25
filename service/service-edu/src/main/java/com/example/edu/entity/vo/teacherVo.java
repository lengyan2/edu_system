package com.example.edu.entity.vo;

import lombok.Data;
import reactor.util.annotation.Nullable;

import java.util.Date;

@Data
public class teacherVo {

    @Nullable
    private String Name;
    @Nullable
    private Integer level;
    @Nullable
    private Date begin;
    @Nullable
    private Date end;
}
