package com.fly.blog.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author XXX
 * @since 2018-06-04
 */
@Controller
@RequestMapping("/file")
@Api("文件上传测试")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
}
