package com.example.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.edu.entity.Subject;
import com.example.edu.entity.excel.SubjectData;
import com.example.edu.entity.subject.OneSubject;
import com.example.edu.entity.subject.TwoSubject;
import com.example.edu.mapper.SubjectMapper;
import com.example.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-14
 */
@Service

public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
//    /添加课程分类
    @Override
    public void saveSubject(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new PageReadListener<SubjectData>(dataList -> {

                Subject subject = new Subject();
                for (SubjectData d: dataList
                     ) {
                    QueryWrapper<Subject> wrapper = new QueryWrapper<>();
                    QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
                    Subject  one = this.getOne(wrapper
                            .eq("title", d.getOneSubjectName()).eq("parent_id", "0"));
                    System.out.println("one"+one);
                    // 如果查询的有这一🐔分类
                    if (one==null){
                        // 进行添加一级分类
                        subject.setParentId("0");
                        subject.setTitle(d.getOneSubjectName());
                        this.save(subject);
                        System.out.println(subject);

                    }
                    else {
                        subject.setId(one.getId());
                    }
                    System.out.println(subject.getId());
                    Subject two = this.getOne(wrapper1
                            .eq("title", d.getTwoSubjectName())
                            .eq("parent_id", subject.getId()));
                    System.out.println("二级"+d.getTwoSubjectName());
                    System.out.println("two"+two+d.getTwoSubjectName());
                    if (two==null)
                    {

                        subject.setParentId(subject.getId());
                        subject.setTitle(d.getTwoSubjectName());
                        subject.setId("");
                        this.save(subject);
                    }
                }
            })).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        // 查询一级分类 parent_id = 0
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("parent_id",0);
        List<Subject> one = baseMapper.selectList(subjectQueryWrapper);

        // 查询二级分类 parent_id != 0
        QueryWrapper<Subject> subjectQueryWrapper1 = new QueryWrapper<>();
        subjectQueryWrapper1.ne("parent_id",0);
        List<Subject> two = baseMapper.selectList(subjectQueryWrapper1);
        //封装一级分类
        List<OneSubject> finalSubject = new ArrayList<>();
        one.forEach(data -> {
                    OneSubject oneSubject = new OneSubject();
                    BeanUtils.copyProperties(data,oneSubject);
                    List<TwoSubject> twoFinalSubject = new ArrayList<>();
                    // 遍历二级分类list集合
                    two.forEach(twosubject -> {
                        // 判断是否为当前一级分类下的二级分类
                        if (data.getId().equals(twosubject.getParentId())){
                            TwoSubject twoSubject = new TwoSubject();
                            BeanUtils.copyProperties(twosubject,twoSubject);
                            System.out.println(twoSubject);
                            twoFinalSubject.add(twoSubject);
                        }
                    });
            System.out.println(twoFinalSubject);
                    oneSubject.setChildren(twoFinalSubject);
                    finalSubject.add(oneSubject);
        });
        return finalSubject;
    }


}
