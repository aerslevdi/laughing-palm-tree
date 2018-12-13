package com.phimy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast {

    private String name;
    private Integer id;
    private String profile_path;

    public Cast(String name, Integer id, String profile_path) {
        this.name = name;
        this.id = id;
        this.profile_path = profile_path;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }


}
