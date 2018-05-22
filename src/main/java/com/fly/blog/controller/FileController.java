package com.fly.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author XXX
 * @since 2018-04-11
 */
@Controller
@RequestMapping("/files")
@Api("文件上传下载API")
public class FileController {

    @ApiOperation("文件上传测试")
    @PostMapping("/")
    public String handleFileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }
}
