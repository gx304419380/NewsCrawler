package com.fly.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XXX
 * @since 2018-06-07
 */
public class NewsUtils {

    public static String getImageFromContent(String content) {
        String image = null;
        String imgRegex = "(<img.*src\\s*=\\s*(.*?)[^>]*?>)";
        Pattern imgPattern = Pattern.compile(imgRegex, Pattern.CASE_INSENSITIVE);
        Matcher imgMatcher = imgPattern.matcher(content);
        // 找到img标签
        if (imgMatcher.find()) {
            String img = imgMatcher.group();
            // 匹配<img>中的src数据
            Matcher srcMatcher = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            image = srcMatcher.find() ? srcMatcher.group(1) : null;
        }
        return image;
    }

    public static String getSourceFromPathVariable(String pathVariable) {
        switch (pathVariable) {
            case "toutiao" :
                return "今日头条";
            case "neteasy" :
                return "网易";
            default:
                return null;
        }
    }

}
