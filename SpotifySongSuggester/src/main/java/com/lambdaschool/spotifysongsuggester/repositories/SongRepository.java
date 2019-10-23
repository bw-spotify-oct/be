package com.lambdaschool.spotifysongsuggester.repositories;

import com.lambdaschool.spotifysongsuggester.models.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SongRepository extends PagingAndSortingRepository<Song, Long>
{
//    Song findSongBySong_name(String name);

//    List<Song> findSongBySong_nameContainingIgnoreCase(String name,
//                                                  Pageable pageable);
    Song findSongByTrackid(String id);

    void deleteByTrackid(String id);

    void deleteBySongid(long id);

    Song findSongBySongid(long id);
}
