package com.czw.mediasupportmodule.controller;

import com.czw.mediasupportmodule.response.ShowVideoInfoResponse;
import com.czw.mediasupportmodule.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class VideoController {
    private static final Logger LOG = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService videoService;

    @RequestMapping("/showInfo")
    public List<ShowVideoInfoResponse> showVideoInfoList() {
        return videoService.showVideoInfo();
    }
}
