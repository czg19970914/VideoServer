package com.example.videoserver.controller;

import com.example.videoserver.ConfigParams;
import com.example.videoserver.entities.SubVideoDescriptionEntity;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
public class VideoDescriptionController {

    /**
     * http://127.0.0.1:8080/videoDescription?select_name=Bilibili
     * http://127.0.0.1:8080/videoDescription?select_name=Bilibili&min_id=1&max_id=23
     * **/
    @GetMapping(value = "/videoDescription")
    public ResponseEntity<Map<String, VideoDescriptionEntity>> getVideoDescriptionMap(
            @RequestParam(value = "select_name", required=false) String selectName,
            @RequestParam(value = "min_id", required=false) int minId,
            @RequestParam(value = "max_id", required=false) int maxId
    ) {
        if(selectName == null || selectName.isEmpty()){
            return null;
        }
        if(!FileUtils.isDirectoryExist(ConfigParams.ROOT_DIR)) {
            return null;
        }
        Map<String, VideoDescriptionEntity> descriptionMap = new ConcurrentHashMap<>();
        // 根据名字找到第二层文件夹或者直接是视频
        File secondFile = new File(ConfigParams.ROOT_DIR, selectName);
        if(!secondFile.exists() || !secondFile.isDirectory()) {
            return null;
        }
        File[] secondFileArr = secondFile.listFiles();
        if(secondFileArr == null) {
            return null;
        }
        // 创建线程池来执行耗时的视频抽帧工作，里面可能会开子线程池
        ExecutorService videoDescriptionTaskExecutor = Executors.newFixedThreadPool(2);
        int id = 0;
        for(File f : secondFileArr) {
            if (id >= minId && id <= maxId) {
                if (f.isDirectory()) {
                    File[] thirdFileArr = f.listFiles();
                    if (thirdFileArr == null) {
                        continue;
                    }
                    int finalId1 = id;
                    videoDescriptionTaskExecutor.execute(() -> {
                        getVideoDescriptionItemByDirector(finalId1, f.getName(),
                                File.separator + selectName + File.separator + f.getName(),
                                thirdFileArr, descriptionMap);
                    });
                } else if (f.isFile()) {
                    int finalId = id;
                    String videoFileName = File.separator + selectName + File.separator + f.getName();
                    videoDescriptionTaskExecutor.execute(() -> {
                        getVideoDescriptionItemByFile(finalId, f.getName(), videoFileName, descriptionMap);
                    });
                }
            } else if(id > maxId) {
                break;
            }
            id++;
        }
        videoDescriptionTaskExecutor.shutdown();
        try {
            if(!videoDescriptionTaskExecutor.awaitTermination(3, TimeUnit.MINUTES)) {
                videoDescriptionTaskExecutor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            videoDescriptionTaskExecutor.shutdownNow();
        }
        return new ResponseEntity<>(descriptionMap, HttpStatus.OK);
    }

    private void getVideoDescriptionItemByFile(
            int id, String title, String videoFileName,
            Map<String, VideoDescriptionEntity> descriptionMap) {
        List<SubVideoDescriptionEntity> subImageList = new CopyOnWriteArrayList<>();

        getVideoDescriptionItem(videoFileName, subImageList);
        descriptionMap.put(Integer.toString(id), new VideoDescriptionEntity(title, subImageList));
    }

    private void getVideoDescriptionItemByDirector(
            int id, String title, String lastFilePath, File[] fileArr,
            Map<String, VideoDescriptionEntity> descriptionMap
    ) {
        // 子线程池处理视频抽帧任务
        ExecutorService subVideoDescriptionTaskExecutor = Executors.newFixedThreadPool(3);
        List<SubVideoDescriptionEntity> subImageList = new CopyOnWriteArrayList<>();
        for (File file: fileArr) {
            if(file.isFile()) {
                String videoFileName = lastFilePath + File.separator + file.getName();
                subVideoDescriptionTaskExecutor.execute(() -> {
                    getVideoDescriptionItem(videoFileName, subImageList);
                });
            }
        }
        subVideoDescriptionTaskExecutor.shutdown();
        try {
            if (!subVideoDescriptionTaskExecutor.awaitTermination(90, TimeUnit.SECONDS)) {
                subVideoDescriptionTaskExecutor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            subVideoDescriptionTaskExecutor.shutdownNow();
        }
        descriptionMap.put(Integer.toString(id), new VideoDescriptionEntity(title, subImageList));
    }

    private void getVideoDescriptionItem(String videoFileName, List<SubVideoDescriptionEntity> subImageList) {
        String image = VideoImageUtils.getVideoImageToBase64(ConfigParams.ROOT_DIR + videoFileName, 0);
        subImageList.add(new SubVideoDescriptionEntity(videoFileName, image));
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
