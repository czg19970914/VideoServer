package com.example.videoserver;

import com.example.videoserver.utils.FileUtils;
import com.example.videoserver.utils.VideoImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMethod {

    public static void main(String[] args) throws IOException {
//        String videoFilePath = ConfigParams.ROOT_DIR + "/Bilibili/洛丽塔大哥/1.mp4";
//        String targetFileDirectory = ConfigParams.ROOT_DIR + "/Bilibili/洛丽塔大哥";
//        BufferedImage bi = VideoImageUtils.getVideoImage(videoFilePath, 0);
//        if (bi != null && FileUtils.isDirectoryExist(targetFileDirectory)) {
//            ImageIO.write(bi, "jpg", new File(targetFileDirectory + "/1.jpg"));
//        }

        File rootFile = new File(ConfigParams.ROOT_DIR);
        File[] fileArr = rootFile.listFiles();
        assert fileArr != null;
        for(File f : fileArr) {
            System.out.println(f.getName());
        }
    }
}
