package com.fly.blog.schedule.impl;

import com.fly.blog.entity.News;
import com.fly.blog.schedule.NewsPuller;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author XXX
 * @since 2018-04-12
 */
@Component("netEasyNewsPuller")
public class NetEasyNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(NetEasyNewsPuller.class);
    @Value("${news.neteasy.url}")
    private String url;

    @Override
    public void pullNews() {

        Document html= null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取网易新闻首页失败: {}=============", url);
            e.printStackTrace();
            return;
        }
        Elements newsA = html.select("div#whole")
                .next("div.area-half.left")
                .select("div.tabContents")
                .first()
                .select("tbody > tr")
                .select("a[href~=^http://news.163.com.*]");
        HashSet<News> newsSet = new HashSet<>();

        newsA.forEach(a -> {
            String url = a.attr("href");
            News n = new News();
            n.setSource("neteasy");
            n.setUrl(url);
            n.setCreateDate(new Date());
            newsSet.add(n);
        });

        newsSet.forEach(news -> {
            logger.info("开始抽取新闻内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Elements newsContent = newsHtml.select("div#endText");
                Elements titleP = newsContent.select("p.otitle");
                String title = titleP.text();
                title = title.substring(5, title.length() - 1);
                news.setTitle(title);

                System.out.println(title);
                System.out.println(newsContent);

            } catch (Exception e) {
                logger.error("新闻抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });



    }
}
