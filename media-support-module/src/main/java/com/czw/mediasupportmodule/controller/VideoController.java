package com.czw.mediasupportmodule.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.czw.mediasupportmodule.response.ShowVideoInfoResponse;
import com.czw.mediasupportmodule.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@RestController
@CrossOrigin
public class VideoController {
    private static final Logger LOG = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService videoService;
    private Map<String, String> sliceFullPathMap = new TreeMap<>();
    String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    String accessKeyId = "LTAI4GKeGZb44FVAQCD7y1hb";
    String accessKeySecret = "Xns4PoPT0XSbaACmOYx523RtQd3ZwK";

    @RequestMapping("/showInfo")
    public List<ShowVideoInfoResponse> showVideoInfoList() {
        return videoService.showVideoInfo();
    }

    /**
     * 1.上传文件至OSS上
     * 2.在数据库添加对应的信息
     * [id imgSrc videoDescribe videoTitle videoUrl]
     * @return
     */
    @PostMapping("/uploadfile")
    public String uploadFile2OSS(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "currentIndex") String currentIndex,
                                 @RequestParam(value = "totalIndex") String totalIndex,
                                 @RequestParam(value = "fileName") String filename) throws IOException {
        LOG.info("上传文件开始{}", file);
        LOG.info("文件名:" + file.getOriginalFilename());
        LOG.info("文件类型:" + file.getContentType());
        LOG.info("当前片{}", currentIndex);

        String fileName = file.getOriginalFilename();
        String fullPath = "C:\\Users\\adm\\Desktop\\" + UUID.randomUUID() + fileName;
        File dest = new File(fullPath);
        file.transferTo(dest);
//        添加分片路径到sliceFullPathList
        sliceFullPathMap.put(currentIndex, fullPath);
        LOG.info("Map大小:{}", sliceFullPathMap.size());
        if (sliceFullPathMap.size() == Integer.parseInt(totalIndex)) {
            combine(filename);
            LOG.info("*****************");
        }
        return "上传成功";
    }

    /**
     * 上传到阿里云OSS
     * @param filename:文件名
     * @throws IOException
     */
    public void combine(String filename) throws IOException {
        UUID uuid =UUID.randomUUID();
        File file = new File("C:\\Users\\adm\\Desktop\\"+uuid.toString()+filename);
        FileOutputStream outputStream = new FileOutputStream(file, true);
        byte[] bytes = new byte[3 * 1024 * 1024];
        int len;
        for (int i = 0; i < sliceFullPathMap.size(); i++) {
            File objFile = new File(sliceFullPathMap.get(String.valueOf(i)));
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
        sliceFullPathMap.clear();
        //上传至阿里云OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest("czwhub", uuid+filename, new FileInputStream(file));
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        LOG.info("上传成功");
        if(file.exists()){
            file.delete();
        }
    }
}
