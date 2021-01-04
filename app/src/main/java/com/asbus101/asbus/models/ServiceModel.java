package com.asbus101.asbus.models;

public class ServiceModel {
    String title;
    int image;

    public ServiceModel(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public ServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
