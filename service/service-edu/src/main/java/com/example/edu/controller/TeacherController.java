package com.example.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ResponseResult;
import com.example.edu.entity.Teacher;
import com.example.edu.entity.vo.teacherVo;
import com.example.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.util.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-13
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class TeacherController {

    @Autowired
  private   TeacherService teacherService;

        // 查询表中所有数据
    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public ResponseResult allTeacher(){
        List<Teacher> list = teacherService.list(null);
        return ResponseResult.success(list);
    }

//    // 逻辑删除
//    @ApiOperation("根据id删除讲师")
//    @DeleteMapping("{id}")
//    public void deleteTeacherById(
//            @ApiParam(name = "id",value = "讲师ID",required = true)
//            @PathVariable("id") String  id){
//        teacherService.removeById(id);
//    }

    @ApiOperation("t统一返回结果")
    @GetMapping("")
    public ResponseResult query(){
        List<Teacher> list = teacherService.list(null);
        return ResponseResult.success(list);
    }

    // 分页查询统一返回结果
    @ApiOperation("分页查询")
    @RequestMapping("/pageTeacher")
    public ResponseResult pageListTeacher(@RequestParam(value = "page" ) Integer page,
                                          @RequestParam(value = "limit") Integer limit,
                                          @RequestBody(required = false) @Nullable teacherVo teacherVo){
//
        Page<Teacher> objects = new Page<Teacher>(page,limit);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        if (teacherVo != null){
        if ( teacherVo.getName()!=null&&teacherVo.getName().length()>0){
            teacherQueryWrapper.like("Name",teacherVo.getName());
        }
        if (!StringUtils.isEmpty(teacherVo.getLevel())){
            teacherQueryWrapper.eq("level",teacherVo.getLevel());
        }
        if (!StringUtils.isEmpty(teacherVo.getBegin())){
            teacherQueryWrapper.gt("gmt_create",teacherVo.getBegin());
        }
        if (!StringUtils.isEmpty(teacherVo.getEnd())){
            teacherQueryWrapper.lt("gmt_create",teacherVo.getEnd());
        }}
        teacherQueryWrapper.orderByDesc("gmt_create");
        IPage<Teacher> page1 = teacherService.page(objects,teacherQueryWrapper);
        long total = page1.getTotal();
        List<Teacher> records = page1.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",records);
        map.put("total",total);
        if (!page1.getRecords().isEmpty()){
            return ResponseResult.success(map);
        }
        return ResponseResult.fail("数据不存在");


    }


    // 添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public ResponseResult addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if (save){
          return   ResponseResult.success();
        }
        return ResponseResult.fail("添加失败");
    }


    // 根据讲师id进行查询
    @GetMapping("/{id}")
    public ResponseResult getTeacherById(@PathVariable("id") String id) {
        System.out.println("id"+id);
        Teacher byId = teacherService.getById(id);
        System.out.println(byId.getName());
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("teacher",byId);
        return ResponseResult.success(stringObjectHashMap);
    }


    // 修改讲师
    @PostMapping("UpdateTeacher")
    public ResponseResult UpdateTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);

        if (b){
            return ResponseResult.success();
        }
        return ResponseResult.fail("修改失败");
    }

    // 根据id删除讲师
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("{id}")
    public ResponseResult deleteTeacherById(@PathVariable("id") String id){
        boolean b = teacherService.removeById(id);
        if (b){
            HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
            stringIntegerHashMap.put("code",200);
            return ResponseResult.success(stringIntegerHashMap);
        }
        return ResponseResult.fail("删除失败 ，讲师未找到");
    }
}

