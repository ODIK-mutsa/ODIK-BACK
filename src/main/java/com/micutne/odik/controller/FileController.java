package com.micutne.odik.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class FileController {
    /**
     * 이미지 불러오기 (경로 지정)
     */
    @GetMapping("/file/{folder}/{file}")
    public Resource getImage(@PathVariable("folder") String folder, @PathVariable("file") String file) throws IOException {
        String[] url = System.getProperty("user.dir").split(":");
        Resource resource = new UrlResource("file:" + url[1] + "/file/" + folder + "/" + file);
        log.info(resource.getFile().getAbsolutePath());
        return resource;
    }
}
