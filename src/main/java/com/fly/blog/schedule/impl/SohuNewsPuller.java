package com.fly.blog.schedule.impl;

import com.fly.blog.entity.News;
import com.fly.blog.schedule.NewsPuller;
import com.fly.blog.service.NewsService;
import com.fly.blog.util.NewsUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;

/**
 * @author XXX
 * @since 2018-06-07
 */
@Component("sohuNewsPuller")
public class SohuNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(SohuNewsPuller.class);
    @Value("${news.sohu.url}")
    private String url;
    @Autowired
    private NewsService newsService;

    @Override
    public void pullNews() {
        logger.info("开始拉取搜狐新闻！");
        // 1.获取首页
        Document html= null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", url);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html.select("div.focus-news")
                .select("div.list16")
                .select("li")
                .select("a");


        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        for (Element a : newsATags) {
            String url = a.attr("href");
            String title = a.attr("title");
            News n = new News();
            n.setSource("搜狐");
            n.setUrl(url);
            n.setTitle(title);
            n.setCreateDate(new Date());
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("开始抽取搜狐新闻内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Element newsContent = newsHtml.select("div#article-container")
                        .select("div.main")
                        .select("div.text")
                        .first();
                String title = newsContent.select("div.text-title").select("h1").text();
                String content = newsContent.select("article.article").first().toString();
                String image = NewsUtils.getImageFromContent(content);

                news.setTitle(title);
                news.setContent(content);
                news.setImage(image);
                newsService.saveNews(news);
            } catch (Exception e) {
                logger.error("新闻抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });
    }
}
