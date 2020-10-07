package com.czw.mediasupportmodule.controller;

import com.czw.mediasupportmodule.response.ShowVideoInfoResponse;
import com.czw.mediasupportmodule.response.UploadFileResponse;
import com.czw.mediasupportmodule.response.VideoInfoCountResponse;
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

    /**
     * 查询视频信息
     *
     * @return
     */
    @RequestMapping("/showInfo")
    public List<ShowVideoInfoResponse> showVideoInfoList() {
        return videoService.showVideoInfo();
    }

    @RequestMapping("/getCount")
    public VideoInfoCountResponse getInfoCount(){
        return videoService.getCount();
    }

    /**
     * 通过post请求，分页获取视频信息
     * @param currentPage ：当前页面数
     * @param pageSize :页面大小
     * @return
     */
    @PostMapping("/getInfo")
    public List<ShowVideoInfoResponse> getVideoInfoList(@RequestParam(value = "currentPage") int currentPage, @RequestParam(value = "pageSize") int pageSize) {
        return videoService.getVideoInfo(currentPage,pageSize);
    }

    /**
     * 1.上传文件至OSS上
     * 1.1上传封面图片至OSS上
     * 1.2上传视频至OSS上
     * 2.在数据库添加对应的信息
     * [id imgSrc videoDescribe videoTitle videoUrl]
     *
     * @return
     */
    @PostMapping("/uploadfile")
    public UploadFileResponse uploadFile2OSS(@RequestParam(value = "file") MultipartFile file,
                                             @RequestParam(value = "currentIndex") String currentIndex,
                                             @RequestParam(value = "totalIndex") String totalIndex,
                                             @RequestParam(value = "fileName") String filename) throws IOException {
        return videoService.UpLoadFile(file, currentIndex, totalIndex, filename);
    }

    /**
     * 插入视频信息至数据库
     *
     * @param title
     * @param imgUrl
     * @param videoUrl
     * @param describe
     * @return
     */
    @PostMapping("/insertVideoInfo")
    public String InsertVideoInfo(@RequestParam(value = "title") String title,
                                  @RequestParam(value = "imgUrl") String imgUrl,
                                  @RequestParam(value = "videoUrl") String videoUrl,
                                  @RequestParam(value = "describe") String describe) {

        return videoService.InsertVideoInfo(title, imgUrl, videoUrl, describe);
    }
}
