package com.lambdaschool.spotifysongsuggester.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Loggable
@Entity (name = "favoriteimagesongs")
@Table(name = "favoriteimagesongs",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "imagesongid"})})
public class FavoriteImageSong extends Auditable implements Serializable
{
    @ApiModelProperty(name = "userid", value = "primary key for User", required = true, example = "1")
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("favoriteimagesongs")
    private User user;

    @ApiModelProperty(name = "imagesongid", value = "primary key for ImageSong", required = true, example = "1")
    @Id
    @ManyToOne
    @JoinColumn(name = "imagesongid")
    @JsonIgnoreProperties("favoriteimages")
    private ImageSong imagesong;

    public FavoriteImageSong()
    {
    }

    public FavoriteImageSong(User user, ImageSong imagesong)
    {
        this.user = user;
        this.imagesong = imagesong;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public ImageSong getImagesong()
    {
        return imagesong;
    }

    public void setImagesong(ImageSong imagesong)
    {
        this.imagesong = imagesong;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof FavoriteImageSong))
        {
            return false;
        }
        FavoriteImageSong favorites = (FavoriteImageSong) o;
        return getUser().equals(favorites.getUser()) &&
                getImagesong().equals(favorites.getImagesong());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUser(), getImagesong());
    }

    @Override
    public String toString()
    {
        return "Favorite{" +
                "user=" + user +
                ", song=" + imagesong +
                '}';
    }
}
