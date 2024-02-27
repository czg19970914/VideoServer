package com.example.videoserver.entities;

import java.util.List;
import java.util.Map;

public class VideoDescriptionResponse {
    private List<String> nameList;

    private Map<String, VideoDescriptionEntity> VideoDescriptionContent;

    public VideoDescriptionResponse(List<String> nameList,
                                    Map<String, VideoDescriptionEntity> VideoDescriptionContent) {
        this.nameList = nameList;
        this.VideoDescriptionContent = VideoDescriptionContent;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public Map<String, VideoDescriptionEntity> getVideoDescriptionContent() {
        return VideoDescriptionContent;
    }

    public void setVideoDescriptionContent(Map<String, VideoDescriptionEntity> videoDescriptionContent) {
        VideoDescriptionContent = videoDescriptionContent;
    }
}
