package com.example.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ResponseResult;
import com.example.acl.entity.Role;
import com.example.acl.service.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/role")

public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public ResponseResult index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", pageParam.getRecords());
        map.put("total", pageParam.getTotal());
        return ResponseResult.success(map);
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable String id) {
        Role role = roleService.getById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("item", role);
        return ResponseResult.success(map);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public ResponseResult save(@RequestBody Role role) {
        roleService.save(role);
        return ResponseResult.success();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public ResponseResult updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public ResponseResult remove(@PathVariable String id) {
        roleService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public ResponseResult batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return ResponseResult.success();
    }
}

