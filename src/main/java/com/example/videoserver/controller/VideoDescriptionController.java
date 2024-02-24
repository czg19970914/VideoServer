package com.example.videoserver.controller;

import com.example.videoserver.ConfigParams;
import com.example.videoserver.entities.VideoDescriptionEntity;
import com.example.videoserver.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VideoDescriptionController {
    public ResponseEntity<Map<String, Map<String, List<VideoDescriptionEntity>>>> getVideoDescriptionMap() {
        if(!FileUtils.isDirectoryExist(ConfigParams.ROOT_DIR)) {
            return null;
        }
        Map<String, Map<String, List<VideoDescriptionEntity>>> descriptionMap = new HashMap<>();
        File rootFile = new File(ConfigParams.ROOT_DIR);
        // 第一层的文件夹，是bar上的名字
        File[] firstFileArr = rootFile.listFiles();
        return null;
    }

    @GetMapping(value = "/descriptionNameList")
    public ResponseEntity<List<String>> getVideoDescriptionBarNames() {
        if(!FileUtils.isDirectoryExist(ConfigParams.ROOT_DIR)) {
            return null;
        }
        List<String> videoDescriptionBarNames = new ArrayList<>();
        File rootFile = new File(ConfigParams.ROOT_DIR);
        // 第一层的文件夹，是bar上的名字
        File[] firstFileArr = rootFile.listFiles();
        if(firstFileArr == null) {
            return null;
        }
        for(File f : firstFileArr) {
            videoDescriptionBarNames.add(f.getName());
        }
        return new ResponseEntity<>(videoDescriptionBarNames, HttpStatus.OK);
    }
}
