package com.open_source.worldwide.baking.models;


public final class Step {

    private Integer stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Step(Integer id, String shortDescription,
                String description, String videoURL, String thumbnailURL) {

        this.stepId = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;

    }

    public Integer getStepId() {
        return stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }


    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }


}