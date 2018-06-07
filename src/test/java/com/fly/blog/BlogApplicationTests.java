package com.fly.blog;

import com.fly.blog.dao.NewsDao;
import com.fly.blog.entity.News;
import com.fly.blog.entity.NewsExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private NewsDao newsDao;

    @Test
    public void replaceContentWithHTMLTags() throws ClassNotFoundException {
        NewsExample newsExample = new NewsExample();
        List<News> newsList = newsDao.selectByExampleWithBLOBs(newsExample);
        newsList.forEach(news -> {
            String content = news.getContent()
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                    .replace("&quot;", "\"")
                    .replace("&#x3D;", "=");
            news.setContent(content);
            System.out.println(content);
            newsDao.updateByPrimaryKeyWithBLOBs(news);
        });
    }

    @Test
    public void replaceSource() throws ClassNotFoundException {
        NewsExample newsExample = new NewsExample();
        List<News> newsList = newsDao.selectByExample(newsExample);
        newsList.forEach(news -> {
            String oldSource = news.getSource();
            news.setSource(oldSource.equals("toutiao") ? "今日头条" : "网易");
            newsDao.updateByPrimaryKey(news);
        });
    }

}
