package com.czw.mediasupportmodule.service;
import com.czw.mediasupportmodule.entity.Video_infoEntity;
import com.czw.mediasupportmodule.mapper.VideoMapper;
import com.czw.mediasupportmodule.response.ShowVideoInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);

    @Autowired
    private VideoMapper videoMapper;


    public List<ShowVideoInfoResponse> showVideoInfo(){
        LOG.info("开始查询视频信息");
        List<ShowVideoInfoResponse> showVideoInfoResponseList = new ArrayList<>();
        List<Video_infoEntity> videoInfoEntityList=videoMapper.showVideoInfo();
        LOG.info("aaaaaaaaaaa");
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
}
