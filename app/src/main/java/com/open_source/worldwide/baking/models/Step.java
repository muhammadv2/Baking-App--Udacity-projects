package com.open_source.worldwide.baking.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public final class Step implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @NonNull
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    private final Integer stepId;
    private final String shortDescription;
    private final String description;
    private final String videoURL;
    private final String thumbnailURL;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(stepId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    private Step(Parcel in) {

        stepId = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }


}