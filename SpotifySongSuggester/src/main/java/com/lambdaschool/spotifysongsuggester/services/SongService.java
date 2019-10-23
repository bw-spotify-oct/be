package com.lambdaschool.spotifysongsuggester.services;

import com.lambdaschool.spotifysongsuggester.models.Song;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongService
{

    List<Song> findAll(Pageable pageable);

//    List<Song> findByNameContaining(String name,
//                                    Pageable pageable);

    Song findSongByTrackId(String trackid);

    Song findSongById(long id);

//    Song findByName(String name);

    void delete(long id);

    Song save(Song song);

    Song update(Song song,
                long id);
}