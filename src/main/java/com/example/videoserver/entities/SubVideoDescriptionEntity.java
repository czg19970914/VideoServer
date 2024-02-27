package com.example.videoserver.entities;

public class SubVideoDescriptionEntity {
    private String subImageName;

    public SubVideoDescriptionEntity(String subImageName) {
        this.subImageName = subImageName;
    }

    public String getSubImageName() {
        return subImageName;
    }

    public void setSubImageName(String subImageName) {
        this.subImageName = subImageName;
    }
}
