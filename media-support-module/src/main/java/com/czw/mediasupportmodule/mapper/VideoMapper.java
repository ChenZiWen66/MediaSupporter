package com.czw.mediasupportmodule.mapper;

import com.czw.mediasupportmodule.entity.InsertVideoInfoEntity;
import com.czw.mediasupportmodule.entity.Video_infoEntity;

import java.util.List;

public interface VideoMapper {
    List<Video_infoEntity> showVideoInfo();
    List<Video_infoEntity> getVideoInfo(int currentIndex,int pageSize);
    int getInfoCount();
    int insertVideoInfo(InsertVideoInfoEntity insertVideoInfoEntity);
}
