package com.phimy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoDBContainer {
    @SerializedName("results")
    private List<VideoDB> videoDBS;

    public List<VideoDB> getVideoDBS() {
        return videoDBS;
    }
}
