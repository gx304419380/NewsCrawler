package com.fly.blog.schedule;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author XXX
 * @since 2018-04-12
 */

public interface NewsPuller {

    void pullNews();

    default Document getHtmlFromUrl(String url, boolean useHtmlUnit) throws IOException {
        if (!useHtmlUnit) {
            return Jsoup.connect(url)
                    //模拟火狐浏览器
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .get();
        } else {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getOptions().setCssEnabled(false);
//            webClient.getOptions().setActiveXNative(false);
//            webClient.getOptions().setCssEnabled(false);
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//            webClient.getOptions().setTimeout(5000);
            HtmlPage rootPage = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(8000);
            String htmlString = rootPage.asXml();
            return Jsoup.parse(htmlString);
        }
    }

}
