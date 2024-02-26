package com.example.videoserver.entities;

public class SubVideoDescriptionEntity {
    private String subVideoPath;

    private String subVideoImage;

    public SubVideoDescriptionEntity(String subVideoPath, String subVideoImage) {
        this.subVideoPath = subVideoPath;
        this.subVideoImage = subVideoImage;
    }
    public String getSubVideoPath() {
        return subVideoPath;
    }

    public void setSubVideoPath(String subVideoPath) {
        this.subVideoPath = subVideoPath;
    }

    public String getSubVideoImage() {
        return subVideoImage;
    }

    public void setSubVideoImage(String subVideoImage) {
        this.subVideoImage = subVideoImage;
    }
}
