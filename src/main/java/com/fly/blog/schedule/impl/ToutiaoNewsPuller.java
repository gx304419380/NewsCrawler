package com.fly.blog.schedule.impl;


import com.fly.blog.entity.News;
import com.fly.blog.schedule.NewsPuller;
import com.fly.blog.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XXX
 * @since 2018-05-21
 */
@Component("toutiaoNewsPuller")
public class ToutiaoNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(ToutiaoNewsPuller.class);
    private static final String TOUTIAO_URL = "https://www.toutiao.com";

    @Autowired
    private NewsService newsService;
    @Value("${news.toutiao.url}")
    private String url;

    @Override
    public void pullNews() {
        logger.info("开始拉取今日头条热门新闻！");
        // 1.load html from url
        Document html = null;
        try {
            html = getHtmlFromUrl(url, true);
        } catch (Exception e) {
            logger.error("获取今日头条主页失败！");
            e.printStackTrace();
            return;
        }

        // 2.parse the html to news information and load into POJO
        Map<String, News> newsMap = new HashMap<>();
        for (Element a : html.select("a[href~=/group/.*]:not(.comment)")) {
            logger.debug("标签a: \n{}", a);
            String href = TOUTIAO_URL + a.attr("href");
            String title = StringUtils.isNotBlank(a.select("p").text()) ?
                    a.select("p").text() : a.text();
            String image = a.select("img").attr("src");

            News news = newsMap.get(href);
            if (news == null) {
                News n = new News();
                n.setSource("toutiao");
                n.setUrl(href);
                n.setCreateDate(new Date());
                n.setImage(image);
                n.setTitle(title);
                newsMap.put(href, n);
            } else {
                if (a.hasClass("img-wrap")) {
                    news.setImage(image);
                } else if (a.hasClass("title")) {
                    news.setTitle(title);
                }
            }
        }

        logger.info("今日头条新闻标题拉取完成!");
        logger.info("开始拉取新闻内容...");
        newsMap.values().parallelStream().forEach(news -> {
            logger.info("===================={}====================", news.getTitle());
            Document contentHtml = null;
            try {
                contentHtml = getHtmlFromUrl(news.getUrl(), true);
            } catch (Exception e) {
                logger.error("获取新闻《{}》内容失败！", news.getTitle());
                return;
            }
            Elements scripts = contentHtml.getElementsByTag("script");
            scripts.forEach(script -> {
                String regex = "articleInfo: \\{\\s*[\\n\\r]*\\s*title: '.*',\\s*[\\n\\r]*\\s*content: '(.*)',";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(script.toString());
                if (matcher.find()) {
                    logger.info("content: {}", matcher.group(1));
                    news.setContent(matcher.group(1));
                }
            });
        });
        logger.info("新闻内容拉取完成!");
        newsMap.values()
                .stream()
                .filter(news -> StringUtils.isNotBlank(news.getContent()) && !news.getContent().equals("null"))
                .forEach(newsService::saveNews);

    }
}
