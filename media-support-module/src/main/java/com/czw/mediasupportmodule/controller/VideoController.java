package com.czw.mediasupportmodule.controller;

import com.czw.mediasupportmodule.response.ShowVideoInfoResponse;
import com.czw.mediasupportmodule.response.UploadFileResponse;
import com.czw.mediasupportmodule.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class VideoController {
    private static final Logger LOG = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService videoService;

    @RequestMapping("/showInfo")
    @CrossOrigin
    public List<ShowVideoInfoResponse> showVideoInfoList() {
        return videoService.showVideoInfo();
    }

    /**
     * 1.上传文件至OSS上
     *  1.1上传封面图片至OSS上
     *  1.2上传视频至OSS上
     * 2.在数据库添加对应的信息
     * [id imgSrc videoDescribe videoTitle videoUrl]
     * @return
     */
    @PostMapping("/uploadfile")
    @CrossOrigin
    public UploadFileResponse uploadFile2OSS(@RequestParam(value = "file") MultipartFile file,
                                             @RequestParam(value = "currentIndex") String currentIndex,
                                             @RequestParam(value = "totalIndex") String totalIndex,
                                             @RequestParam(value = "fileName") String filename) throws IOException {
        return videoService.UpLoadFile(file,currentIndex,totalIndex,filename);
    }
}
