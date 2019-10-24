package com.lambdaschool.spotifysongsuggester.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "imagesongs")
public class ImageSong extends Auditable
{
    @ApiModelProperty(name = "imagesongid", value = "primary key for ImageSong", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imagesongid;

    @ApiModelProperty(name = "trackid", value = "Spotify key for Song", required = true, example = "1")
    private String trackid;

    @ApiModelProperty(name = "song_name", value = "Song Title", example = "Song Title One")
    private String song_name;

    @ApiModelProperty(name = "artist", value = "Song Artist", example = "Artist Name")
    private String artist;

    @ApiModelProperty(name = "uri", value = "Song URI", example = "spotify:track:trackid")
    private String uri;

    @ApiModelProperty(name = "large_image", value = "Large Image URL", example = "https://")
    private String large_image;

    @ApiModelProperty(name = "med_image", value = "Medium Image URL", example = "https://")
    private String med_image;

    @ApiModelProperty(name = "small_image", value = "Large Image URL", example = "https://")
    private String small_image;

    @OneToMany(mappedBy = "imagesong",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("imagesong")
    private List<FavoriteImageSong> favorites= new ArrayList<>();

    public ImageSong()
    {
    }

    public ImageSong(String trackid,
                     String song_name,
                     String artist,
                     String uri,
                     String large_image,
                     String med_image, String small_image)
    {
        this.trackid = trackid;
        this.song_name = song_name;
        this.artist = artist;
        this.uri = uri;
        this.large_image = large_image;
        this.med_image = med_image;
        this.small_image = small_image;
    }

    public long getImagesongid()
    {
        return imagesongid;
    }

    public void setImagesongid(long songid)
    {
        this.imagesongid = songid;
    }

    public String getTrackid()
    {
        return trackid;
    }

    public void setTrackid(String trackid)
    {
        this.trackid = trackid;
    }

    public String getSong_name()
    {
        return song_name;
    }

    public void setSong_name(String song_name)
    {
        this.song_name = song_name;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
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

    @Override
    public String toString()
    {
        return "Song{" +
                "imagesongid=" + imagesongid +
                ", trackid='" + trackid + '\'' +
                ", song_name='" + song_name + '\'' +
                ", artist='" + artist + '\'' +
                ", uri='" + uri + '\'' +
//                ", favorites=" + favorites +
                '}';
    }
}
