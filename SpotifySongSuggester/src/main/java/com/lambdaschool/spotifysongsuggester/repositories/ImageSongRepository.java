package com.lambdaschool.spotifysongsuggester.repositories;

import com.lambdaschool.spotifysongsuggester.models.ImageSong;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageSongRepository extends PagingAndSortingRepository<ImageSong, Long>
{
    ImageSong findImageSongByTrackid(String id);

    void deleteByTrackid(String id);

    void deleteByImagesongid(long id);

    ImageSong findImageSongByImagesongid(long id);
}
