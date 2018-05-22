package com.fly.blog.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author XXX
 * @since 2018-04-12
 */
@Component
public class PullContentJob {

    private static final Logger logger = LoggerFactory.getLogger(PullContentJob.class);
    @Autowired
    List<NewsPuller> newsPullers;

//    @Scheduled(fixedRate = 3600000)
    public void pullContent() {
        newsPullers.forEach(NewsPuller::pullNews);
    }

}
