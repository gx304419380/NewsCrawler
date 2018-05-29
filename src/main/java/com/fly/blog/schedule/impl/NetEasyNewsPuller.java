package com.fly.blog.schedule.impl;

import com.fly.blog.schedule.NewsPuller;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
        try {
            Document html = getHtmlFromUrl(url, false);
            Elements titles = html.getElementsByTag("h3");
            titles.forEach(System.out::println);
        } catch (Exception e) {
            logger.error("==============get html error : {}=============", url);
            e.printStackTrace();
        }
    }
}
