package com.fly.blog.schedule.impl;

import com.fly.blog.entity.News;
import com.fly.blog.schedule.NewsPuller;
import com.fly.blog.service.NewsService;
import com.fly.blog.util.NewsUtils;
import org.jsoup.nodes.Document;
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
 * @since 2018-04-12
 */
@Component("netEasyNewsPuller")
public class NetEasyNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(NetEasyNewsPuller.class);
    @Value("${news.neteasy.url}")
    private String url;
    @Autowired
    private NewsService newsService;
    @Override
    public void pullNews() {
        logger.info("开始拉取网易热门新闻！");
        // 1.获取首页
        Document html= null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取网易新闻首页失败: {}=============", url);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取指定标签
        Elements newsA = html.select("div#whole")
                .next("div.area-half.left")
                .select("div.tabContents")
                .first()
                .select("tbody > tr")
                .select("a[href~=^http://news.163.com.*]");

        // 3.从标签中抽取信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        newsA.forEach(a -> {
            String url = a.attr("href");
            News n = new News();
            n.setSource("网易");
            n.setUrl(url);
            n.setCreateDate(new Date());
            newsSet.add(n);
        });

        // 4.根据url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("开始抽取新闻内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Elements newsContent = newsHtml.select("div#endText");
                Elements titleP = newsContent.select("p.otitle");
                String title = titleP.text();
                title = title.substring(5, title.length() - 1);
                String image = NewsUtils.getImageFromContent(newsContent.toString());

                news.setTitle(title);
                news.setContent(newsContent.toString());
                news.setImage(image);
                newsService.saveNews(news);
                logger.info("抽取网易新闻《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("新闻抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });
        logger.info("网易新闻拉取完成！");


    }
}
