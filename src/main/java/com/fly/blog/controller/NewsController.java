package com.fly.blog.controller;

import com.fly.blog.entity.News;
import com.fly.blog.entity.NewsExample;
import com.fly.blog.schedule.NewsPuller;
import com.fly.blog.service.NewsService;
import com.fly.blog.util.NewsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "获取{source}新闻")
    @GetMapping("/{source}")
    public List<News> getToutiaoNews(@RequestParam Integer page, @RequestParam Integer pageSize, @PathVariable String source) {
        NewsExample example = new NewsExample();
        example.createCriteria().andSourceEqualTo(NewsUtils.getSourceFromPathVariable(source));
        example.setOrderByClause("create_date desc");
        return newsService.searchNewsForPage(page, pageSize, example);
    }

    @ApiOperation("获取{source}新闻总数")
    @GetMapping("/{source}/count")
    public Long getToutiaoCount(@PathVariable String source) {
        NewsExample example = new NewsExample();
        example.createCriteria().andSourceEqualTo(NewsUtils.getSourceFromPathVariable(source));
        return newsService.countByExample(example);
    }

    @ApiOperation(value = "获取所有新闻")
    @GetMapping
    public List<News> getNews(@RequestParam Integer page, @RequestParam Integer pageSize) {
        NewsExample example = new NewsExample();
        example.createCriteria();
        example.setOrderByClause("create_date desc");
        return newsService.searchNewsForPage(page, pageSize, example);
    }

    @ApiOperation("获取新闻总数")
    @GetMapping("/count")
    public Long getCount() {
        NewsExample example = new NewsExample();
        example.createCriteria();
        return newsService.countByExample(example);
    }

}
