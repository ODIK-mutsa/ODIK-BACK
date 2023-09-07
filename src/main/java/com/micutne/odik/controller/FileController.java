package com.micutne.odik.controller;

import com.micutne.odik.config.FileConfig;
import com.micutne.odik.utils.file.FileRequest;
import com.micutne.odik.utils.file.FileResponse;
import com.micutne.odik.utils.file.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    /**
     * 이미지 불러오기 (경로 지정)
     */
    @GetMapping("{category}/{file}")
    public Resource getImage(@PathVariable("category") String category, @PathVariable("file") String file) throws IOException {
        String folder = FileConfig.findPath(category);
        log.info(folder);
        String[] url = System.getProperty("user.dir").split(":");
        Resource resource = new UrlResource("file:" + url[1] + folder + "/" + file);
        log.info(resource.getFile().getAbsolutePath());
        return resource;
    }

    @PostMapping("")
    public List<String> saveImage(@ModelAttribute FileRequest request,
                                  MultipartFile[] images) {
        List<FileResponse> result = ImageUtils.saveFiles(images, request.getCategory());
        return result.stream().map(FileResponse::getFileName).toList();
    }
}
