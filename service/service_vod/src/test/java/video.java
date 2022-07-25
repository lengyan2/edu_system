import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class video {
    public static void main(String[] args) throws Exception {
            getplatAuth();


    }
    public static void getPlayurl() throws Exception{
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("de26008fcabc4fee916a651655c2cdf2");
        DefaultAcsClient defaultAcsClient = test.initVodClient("LTAI5t6oV8zBYuRfccajo2Nj", "Aq1ZKNMkGW9Ul9C5kLRxpBEA2e3xIH");
        GetPlayInfoResponse response = defaultAcsClient.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    public static void getplatAuth() throws  Exception{
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("59fef00642a14a5b9f69f8fd40ee2085");
        DefaultAcsClient defaultAcsClient = test.initVodClient("LTAI5t6oV8zBYuRfccajo2Nj", "Aq1ZKNMkGW9Ul9C5kLRxpBEA2e3xIH");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        response =  defaultAcsClient.getAcsResponse(request);
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }
}
