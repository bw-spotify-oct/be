package com.lambdaschool.spotifysongsuggester.models;

import com.lambdaschool.spotifysongsuggester.logging.Loggable;

@Loggable
// From: https://spotify-api-helper.herokuapp.com/songs/DReaI4d55IIaiD6P9/may%20we%20all
// This class must match the JSON object
public class DSSongWithImg
{
    private String artist;
    private String id;
    private String large_image;
    private String med_image;
    private String small_image;
    private String song_name;
    private String uri;

    public DSSongWithImg()
    {
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLarge_image()
    {
        return large_image;
    }

    public void setLarge_image(String large_image)
    {
        this.large_image = large_image;
    }

    public String getMed_image()
    {
        return med_image;
    }

    public void setMed_image(String med_image)
    {
        this.med_image = med_image;
    }

    public String getSmall_image()
    {
        return small_image;
    }

    public void setSmall_image(String small_image)
    {
        this.small_image = small_image;
    }

    public String getSong_name()
    {
        return song_name;
    }

    public void setSong_name(String song_name)
    {
        this.song_name = song_name;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    @Override
    public String toString()
    {
        return "DSSongWithImg{" +
                "artist='" + artist + '\'' +
                ", id='" + id + '\'' +
                ", large_image='" + large_image + '\'' +
                ", med_image='" + med_image + '\'' +
                ", small_image='" + small_image + '\'' +
                ", song_name='" + song_name + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
