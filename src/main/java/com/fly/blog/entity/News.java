package com.fly.blog.entity;

import com.fly.blog.util.NewsUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class News {
    private Integer id;

    private String title;

    private String url;

    private String image;

    private Date createDate;

    private Date newsDate;

    private String source;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return StringUtils.isNotBlank(image) ? image : "/img/6.jpg";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        // 去除网页中的所有标签，然后取出140个字符
        String summary = content.replaceAll("</?[^>]+>", "")
                .replaceAll("\\s*|\t|\r|\n", "");
        // 值得注意，如果新闻太短，小于140个字符，则有多少截取多少！！！
        summary = summary.substring(0, summary.length() > 140 ? 140 : summary.length()) + "...";
        return summary;
    }

    public String getLargeImage() {
        String largeImage = NewsUtils.getImageFromContent(content);
        return StringUtils.isNotBlank(largeImage) ? largeImage : image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", url=").append(url);
        sb.append(", image=").append(image);
        sb.append(", createDate=").append(createDate);
        sb.append(", newsDate=").append(newsDate);
        sb.append(", source=").append(source);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}