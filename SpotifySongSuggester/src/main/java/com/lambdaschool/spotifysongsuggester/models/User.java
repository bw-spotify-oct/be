package com.lambdaschool.spotifysongsuggester.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "users")
public class User extends Auditable
{
    @ApiModelProperty(name = "userid", value = "primary key for User", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @ApiModelProperty(name = "username", value = "Username", required = true, example = "aname")
    @Column(nullable = false,
            unique = true)
    private String username;

    @ApiModelProperty(name = "password", value = "User password", required = true, example = "password")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<FavoriteImageSong> favoriteImageSongs = new ArrayList<>();

    public User()
    {
    }

    public User(String username,
                String password)
    {
        setUsername(username);
        setPassword(password);
    }

    public User(String username,
                String password,
                List<Favorite> favorites, List<FavoriteImageSong> favoriteImageSongs)
    {
        this.username = username;
        this.password = password;
        this.favorites = favorites;
        this.favoriteImageSongs = favoriteImageSongs;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        if (username == null) // this is possible when updating a user
        {
            return null;
        } else
        {
            return username.toLowerCase();
        }
    }

    public void setUsername(String username)
    {
        this.username = username.toLowerCase();
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        String myRole = "ROLE_" + "USER";

        rtnList.add(new SimpleGrantedAuthority(myRole));

        return rtnList;
    }

    public List<Favorite> getFavorites()
    {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites)
    {
        this.favorites = favorites;
    }

    public List<FavoriteImageSong> getFavoriteImageSongs()
    {
        return favoriteImageSongs;
    }

    public void setFavoriteImageSongs(List<FavoriteImageSong> favoriteImageSongs)
    {
        this.favoriteImageSongs = favoriteImageSongs;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
//                ", favorites=" + favorites +
                '}';
    }
}
