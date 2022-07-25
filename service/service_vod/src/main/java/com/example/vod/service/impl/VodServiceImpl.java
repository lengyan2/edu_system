package com.example.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.example.vod.utils.InitVodClient;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.vod.service.VodService;
import com.example.vod.utils.VodProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class VodServiceImpl implements VodService {
    @Autowired
    VodProperties vodProperties;
    @Override
    public String uploadVideo(MultipartFile file)  {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();

            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    vodProperties.getAccessKeyId(),
                    vodProperties.getAccessKeySecret(),
                    title, originalFilename, inputStream);
            request.setApiRegionId("cn-shenzhen");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            System.out.println(videoId);
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
}

    // 根据videoid删除VIdeo
    @Override
    public void deleteVideo(String videoId) {

        try {
            String regionId = "cn-shenzhen";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, vodProperties.getAccessKeyId(), vodProperties.getAccessKeySecret());
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
        deleteVideoRequest.setVideoIds(videoId);
        defaultAcsClient.getAcsResponse(deleteVideoRequest);
        } catch (ClientException e) {

            e.printStackTrace();
        }
    }

    // 批量删除视频
    @Override
    public void bathDeleteVideo(List videoList) {

    try {
        String regionId = "cn-shenzhen";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, vodProperties.getAccessKeyId(), vodProperties.getAccessKeySecret());
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
        deleteVideoRequest.setVideoIds(String.valueOf(videoList).replace("[","").replace("]","").replace(" ",""));
        defaultAcsClient.getAcsResponse(deleteVideoRequest);
    }catch (ClientException e){
        e.printStackTrace();
    }

    }

    @Override
    public String getPlayAuthByVideoId(String id) {
      try {
          GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
          request.setVideoId(id);
          DefaultAcsClient defaultAcsClient = InitVodClient.initVodClient(vodProperties.getAccessKeyId(), vodProperties.getAccessKeySecret());
          GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
          response =  defaultAcsClient.getAcsResponse(request);
          return response.getPlayAuth();
      }catch (Exception e){
          e.printStackTrace();
      }
        return null;
    }


}


