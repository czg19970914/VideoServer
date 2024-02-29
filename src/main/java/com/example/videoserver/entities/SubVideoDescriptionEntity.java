package com.example.videoserver.entities;

public class SubVideoDescriptionEntity {
    private String subImageName;

    private String subVideoPath;

    public SubVideoDescriptionEntity(String subImageName, String subVideoPath) {
        this.subImageName = subImageName;
        this.subVideoPath = subVideoPath;
    }

    public String getSubImageName() {
        return subImageName;
    }

    public void setSubImageName(String subImageName) {
        this.subImageName = subImageName;
    }

    public String getSubVideoPath() {
        return subVideoPath;
    }

    public void setSubVideoPath(String subVideoPath) {
        this.subVideoPath = subVideoPath;
    }
}
