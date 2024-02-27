package com.example.videoserver.controller;

import com.example.videoserver.ConfigParams;
import com.example.videoserver.entities.SubVideoDescriptionEntity;
import com.example.videoserver.entities.VideoDescriptionEntity;
import com.example.videoserver.entities.VideoDescriptionResponse;
import com.example.videoserver.utils.FileUtils;
import com.example.videoserver.utils.VideoImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VideoDescriptionController {
    private static final String IMAGE_ROOT_DIR = ConfigParams.ROOT_DIR + File.separator + "Pictures" + File.separator;
    @GetMapping(value = "/videoDescriptionData")
    public ResponseEntity<VideoDescriptionResponse> getVideoDescriptionData() {
        if(!FileUtils.isDirectoryExist(ConfigParams.ROOT_DIR)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        File rootFile = new File(ConfigParams.ROOT_DIR);
        // 第一层的文件夹，是bar上的名字
        File[] firstFileArr = rootFile.listFiles();
        if(firstFileArr == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<String> nameList = new ArrayList<>();
        Map<String, List<VideoDescriptionEntity>>VideoDescriptionContent = new HashMap<>();
        for(File firstFile : firstFileArr) {
            if(firstFile == null || !firstFile.isDirectory() || firstFile.getName().equals("Pictures")) {
                continue;
            }
            File[] secondFileArr = firstFile.listFiles();
            if(secondFileArr == null) {
                continue;
            }
            // 标识是否加入nameList
            boolean addNameFlag = false;
            List<VideoDescriptionEntity> videoDescriptionEntities = new ArrayList<>();
            // 第二层是视频文件或者文件夹
            for(File secondFile : secondFileArr) {
                List<SubVideoDescriptionEntity> subVideoDescriptionEntities = new ArrayList<>();

                if(secondFile.isDirectory()) {
                    // 第三层是视频文件
                    File[] thirdFileArr = secondFile.listFiles();
                    if(thirdFileArr == null) {
                        continue;
                    }
                    String title = secondFile.getName();
                    for(File thirdFile : thirdFileArr) {
                        if(thirdFile.isFile()) {
                            String subImageName =
                                    firstFile.getName() + "_" + secondFile.getName() + "_" + thirdFile.getName() + ".jpg";
                            subVideoDescriptionEntities.add(new SubVideoDescriptionEntity(subImageName));
                        }
                    }
                    if(!subVideoDescriptionEntities.isEmpty()) {
                        // 拿子视频的第一页作为总体的图片显示
                        String imageName = subVideoDescriptionEntities.get(0).getSubImageName();
                        videoDescriptionEntities.add(
                                new VideoDescriptionEntity(title, imageName, subVideoDescriptionEntities));
                        addNameFlag = true;
                    }
                } else if (secondFile.isFile()) {
                    String title = firstFile.getName();
                    String imageName = firstFile.getName() + "_" + secondFile.getName() + ".jpg";
                    subVideoDescriptionEntities.add(new SubVideoDescriptionEntity(imageName));
                    videoDescriptionEntities.add(
                            new VideoDescriptionEntity(title, imageName, subVideoDescriptionEntities));
                    addNameFlag = true;
                }
            }
            if(addNameFlag) {
                nameList.add(firstFile.getName());
                VideoDescriptionContent.put(firstFile.getName(), videoDescriptionEntities);
            }
        }

        return new ResponseEntity<>(new VideoDescriptionResponse(nameList, VideoDescriptionContent), HttpStatus.OK);
    }

    @GetMapping(value = "/videoImageBytes")
    public ResponseEntity<byte[]> getVideoImageBytes(
            @RequestParam(value = "image_file_path", required = false) String imageFilePath
    ) {
        String absoluteImageFilePath = IMAGE_ROOT_DIR + imageFilePath;
        if (FileUtils.isFileExist(absoluteImageFilePath)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        try {
            File imageFile = new File(absoluteImageFilePath);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, VideoImageUtils.DEFAULT_IMG_FORMAT, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return new ResponseEntity<>(imageBytes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("获取图片有误：" + imageFilePath);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
