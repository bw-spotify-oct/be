package com.lambdaschool.spotifysongsuggester.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.*;
import com.lambdaschool.spotifysongsuggester.services.ImageSongService;
import com.lambdaschool.spotifysongsuggester.services.SongService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/data")
public class DataScienceAPIsController
{
    private static final Logger logger = LoggerFactory.getLogger(DataScienceAPIsController.class);
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private SongService songService;

    @Autowired
    private ImageSongService imageSongService;

    @ApiOperation(value = "Retrieves Songs by Search Query.", response = Song.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Songs Found", response = Song.class),
            @ApiResponse(code = 404, message = "Songs Not Found, Internal Data Server Error", response = ErrorDetail.class
            )})
    @GetMapping(value = "/search/{search}",
            produces = {"application/json"})
    public ResponseEntity<?> ListSongsGivenSearch(HttpServletRequest request,
                                                  @PathVariable
                                                          String search)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        String requestURL = "https://spotify-api-helper.herokuapp.com/songs/DReaI4d55IIaiD6P9/" + search;

        ParameterizedTypeReference<List<DSSearchSong>> responseType = new ParameterizedTypeReference<List<DSSearchSong>>()
        {
        };
        ResponseEntity<List<DSSearchSong>> responseEntity = restTemplate.exchange(requestURL,
                HttpMethod.GET,
                null,
                responseType);

        List<DSSearchSong> ourSongs = responseEntity.getBody();
      
        List <Song> newSongs = new ArrayList<>();

        for( DSSearchSong s : ourSongs)
        {
//            if(songService.findSongByTrackId(s.getId()).getUri().equalsIgnoreCase(s.getUri()))
//            {
//                newSongs.add(songService.findSongByTrackId(s.getId()));
//            }
//            else
//            {
                Song s1 = new Song(s.getId(), s.getSong_name(), s.getArtist(), s.getUri());
                newSongs.add(s1);
//                songService.save(s1);
//            }
        }

        System.out.println(ourSongs);
        return new ResponseEntity<>(newSongs,
                HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Similar Songs by Track Id.", response = DSSongWithImg.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Songs Found", response = DSSongWithImg.class),
            @ApiResponse(code = 404, message = "Songs Not Found, Internal Data Server Error", response = ErrorDetail.class
            )})
    @GetMapping(value = "/recs/trackid/{trackid}",
            produces = {"application/json"})
    public ResponseEntity<?> ListRecsGivenTrackId(HttpServletRequest request,
                                                  @PathVariable
                                                          String trackid)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        String requestURL = "https://spotify-api-helper.herokuapp.com/recs/DReaI4d55IIaiD6P9/" + trackid;

        ParameterizedTypeReference<List<DSSongWithImg>> responseType = new ParameterizedTypeReference<List<DSSongWithImg>>()
        {
        };
        ResponseEntity<List<DSSongWithImg>> responseEntity = restTemplate.exchange(requestURL,
                HttpMethod.GET,
                null,
                responseType);

        List<DSSongWithImg> ourSongs = responseEntity.getBody();

        List <ImageSong> newSongs = new ArrayList<>();

        for( DSSongWithImg s : ourSongs)
        {
//            if(imageSongService.findImageSongByTrackId(s.getId()).getUri().equalsIgnoreCase(s.getUri()))
//            {
//                newSongs.add(imageSongService.findImageSongByTrackId(s.getId()));
//            }
//            else
//            {
                ImageSong s1 = new ImageSong(s.getId(), s.getSong_name(), s.getArtist(), s.getUri(), s.getLarge_image(), s.getMed_image(), s.getSmall_image());
                newSongs.add(s1);
//                imageSongService.save(s1);
//            }
        }

        return new ResponseEntity<>(newSongs, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Similar Songs by Search.", response = ImageSong.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Songs Found", response = ImageSong.class),
            @ApiResponse(code = 404, message = "Songs Not Found, Internal Data Server Error", response = ErrorDetail.class
            )})
    @GetMapping(value = "/recs/search/{search}",
            produces = {"application/json"})

    public ResponseEntity<?> searchViaSearch(HttpServletRequest request, @PathVariable String search) throws IOException
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        String requestURL = "https://spotify-api-helper.herokuapp.com/auto_search/DReaI4d55IIaiD6P9/" + search;

        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<>()
        {
        };
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestURL, HttpMethod.GET, null, responseType);
        String jsonString = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        List<DSSongWithImg> songWithImgList = mapper.readValue(jsonString, new TypeReference<List<DSSongWithImg>>() {});

        List <ImageSong> newSongs = new ArrayList<>();

        for( DSSongWithImg s : songWithImgList)
        {
//            if(imageSongService.findImageSongByTrackId(s.getId()).getUri().equalsIgnoreCase(s.getUri()))
//            {
//                newSongs.add(imageSongService.findImageSongByTrackId(s.getId()));
//            }
//            else
//            {
                ImageSong s1 = new ImageSong(s.getId(), s.getSong_name(), s.getArtist(), s.getUri(), s.getLarge_image(), s.getMed_image(), s.getSmall_image());
                newSongs.add(s1);
//                imageSongService.save(s1);
//            }
        }

        System.out.println(songWithImgList);
        System.out.println(newSongs);
        return new ResponseEntity<>(newSongs,
                HttpStatus.OK);
    }


    @ApiOperation(value = "Retrieves Songs with Album Art Given Search.", response = Song.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Songs Found", response = Song.class),
            @ApiResponse(code = 404, message = "Songs Not Found, Internal Data Server Error", response = ErrorDetail.class
            )})
    @GetMapping(value = "/search/images/{search}",
            produces = {"application/json"})
    public ResponseEntity<?> SongsWithImagesGivenSearch(HttpServletRequest request, @PathVariable String search) throws IOException
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        String requestURL = "https://spotify-api-helper.herokuapp.com/songs_with_pic/DReaI4d55IIaiD6P9/" + search;

        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<>()
        {
        };
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestURL, HttpMethod.GET, null, responseType);
        String jsonString = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        List<DSSongWithImg> songWithImgList = mapper.readValue(jsonString, new TypeReference<List<DSSongWithImg>>() {});

        List <ImageSong> newSongs = new ArrayList<>();

        for( DSSongWithImg s : songWithImgList)
        {
//            if(imageSongService.findImageSongByTrackId(s.getId()).getUri().equalsIgnoreCase(s.getUri()))
//            {
//                newSongs.add(imageSongService.findImageSongByTrackId(s.getId()));
//            }
//            else
//            {
                ImageSong s1 = new ImageSong(s.getId(), s.getSong_name(), s.getArtist(), s.getUri(), s.getLarge_image(), s.getMed_image(), s.getSmall_image());
                newSongs.add(s1);
//                imageSongService.save(s1);
//            }
        }

        System.out.println(songWithImgList);
        System.out.println(newSongs);
        return new ResponseEntity<>(newSongs,
                HttpStatus.OK);

    }
}