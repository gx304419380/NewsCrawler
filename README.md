# NewsCrawler
this is a crowler project built for crawling news from 今日头条 and 网易.

springboot and mybatis are adopted respectively for web layer and persistency layer.

the major utils used by this project is: jsoup, htmlUnit and java regex matcher

the jsoup is perfect for extracting key content from an HTML page, 

the htmlUtil is used for crawling an ajax-loading page (for example, 今日头条) rather than a static html page,

and some times we need to analysis some string of javascript to get information, so the java regex matcher is also adopted.

