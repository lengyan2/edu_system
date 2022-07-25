package com.example.edu.client;

import com.example.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileFeignClient implements VodClient{
    @Override
    public ResponseResult deleteVideo(String videoSourceId) {
        return ResponseResult.fail("time out");
    }

    @Override
    public ResponseResult batchDelete(List<String> videoList) {
        return ResponseResult.fail("error");
    }
}
