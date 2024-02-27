package com.example.videoserver.entities;

import java.util.List;
import java.util.Map;

public class VideoDescriptionResponse {
    private List<String> nameList;

    private Map<String, VideoDescriptionEntity> videoDescriptionContent;

    public VideoDescriptionResponse(List<String> nameList,
                                    Map<String, VideoDescriptionEntity> videoDescriptionContent) {
        this.nameList = nameList;
        this.videoDescriptionContent = videoDescriptionContent;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public Map<String, VideoDescriptionEntity> getVideoDescriptionContent() {
        return videoDescriptionContent;
    }

    public void setVideoDescriptionContent(Map<String, VideoDescriptionEntity> videoDescriptionContent) {
        this.videoDescriptionContent = videoDescriptionContent;
    }
}