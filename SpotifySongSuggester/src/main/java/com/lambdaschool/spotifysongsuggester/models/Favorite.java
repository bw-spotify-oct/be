package com.lambdaschool.spotifysongsuggester.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Loggable
@Entity (name = "favorites")
@Table(name = "favorites",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "songid"})})

public class Favorite extends Auditable implements Serializable
{
    @ApiModelProperty(name = "userid", value = "primary key for User", required = true, example = "1")
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("favorites")
    private User user;

    @ApiModelProperty(name = "songid", value = "primary key for Song", required = true, example = "1")
    @Id
    @ManyToOne
    @JoinColumn(name = "songid")
    @JsonIgnoreProperties("favorites")
    private Song song;

//    @ApiModelProperty(name = "trackid", value = "Spotify key for Song", required = true, example = "1")
//    @ManyToOne
//    @JoinColumn(name = "trackid")
//    @JsonIgnoreProperties("favorites")
//    private Song song;

    public Favorite()
    {
    }

    public Favorite(User user, Song song)
    {
        this.user = user;
        this.song = song;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Song getSong()
    {
        return song;
    }

    public void setSong(Song song)
    {
        this.song = song;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Favorite))
        {
            return false;
        }
        Favorite favorites = (Favorite) o;
        return getUser().equals(favorites.getUser()) &&
                getSong().equals(favorites.getSong());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUser(), getSong());
    }

    @Override
    public String toString()
    {
        return "Favorite{" +
                "user=" + user +
                ", song=" + song +
                '}';
    }
}
