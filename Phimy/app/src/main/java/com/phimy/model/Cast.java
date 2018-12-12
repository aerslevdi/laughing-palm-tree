package com.phimy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable{

    private String name;
    private Integer id;
    private Integer profile_path;

    public Cast(String name, Integer id, Integer profile_path) {
        this.name = name;
        this.id = id;
        this.profile_path = profile_path;
    }

    private Cast(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.profile_path = in.readInt();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getProfile_path() {
        return profile_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(profile_path);
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };
}
