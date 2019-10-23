package com.lambdaschool.spotifysongsuggester.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "songs")
public class Song extends Auditable
{
    @ApiModelProperty(name = "songid", value = "primary key for Song", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long songid;

    @ApiModelProperty(name = "trackid", value = "Spotify key for Song", required = true, example = "1")
    private String trackid;

    @ApiModelProperty(name = "song_name", value = "Song Title", example = "Song Title One")
    private String song_name;

    @ApiModelProperty(name = "artist", value = "Song Artist", example = "Artist Name")
    private String artist;

    @OneToMany(mappedBy = "song",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("song")
    private List<Favorites> favorites= new ArrayList<>();

    public Song()
    {
    }

    public Song(String trackid, String song_name, String artist)
    {
        this.trackid = trackid;
        this.song_name = song_name;
        this.artist = artist;
    }

    public long getSongid()
    {
        return songid;
    }

    public void setSongid(long songid)
    {
        this.songid = songid;
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

//    public List<Favorites> getFavorites()
//    {
//        return favorites;
//    }
//
//    public void setFavorites(List<Favorites> favorites)
//    {
//        this.favorites = favorites;
//    }

    @Override
    public String toString()
    {
        return "Song{" +
                "trackid='" + trackid + '\'' +
                ", songid='" + songid + '\'' +
                ", song_name='" + song_name + '\'' +
                ", artist='" + artist + '\'' +
//                ", favorites=" + favorites +
                '}';
    }
}
