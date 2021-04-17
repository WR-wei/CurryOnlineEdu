package com.curry.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.curry.oss.service.OssService;
import com.curry.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author curry30
 * @create 2021-04-01 11:47
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传的文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String filename = file.getOriginalFilename();

            //在文件名中添加随机唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            filename = uuid + filename;

            //把文件按日期进行分类 2021/03/30
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            filename = datePath + "/" + filename;
            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;
            return url;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
