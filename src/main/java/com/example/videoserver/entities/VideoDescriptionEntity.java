package com.example.videoserver.entities;

import java.util.List;
import java.util.Map;

public class VideoDescriptionEntity {
    // 该视频的标题
    private String title;

    // 视频文件的相对地址和名字
//    private String videoFileName;

    // 视频文件的缩略图，最外层的缩略图的base64字符串
//    private String image;

    // 子视频的文件路径和缩略图的base64字符串
    private List<Map<String, String>> subImages;

    public VideoDescriptionEntity(String title, List<Map<String, String>> subImages) {
        this.title = title;
        this.subImages = subImages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String, String>> getSubImages() {
        return subImages;
    }

    public void setSubImages(List<Map<String, String>> subImages) {
        this.subImages = subImages;
    }
}
