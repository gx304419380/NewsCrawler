package com.fly.blog.schedule;

/**
 * @author XXX
 * @since 2018-04-12
 */
@FunctionalInterface
public interface PullStrategy {
    String pull(String html);
}
