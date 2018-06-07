package com.fly.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XXX
 * @since 2018-06-07
 */
public class NewsUtils {

    public static String getTextFromContent(String content) {
        String scriptRegex = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
        String styleRegex = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
        String htmlTagRegex = "<[^>]+>"; // HTML tag
        String spaceRegex = "\\s+|\t|\r|\n";// other characters

        Pattern scriptPattern = Pattern.compile(scriptRegex, Pattern.CASE_INSENSITIVE);
        Matcher scriptMatcher = scriptPattern.matcher(content);
        content = scriptMatcher.replaceAll("");

        Pattern stylePattern = Pattern.compile(styleRegex, Pattern.CASE_INSENSITIVE);
        Matcher styleMatcher = stylePattern.matcher(content);
        content = styleMatcher.replaceAll("");

        Pattern htmlTagPattern = Pattern.compile(htmlTagRegex, Pattern.CASE_INSENSITIVE);
        Matcher htmlTagMatcher = htmlTagPattern.matcher(content);
        content = htmlTagMatcher.replaceAll("");

        Pattern spacePattern = Pattern.compile(spaceRegex, Pattern.CASE_INSENSITIVE);
        Matcher spaceMatcher = spacePattern.matcher(content);
        content = spaceMatcher.replaceAll(" ");

        return content;
    }

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
            case "sohu" :
                return "搜狐";
            case "ifeng" :
                return "凤凰";
            case "sina" :
                return "新浪";
            default:
                return null;
        }
    }

}
