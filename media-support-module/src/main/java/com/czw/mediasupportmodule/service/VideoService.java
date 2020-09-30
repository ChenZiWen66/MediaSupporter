package com.czw.mediasupportmodule.service;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.czw.mediasupportmodule.entity.Video_infoEntity;
import com.czw.mediasupportmodule.mapper.VideoMapper;
import com.czw.mediasupportmodule.response.ShowVideoInfoResponse;
import com.czw.mediasupportmodule.response.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class VideoService {
    private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);
//    字典:<'文件名':<'分片序号','分片路径'>>
    private Map<String,Map<String,String>> file_slicePath_dictionary= new TreeMap<>();
    String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    String endpointWithoutHttp = "oss-cn-shanghai.aliyuncs.com";
    String accessKeyId = "LTAI4GKeGZb44FVAQCD7y1hb";
    String accessKeySecret = "Xns4PoPT0XSbaACmOYx523RtQd3ZwK";

    @Autowired
    private VideoMapper videoMapper;


    public List<ShowVideoInfoResponse> showVideoInfo(){
        LOG.info("开始查询视频信息");
        List<ShowVideoInfoResponse> showVideoInfoResponseList = new ArrayList<>();
        List<Video_infoEntity> videoInfoEntityList=videoMapper.showVideoInfo();
        for (Video_infoEntity videoInfoEntity : videoInfoEntityList){
            ShowVideoInfoResponse showVideoInfoResponse = new ShowVideoInfoResponse();
            showVideoInfoResponse.setImgSrc(videoInfoEntity.getImgSrc());
            showVideoInfoResponse.setDescribe(videoInfoEntity.getVideoDescribe());
            showVideoInfoResponse.setVideoTitle(videoInfoEntity.getVideoTitle());
            showVideoInfoResponse.setVideoUrl(videoInfoEntity.getVideoUrl());
            showVideoInfoResponseList.add(showVideoInfoResponse);
        }
        LOG.info("查询视频信息成功");
        return showVideoInfoResponseList;
    }

    public UploadFileResponse UpLoadFile(MultipartFile file, String currentIndex, String totalIndex, String filename) throws IOException {
        LOG.info("开始接收文件["+filename+"]分片");
        String sliceName = file.getOriginalFilename();
        String fullPath = "C:\\Users\\adm\\Desktop\\" + UUID.randomUUID() + sliceName;
        File dest = new File(fullPath);
        file.transferTo(dest);
//        分片的字典
        Map<String,String> slicePath_dictionary=new TreeMap<>();
//        如果已经有字典就获取它
        if(file_slicePath_dictionary.containsKey(filename)){
            slicePath_dictionary=file_slicePath_dictionary.get(filename);
        }
        slicePath_dictionary.put(currentIndex, fullPath);
        file_slicePath_dictionary.put(filename,slicePath_dictionary);
        UploadFileResponse uploadFileResponse=new UploadFileResponse();
        if (slicePath_dictionary.size() == Integer.parseInt(totalIndex)) {
            LOG.info("文件["+filename+"]所有分片接收成功");
            String fileURL_OSS = combine(filename);
            uploadFileResponse.setFileUrl_OSS(fileURL_OSS);
            LOG.info("成功上传文件["+filename+"]至OSS");
        }else {
            uploadFileResponse.setFileUrl_OSS("11111111111111");
        }
        return uploadFileResponse;
    }
    /**
     * 上传到阿里云OSS
     * @param filename:文件名
     * @throws IOException
     * @return 返回阿里云OSS的URL
     */
    public String combine(String filename) throws IOException {
        LOG.info("开始合并["+filename+"]文件分片");
        UUID uuid =UUID.randomUUID();
        File file = new File("C:\\Users\\adm\\Desktop\\"+uuid.toString()+filename);
        FileOutputStream outputStream = new FileOutputStream(file, true);
        byte[] bytes = new byte[3 * 1024 * 1024];
        int len;
        Map<String,String> slicePath_dictionary=file_slicePath_dictionary.get(filename);
        for (int i = 0; i < slicePath_dictionary.size(); i++) {
            File objFile = new File(slicePath_dictionary.get(String.valueOf(i)));
            FileInputStream inputStream = new FileInputStream(objFile);
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            inputStream.close();
            if (objFile.exists()) {
                objFile.delete();
            }
        }
        outputStream.close();
        file_slicePath_dictionary.remove(filename);

        LOG.info("开始上传文件["+filename+"]至OSS");
        //上传至阿里云OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest("czwhub", uuid+filename, new FileInputStream(file));
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        if(file.exists()){
            file.delete();
        }
        LOG.info("上传文件到成功:{}","http://czwhub."+endpointWithoutHttp+"//"+uuid+filename);
        return "http://czwhub."+endpointWithoutHttp+"//"+uuid+filename;
    }
}
