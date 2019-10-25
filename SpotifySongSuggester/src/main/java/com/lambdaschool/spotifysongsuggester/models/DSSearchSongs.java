package com.lambdaschool.spotifysongsuggester.models;

import com.lambdaschool.spotifysongsuggester.logging.Loggable;

import java.util.List;

@Loggable
public class DSSearchSongs
{
    private List <DSSearchSong> songResults;

    public DSSearchSongs()
    {
    }

    @Override
    public String toString()
    {
        return "DSSearchSongs{" +
                "songResults=" + songResults +
                '}';
    }
}
