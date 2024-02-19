package com.example.videoserver.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class VideoPlayerController {
    @GetMapping(value = "/video")
    public ResponseEntity<InputStreamResource> getVideo() throws IOException {
//        ClassPathResource videoFile = new ClassPathResource("D:\\apache-tomcat-8.5.93\\webapps\\videos\\Bilibili\\洛丽塔大哥\\2.mp4");
        File videoFile = new File("D:\\apache-tomcat-8.5.93\\webapps\\videos\\Bilibili\\洛丽塔大哥\\2.mp4");
        InputStream inputStream = new FileInputStream(videoFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("video/mp4"));

        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
    }
}
