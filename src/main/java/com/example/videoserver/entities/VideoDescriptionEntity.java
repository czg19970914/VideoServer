package com.example.videoserver.entities;

import java.util.List;
import java.util.Map;

public class VideoDescriptionEntity {
    // 该视频的标题
    private String title;

    // 子视频的文件路径和缩略图的base64字符串
    private List<SubVideoDescriptionEntity> subImages;

    public VideoDescriptionEntity(String title, List<SubVideoDescriptionEntity> subImages) {
        this.title = title;
        this.subImages = subImages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubVideoDescriptionEntity> getSubImages() {
        return subImages;
    }

    public void setSubImages(List<SubVideoDescriptionEntity> subImages) {
        this.subImages = subImages;
    }
}
