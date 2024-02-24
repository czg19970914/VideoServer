package com.example.videoserver.entities;

import java.awt.image.BufferedImage;

public class VideoDescriptionEntity {
    // 视频文件的相对地址和名字
    private String videoFileName;

    // 视频文件的缩略图
    private BufferedImage image;

    // 该视频的标题
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }
}
