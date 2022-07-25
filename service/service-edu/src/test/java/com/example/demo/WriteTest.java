package com.example.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.example.demo.excel.DemoData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
@Slf4j
public class WriteTest {
    @Test
    public void simpleWrite(){
        String fileName = "/Users/daiyuanjing/write.xlsx";
        EasyExcel.write(fileName, DemoData.class).sheet("模版").doWrite(()->{return data();});

    }

    @Test
    public void simpleRead(){
        String fileName = "/Users/daiyuanjing/write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new PageReadListener<DemoData>(dataList -> {
            for (DemoData d :
                    dataList) {
                log.info("读取到一条数据：{}", JSON.toJSONString(d));

            }
        })).sheet().doRead();
    }
    private List<DemoData> data(){
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i+1);
            demoData.setSname("张"+i+1);
            list.add(demoData);

        }
        return list;
    }
}
