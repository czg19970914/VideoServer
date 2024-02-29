package com.example.videoserver.controller;

import com.example.videoserver.ConfigParams;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class VideoPlayerController {
    /**使用: http://127.0.0.1:8080/video?file_name=/Bilibili/洛丽塔大哥/1.mp4 访问*/
    @GetMapping(value = "/video")
    public ResponseEntity<InputStreamResource> getVideo(
            @RequestParam(value = "file_name", required=false) String file_name
            ) throws IOException {
        if(file_name == null || file_name.isEmpty()){
            return null;
        }
        String videoFilePath = ConfigParams.ROOT_DIR + file_name;
        try {
            File videoFile = new File(videoFilePath);
            System.out.println(videoFilePath);
            InputStream inputStream = new FileInputStream(videoFile);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("video/mp4"));

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("视频文件打开有误");
        } finally {

        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**使用: http://127.0.0.1:8080/videoPlay?file_name=/Bilibili/洛丽塔大哥/1.mp4 访问*/
    /**专门给android端的MediaPlayer访问的**/
    @GetMapping(value = "/videoPlay")
    public ResponseEntity<Resource> videoPlay(
            @RequestParam(value = "file_name", required=false) String file_name
    ) {
        if(file_name == null || file_name.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String videoFilePath = ConfigParams.ROOT_DIR + file_name;
        System.out.println(videoFilePath);
        Resource video = new FileSystemResource(videoFilePath);

        return ResponseEntity.ok().
                contentType(MediaType.parseMediaType("video/mp4")).
                body(video);
    }
}
