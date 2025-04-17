package com.xingye.data.content.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
public class IndexController {

    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return "请选择要上传的图片";
        }
        try {

            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + "." + getFileExtension(file.getOriginalFilename());
            File dest = new File("./data/image" + File.separator + fileName);
            // 保存文件到本地
            file.transferTo(dest);
            return "图片上传成功，保存路径：" + dest.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "图片上传失败：" + e.getMessage();
        }
    }

    // 获取文件扩展名
    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return "";
        }
        return fileName.substring(lastIndex + 1);
    }
}
