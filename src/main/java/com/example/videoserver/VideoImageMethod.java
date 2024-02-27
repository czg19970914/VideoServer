package com.example.videoserver;

import com.example.videoserver.utils.FileUtils;

import java.io.File;

public class VideoImageMethod {
    private static final String imageRootPath = ConfigParams.ROOT_DIR + File.separator + "Pictures" + File.separator;
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
        String imageFilePath = "";
        for(File firstFile : firstFileArr) {
            if(!firstFile.isDirectory()) {
                continue;
            }
            imageFilePath = firstFile.getName();
        }
    }
}
