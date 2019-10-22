package com.lambdaschool.spotifysongsuggester.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "songs")
public class Song extends Auditable
{
    @ApiModelProperty(name = "trackid", value = "primary key for Song", required = true, example = "1")
    @Id
    private String trackid;

    @ApiModelProperty(name = "title", value = "Song Title", example = "Song Title One")
    private String title;

    @ApiModelProperty(name = "artist", value = "Song Artist", example = "Artist Name")
    private String Artist;


}
