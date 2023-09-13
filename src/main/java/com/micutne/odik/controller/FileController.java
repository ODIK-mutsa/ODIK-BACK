package com.micutne.odik.controller;

import com.micutne.odik.config.FileConfig;
import com.micutne.odik.service.FileService;
import com.micutne.odik.utils.file.FileRequest;
import com.micutne.odik.utils.file.FileResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * 이미지 불러오기 (경로 지정)
     */
    @GetMapping("{category}/{file}")
    public Resource getImage(@PathVariable("category") String category, @PathVariable("file") String file) throws IOException {
        String folder = FileConfig.findPath(category);
        //로컬 경로
//        String[] url = System.getProperty("user.dir").split(":");
//        Resource resource = new UrlResource("file:" + url[1] + folder + "/" + file);
        //서버 경로
        String ubuntuUrl = "/home/ubuntu";
        Resource resource = new FileSystemResource(folder + "/" + file);
        return resource;
    }

    //이미지 저장하기
    @PostMapping("")
    public FileResultResponse saveImage(Authentication authentication, @ModelAttribute FileRequest request, MultipartFile[] images) {
        return fileService.saveFile(request, images, authentication.getPrincipal().toString());
    }
}
