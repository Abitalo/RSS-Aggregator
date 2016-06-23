package com.abitalo.www.rss_aggregator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zela on 2016/6/10.
 */
public class RSSContentFilter {
    //得到网页中图片的地址
    public static String getAbstract(String description) {
        description = htmlRemoveTag(description);
        return description;
    }

    private static String htmlRemoveTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";

        Pattern scriptPattern;
        Matcher scriptMatcher;
        Pattern stylePattern;
        Matcher styleMatcher;
        Pattern htmlPattern;
        Matcher htmlMatcher;

        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            scriptPattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            scriptMatcher = scriptPattern.matcher(htmlStr);
            htmlStr = scriptMatcher.replaceAll(""); // 过滤script标签

            stylePattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            styleMatcher = stylePattern.matcher(htmlStr);
            htmlStr = styleMatcher.replaceAll(""); // 过滤style标签

            htmlPattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            htmlMatcher = htmlPattern.matcher(htmlStr);
            htmlStr = htmlMatcher.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;
    }

    //得到网页中图片的地址
    public static String getImgSrc(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src=\"?(.*?)" +
                    "(\"|>|\\s+)").matcher(img); //匹配src
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        if (pics.size() > 0) return pics.get(0);
        else return null;
    }
}
