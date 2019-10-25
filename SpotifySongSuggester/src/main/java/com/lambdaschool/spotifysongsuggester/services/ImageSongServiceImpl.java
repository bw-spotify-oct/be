package com.lambdaschool.spotifysongsuggester.services;

import com.lambdaschool.spotifysongsuggester.exceptions.ResourceFoundException;
import com.lambdaschool.spotifysongsuggester.exceptions.ResourceNotFoundException;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.ImageSong;
import com.lambdaschool.spotifysongsuggester.repositories.ImageSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Transactional
@Service(value = "imageSongService")
public class ImageSongServiceImpl implements ImageSongService
{

    @Autowired
    private ImageSongRepository imagesongrepos;

    @Override
    public ImageSong findImageSongByTrackId(String id) throws ResourceNotFoundException
    {
        if( imagesongrepos.findImageSongByTrackid(id) !=null)
        {
            return imagesongrepos.findImageSongByTrackid(id);
        }
        else
        {
            throw new ResourceNotFoundException("Track id " + id + " not found!");
        }
    }

    @Override
    public ImageSong findImageSongById(long id) throws ResourceNotFoundException
    {
        if( imagesongrepos.findImageSongByImagesongid(id) !=null)
        {
            return imagesongrepos.findImageSongByImagesongid(id);
        }
        else
        {
            throw new ResourceNotFoundException("ImageSong id " + id + " not found!");
        }
    }

    @Transactional
    @Override
    public ImageSong save(ImageSong imagesong)
    {
        if(imagesongrepos.findImageSongByTrackid(imagesong.getTrackid()) !=null)
        {
            throw new ResourceFoundException(imagesong.getSong_name() + " is already saved!");
        }

        ImageSong imagesong1 = new ImageSong();
        imagesong1.setTrackid(imagesong.getTrackid());
        imagesong1.setImagesongid(imagesong.getImagesongid());
        imagesong1.setArtist(imagesong.getArtist());
        imagesong1.setSong_name(imagesong.getSong_name());
        imagesong1.setUri(imagesong.getUri());
        imagesong1.setLarge_image(imagesong.getLarge_image());
        imagesong1.setMed_image(imagesong.getMed_image());
        imagesong1.setSmall_image(imagesong.getSmall_image());

        return imagesongrepos.save(imagesong1);
    }

    @Override
    public List<ImageSong> findAll(Pageable pageable)
    {
        List<ImageSong> list = new ArrayList<>();
        imagesongrepos.findAll(pageable)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

//    @Override
//    public List<Song> findByNameContaining(String name, Pageable pageable)
//    {
//        return songrepos.findSongBySong_nameContainingIgnoreCase(name.toLowerCase(), pageable);
//    }

    @Transactional
    @Override
    public ImageSong update(ImageSong imagesong, long id)
    {
        if(imagesongrepos.findImageSongByImagesongid(id) != null)
        {
            ImageSong currentSong = imagesongrepos.findImageSongByImagesongid(id);

            if (imagesong.getArtist() != null)
            {
                currentSong.setArtist(imagesong.getArtist().toLowerCase());
            }

            if(imagesong.getSong_name() != null)
            {
                currentSong.setSong_name(imagesong.getSong_name().toLowerCase());
            }

            if(imagesong.getUri() != null)
            {
                currentSong.setUri(imagesong.getUri().toLowerCase());
            }

            if(imagesong.getLarge_image() != null)
            {
                currentSong.setLarge_image(imagesong.getLarge_image());
            }

            if(imagesong.getMed_image() != null)
            {
                currentSong.setMed_image(imagesong.getMed_image());
            }

            if(imagesong.getSmall_image() != null)
            {
                currentSong.setSmall_image(imagesong.getSmall_image());
            }

            return imagesongrepos.save(currentSong);
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
        if( imagesongrepos.findImageSongByImagesongid(id) !=null)
        {
            imagesongrepos.deleteByImagesongid(id);
        }
        else
        {
            throw new ResourceNotFoundException("Track id " + id + " not found!");
        }
    }

}
