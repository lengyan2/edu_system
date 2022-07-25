package com.example.edu.client;

import com.example.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod",fallback = VodFileFeignClient.class)
public interface VodClient {

    @DeleteMapping("/eduvod/videooss/deleteVideo/{videoSourceId}")
    public ResponseResult deleteVideo(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping("/eduvod/videooss/batchDeleteVideo")
    public ResponseResult batchDelete(@RequestParam("videoList") List<String> videoList);
}
