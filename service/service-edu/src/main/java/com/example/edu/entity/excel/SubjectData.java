package com.example.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(index = 0,value = "一级菜单")
    private String oneSubjectName;

    @ExcelProperty(index = 1,value = "二级菜单")
    private String twoSubjectName;
}
