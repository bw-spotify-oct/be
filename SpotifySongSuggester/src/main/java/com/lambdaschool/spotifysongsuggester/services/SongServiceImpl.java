package com.lambdaschool.spotifysongsuggester.services;

import com.lambdaschool.spotifysongsuggester.exceptions.ResourceFoundException;
import com.lambdaschool.spotifysongsuggester.exceptions.ResourceNotFoundException;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.Song;
import com.lambdaschool.spotifysongsuggester.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "songService")
public class SongServiceImpl implements SongService
{

    @Autowired
    private SongRepository songrepos;

    @Override
    public Song findSongByTrackId(String id) throws ResourceNotFoundException
    {
        if( songrepos.findSongByTrackid(id) !=null)
        {
            return songrepos.findSongByTrackid(id);
        }
        else
        {
            throw new ResourceNotFoundException("Track id " + id + " not found!");
        }
    }

    @Override
    public Song findSongById(long id) throws ResourceNotFoundException
    {
        if( songrepos.findSongBySongid(id) !=null)
        {
            return songrepos.findSongBySongid(id);
        }
        else
        {
            throw new ResourceNotFoundException("Song id " + id + " not found!");
        }
    }

    @Transactional
    @Override
    public Song save(Song song)
    {
        if(songrepos.findSongByTrackid(song.getTrackid()) !=null)
        {
            throw new ResourceFoundException(song.getSong_name() + " is already saved!");
        }

        Song song1 = new Song();
        song1.setTrackid(song.getTrackid());
        song1.setSongid(song.getSongid());
        song1.setArtist(song.getArtist());
        song1.setSong_name(song.getSong_name());
        song1.setUri(song.getUri());

        return songrepos.save(song1);
    }

    @Override
    public List<Song> findAll(Pageable pageable)
    {
        List<Song> list = new ArrayList<>();
        songrepos.findAll(pageable)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Song update(Song song, long id)
    {
        if(songrepos.findSongBySongid(id) != null)
        {
            Song currentSong = songrepos.findSongBySongid(id);

            if (song.getArtist() != null)
            {
                currentSong.setArtist(song.getArtist().toLowerCase());
            }

            if(song.getSong_name() != null)
            {
                currentSong.setSong_name(song.getSong_name().toLowerCase());
            }

            if(song.getUri() != null)
            {
                currentSong.setUri(song.getUri().toLowerCase());
            }

            return songrepos.save(currentSong);
        }
        else
        {
            throw new ResourceNotFoundException(id + " Not Found");
        }
    }

    @Transactional
    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        if( songrepos.findSongBySongid(id) !=null)
        {
            songrepos.deleteBySongid(id);
        }
        else
        {
            throw new ResourceNotFoundException("Track id " + id + " not found!");
        }
    }

}
