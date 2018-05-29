package com.fly.blog.service;

import com.fly.blog.entity.News;
import com.fly.blog.entity.NewsExample;

import java.util.List;

/**
 * @author XXX
 * @since 2018-05-22
 */
public interface NewsService {

    int saveNews(News news);

    List<News> searchNewsForPage(int page, int pageSize, NewsExample example);

}
