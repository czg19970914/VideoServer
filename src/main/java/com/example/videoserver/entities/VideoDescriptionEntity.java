package com.example.videoserver.entities;

import java.util.List;
import java.util.Map;

public class VideoDescriptionEntity {
    // 该视频的标题
    private String title;

    // 展示图片
    private String imageName;

    // 子视频列表
    private List<SubVideoDescriptionEntity> subVideoDescriptionEntities;

    public VideoDescriptionEntity(String title, String imageName,
                                  List<SubVideoDescriptionEntity> subVideoDescriptionEntities) {
        this.title = title;
        this.imageName = imageName;
        this.subVideoDescriptionEntities = subVideoDescriptionEntities;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<SubVideoDescriptionEntity> getSubVideoDescriptionEntities() {
        return subVideoDescriptionEntities;
    }

    public void setSubVideoDescriptionEntities(List<SubVideoDescriptionEntity> subVideoDescriptionEntities) {
        this.subVideoDescriptionEntities = subVideoDescriptionEntities;
    }
}
