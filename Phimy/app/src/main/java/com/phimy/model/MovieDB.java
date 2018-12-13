package com.phimy.model;


import java.io.Serializable;

public class MovieDB implements Serializable{
    private String title;
    private Integer vote_count;
    private Integer id;
    private String poster_path;
    private String overview;
    private String release_date;
    private Double popularity;
    private Boolean video;


    public String getTitle() {
        return title;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public Integer getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Boolean getVideo(){ return video;}

    @Override
    public String toString() {
        return "MovieDB{" +
                "title='" + title + '\'' +
                ", vote_count=" + vote_count +
                ", id=" + id +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", popularity=" + popularity +
                ", video=" + video +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return (this.id.equals(((MovieDB) obj).id));
    }
}
