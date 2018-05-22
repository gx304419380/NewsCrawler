package com.fly.blog.schedule.impl;

import com.fly.blog.dao.NewsDao;
import com.fly.blog.entity.News;
import com.fly.blog.schedule.NewsPuller;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author XXX
 * @since 2018-05-21
 */
@Component("toutiaoNewsPuller")
public class ToutiaoNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(ToutiaoNewsPuller.class);
    private static final String TOUTIAO_URL = "https://www.toutiao.com";

    @Autowired
    private NewsDao newsDao;
    private String url = "https://www.toutiao.com/ch/news_hot/";

    @Override
    public void pullNews() {
        try {
            logger.info("begin to pull toutiao's news");
            System.out.println("=====================HTML UNIT====================");
            Document html = getHtmlFromUrl(url, true);
            Map<String, News> newsMap = new HashMap<>();
            for (Element a : html.select("a[href~=/group/*]:not(.comment)")) {
                System.out.println(a);
                String href = TOUTIAO_URL + a.attr("href");
                String title = StringUtils.isNotBlank(a.select("p").text()) ?
                        a.select("p").text() : a.text();
                String image = a.select("img").attr("src");
                String content;
                Document contentHtml = getHtmlFromUrl(href, true);
                System.out.println(contentHtml);

                News news = newsMap.get(href);
                if (news == null) {
                    News n = new News();
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

        } catch (IOException e) {
            logger.error("==============get html error : {}=============", url);
            e.printStackTrace();
        }
    }
}
