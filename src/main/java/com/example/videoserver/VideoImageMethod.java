package com.example.videoserver;

import com.example.videoserver.utils.FileUtils;
import com.example.videoserver.utils.VideoImageUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VideoImageMethod {
    private static final String IMAGE_ROOT_DIR = ConfigParams.ROOT_DIR + File.separator + "Pictures" + File.separator;
    public void getAllVideoImages() {
        if(!FileUtils.isDirectoryExist(ConfigParams.ROOT_DIR)) {
            return;
        }
        File rootFile = new File(ConfigParams.ROOT_DIR);
        // 第一层的文件夹，是bar上的名字
        File[] firstFileArr = rootFile.listFiles();
        if(firstFileArr == null) {
            return;
        }
        // 创建线程池来执行耗时的视频抽帧工作
        ExecutorService videoImageTaskExecutor = Executors.newFixedThreadPool(4);
        for(File firstFile : firstFileArr) {
            if(firstFile == null || !firstFile.isDirectory() || firstFile.getName().equals("Pictures")) {
                continue;
            }
            File[] secondFileArr = firstFile.listFiles();
            if(secondFileArr == null) {
                continue;
            }
            // 第二层是视频文件或者文件夹
            for(File secondFile : secondFileArr) {
                if(secondFile.isDirectory()) {
                    // 第三层是视频文件
                    File[] thirdFileArr = secondFile.listFiles();
                    if(thirdFileArr == null) {
                        continue;
                    }
                    for(File thirdFile : thirdFileArr) {
                        if(thirdFile.isFile()){
                            String videoFilePath = thirdFile.getAbsolutePath();
                            String targetFilePath = IMAGE_ROOT_DIR +
                                    firstFile.getName() + "_" + secondFile.getName() + "_" + thirdFile.getName() + ".jpg";
                            videoImageTaskExecutor.execute(() -> {
                                VideoImageUtils.fetchFrame(videoFilePath, targetFilePath);
                            });
//                            VideoImageUtils.fetchFrame(videoFilePath, targetFilePath);
                        }
                    }
                } else if (secondFile.isFile()) {
                    // 视频文件直接保存截图
                    String videoFilePath = secondFile.getAbsolutePath();
                    String targetFilePath = IMAGE_ROOT_DIR + firstFile.getName() + "_" + secondFile.getName() + ".jpg";
//                    if(!FileUtils.isFileExist(targetFilePath))
//                        VideoImageUtils.getVideoImageToJpg(videoFilePath, targetFilePath, 0);
                    videoImageTaskExecutor.execute(() -> {
                        VideoImageUtils.fetchFrame(videoFilePath, targetFilePath);
                    });
//                    VideoImageUtils.fetchFrame(videoFilePath, targetFilePath);
                }
            }
        }
        videoImageTaskExecutor.shutdown();
        try {
            if(!videoImageTaskExecutor.awaitTermination(10, TimeUnit.MINUTES)) {
                videoImageTaskExecutor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            videoImageTaskExecutor.shutdownNow();
        }
    }

    public static void main(String[] args) {
        VideoImageMethod videoImageMethod = new VideoImageMethod();
        videoImageMethod.getAllVideoImages();
    }
}
