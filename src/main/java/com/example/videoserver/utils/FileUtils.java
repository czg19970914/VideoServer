package com.example.videoserver.utils;

import java.io.File;

public class FileUtils {
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean isDirectoryExist(String directoryPath) {
        File file = new File(directoryPath);
        if(!file.isDirectory())
            return false;
        return file.exists();
    }
}
