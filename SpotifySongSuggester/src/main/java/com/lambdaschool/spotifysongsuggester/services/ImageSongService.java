package com.lambdaschool.spotifysongsuggester.services;

import com.lambdaschool.spotifysongsuggester.models.ImageSong;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageSongService
{

    List<ImageSong> findAll(Pageable pageable);

    ImageSong findImageSongByTrackId(String trackid);

    ImageSong findImageSongById(long id);

    void delete(long id);

    ImageSong save(ImageSong imageSong);

    ImageSong update(ImageSong imageSong,
                long id);
}