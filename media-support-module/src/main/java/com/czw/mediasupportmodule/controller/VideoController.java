package com.czw.mediasupportmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {
    @RequestMapping("/showInfo")
    public String showVideoInfoList(){
        return "aaaaaaaaaaaaaaaaaaaaa";
    }
}
