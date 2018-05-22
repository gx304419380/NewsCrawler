package com.fly.blog.controller;

import com.fly.blog.schedule.NewsPuller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author XXX
 * @since 2018-05-13
 */
@RestController
@RequestMapping("/news")
@Api(value = "新闻拉取API")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    @Qualifier("netEasyNewsPuller")
    NewsPuller neteasyNewsPuller;
    @Autowired
    @Qualifier("toutiaoNewsPuller")
    NewsPuller toutiaoNewsPuller;

    @ApiOperation(value = "获取网易新闻")
    @GetMapping("/pull/neteasy")
    public void pullNeteasyNews() {
        neteasyNewsPuller.pullNews();
    }

    @ApiOperation(value = "获取今日头条新闻")
    @GetMapping("/pull/toutiao")
    public void pullToutiaoNews() {
        toutiaoNewsPuller.pullNews();
    }

}
