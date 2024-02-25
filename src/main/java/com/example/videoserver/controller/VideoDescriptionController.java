package com.example.videoserver.controller;

import com.example.videoserver.ConfigParams;
import com.example.videoserver.entities.VideoDescriptionEntity;
import com.example.videoserver.utils.FileUtils;
import com.example.videoserver.utils.VideoImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VideoDescriptionController {
//    public ResponseEntity<Map<String, Map<String, List<VideoDescriptionEntity>>>> getVideoDescriptionMap(
//
//    ) {
//        if(!FileUtils.isDirectoryExist(ConfigParams.ROOT_DIR)) {
//            return null;
//        }
//        Map<String, Map<String, List<VideoDescriptionEntity>>> descriptionMap = new HashMap<>();
//        File rootFile = new File(ConfigParams.ROOT_DIR);
//        // 第一层的文件夹，是bar上的名字
//        File[] firstFileArr = rootFile.listFiles();
//        return null;
//    }

    /**
     * http://127.0.0.1:8080/videoDescription?select_name=Bilibili 访问
     * **/
    @GetMapping(value = "/videoDescription")
    public ResponseEntity<Map<String, VideoDescriptionEntity>> getVideoDescriptionMap(
            @RequestParam(value = "select_name", required=false) String select_name
    ) {
        if(select_name == null || select_name.isEmpty()){
            return null;
        }
        if(!FileUtils.isDirectoryExist(ConfigParams.ROOT_DIR)) {
            return null;
        }
        int id = 0;
        Map<String, VideoDescriptionEntity> descriptionMap = new HashMap<>();
        // 根据名字找到第二层文件夹或者直接是视频
        File secondFile = new File(ConfigParams.ROOT_DIR, select_name);
        if(!secondFile.exists() || !secondFile.isDirectory()) {
            return null;
        }
        File[] secondFileArr = secondFile.listFiles();
        if(secondFileArr == null) {
            return null;
        }
        for(File f : secondFileArr) {
            if (f.isDirectory()) {
                File[] thirdFileArr = f.listFiles();
                if (thirdFileArr == null) {
                    continue;
                }
                List<Map<String, String>> subImageList = new ArrayList<>();
                for (File thirdFile : thirdFileArr) {
                    if (thirdFile.isFile()) {
                        String videoFileName =  File.separator + select_name + File.separator + f.getName() + File.separator + thirdFile.getName();
                        String image = VideoImageUtils.getVideoImageToBase64(ConfigParams.ROOT_DIR + videoFileName, 0);
                        Map<String, String> subImage = new HashMap<>();
                        subImage.put(videoFileName, image);
                        subImageList.add(subImage);
                    }
                }
                descriptionMap.put(Integer.toString(id), new VideoDescriptionEntity(f.getName(), subImageList));
                id++;
            } else if (f.isFile()) {
                String videoFileName = File.separator + select_name + File.separator + f.getName();
                String image = VideoImageUtils.getVideoImageToBase64(ConfigParams.ROOT_DIR + videoFileName, 0);
                List<Map<String, String>> subImageList = new ArrayList<>();
                Map<String, String> subImage = new HashMap<>();
                subImage.put(videoFileName, image);
                subImageList.add(subImage);
                descriptionMap.put(Integer.toString(id), new VideoDescriptionEntity(f.getName(), subImageList));
                id++;
            }
        }

        return new ResponseEntity<>(descriptionMap, HttpStatus.OK);
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
