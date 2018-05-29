package com.fly.blog.controller;

import com.fly.blog.entity.News;
import com.fly.blog.entity.NewsExample;
import com.fly.blog.schedule.NewsPuller;
import com.fly.blog.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
    private NewsPuller neteasyNewsPuller;
    @Autowired
    @Qualifier("toutiaoNewsPuller")
    private NewsPuller toutiaoNewsPuller;
    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "爬虫拉取网易新闻")
    @GetMapping("/pull/neteasy")
    public void pullNeteasyNews() {
        neteasyNewsPuller.pullNews();
    }

    @ApiOperation(value = "爬虫拉取今日头条新闻")
    @GetMapping("/pull/toutiao")
    public void pullToutiaoNews() {
        toutiaoNewsPuller.pullNews();
    }

    @ApiOperation(value = "获取今日头条新闻")
    @GetMapping("/toutiao")
    public List<News> getToutiaoNews(@RequestParam Integer page, @RequestParam Integer pageSize) {
        NewsExample example = new NewsExample();
        example.createCriteria().andSourceEqualTo("toutiao");
        example.setOrderByClause("create_date desc");
        return newsService.searchNewsForPage(page, pageSize, example);
    }

}
