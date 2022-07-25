package com.example.cms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ResponseResult;
import com.example.cms.entity.CrmBanner;
import com.example.cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-17
 */
@RestController
@RequestMapping("/educms/banner")
@CrossOrigin
public class CrmBannerController {
    @Autowired
    CrmBannerService crmBannerService;

    // 显示所有banner
    @GetMapping("getAllBanner")
    public ResponseResult getALl(){
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return ResponseResult.success(list);
    }

    // 查询分页banner
    @GetMapping("pageBanner")
    public ResponseResult pageBaner(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        Page<CrmBanner> crmBannerPage = new Page<>(page, limit);
        IPage<CrmBanner> page1 = crmBannerService.page(crmBannerPage, null);
        Long total = page1.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", page1.getRecords());
        return ResponseResult.success(map);
    }

    // 添加banner
    @PostMapping("addBanner")
    public ResponseResult addBaner(@RequestBody CrmBanner crmBanner) {
        boolean save = crmBannerService.save(crmBanner);
        return ResponseResult.success(save);
    }

    // 修改banner
    @PutMapping("updateBanner")
    public ResponseResult updateBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return ResponseResult.success();
    }

    // 删除banner
    @DeleteMapping("remove/{id}")
    public ResponseResult remove(@PathVariable("id") String id) {
        crmBannerService.removeById(id);
        return  ResponseResult.success();
    }
}
