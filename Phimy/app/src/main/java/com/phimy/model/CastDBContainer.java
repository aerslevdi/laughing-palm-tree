package com.phimy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastDBContainer {
    @SerializedName("cast")
    private List<Cast> fullCast;
    public List<Cast> getFullCast() {
        return fullCast;
    }
}
