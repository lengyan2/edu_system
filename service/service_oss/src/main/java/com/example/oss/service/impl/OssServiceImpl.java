package com.example.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.example.oss.service.OssService;
import com.example.oss.utils.ConstantProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@ComponentScan("com.example.utils")
public class OssServiceImpl implements OssService {
    @Autowired
    ConstantProperties constantProperties;
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = constantProperties.getEndpoint();
        String accessKeyId = constantProperties.getAccessKeyId();
        String accessKeySecret = constantProperties.getAccessKeySecret();
        String bucketName = constantProperties.getBucketname();
        // 添加随机值 文件名防止覆盖
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String objectName = uuid + file.getOriginalFilename(); // 上传到oss文件路径和文件名称
        // 添加日期作为文件夹
        String time = new DateTime().toString("yyyy/MM/dd");
        objectName = time + "/" + objectName;
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
                String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
                return url;
            }
            return null;

        }


    }

    @Override
    public String uploadSubjectExcel(MultipartFile file) {
        String endpoint = constantProperties.getEndpoint();
        String accessKeyId = constantProperties.getAccessKeyId();
        System.out.println(endpoint);
        System.out.println(accessKeyId);
        String accessKeySecret = constantProperties.getAccessKeySecret();
        String bucketName = constantProperties.getBucketname();
        // 添加随机值 文件名防止覆盖
        String objectName =  "subject/"+file.getOriginalFilename(); // 上传到oss文件路径和文件名称
        // 添加日期作为文件夹

        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
                String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
                return url;
            }
            return null;

        }

    }
}
