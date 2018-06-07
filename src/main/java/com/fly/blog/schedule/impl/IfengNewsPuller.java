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
@Component("ifengNewsPuller")
public class IfengNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(IfengNewsPuller.class);
    @Value("${news.ifeng.url}")
    private String url;
    @Autowired
    private NewsService newsService;

    @Override
    public void pullNews() {
        logger.info("开始拉取凤凰新闻！");
        // 1.获取首页
        Document html= null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取凤凰首页失败: {} =============", url);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html.select("div#headLineDefault")
                .select("ul.FNewMTopLis")
                .select("li")
                .select("a");

        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        for (Element a : newsATags) {
            String url = a.attr("href");
            String title = a.text();
            News n = new News();
            n.setSource("凤凰");
            n.setUrl(url);
            n.setTitle(title);
            n.setCreateDate(new Date());
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.parallelStream().forEach(news -> {
            logger.info("开始抽取凤凰新闻《{}》内容：{}", news.getTitle(), news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Elements contentElement = newsHtml.select("div#main_content");
                if (contentElement.isEmpty()) {
                    contentElement = newsHtml.select("div#yc_con_txt");
                }
                if (contentElement.isEmpty())
                    return;
                String content = contentElement.toString();
                String image = NewsUtils.getImageFromContent(content);
                news.setContent(content);
                news.setImage(image);
                newsService.saveNews(news);
                logger.info("抽取凤凰新闻《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("凤凰新闻抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });

        logger.info("凤凰新闻抽取完成！");
    }
}
