package com.lambdaschool.spotifysongsuggester.controllers;

import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.DSSearchSong;
import com.lambdaschool.spotifysongsuggester.models.DSSongWithImg;
import com.lambdaschool.spotifysongsuggester.models.ErrorDetail;
import com.lambdaschool.spotifysongsuggester.models.Song;
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

    // taken from https://openlibrary.org/dev/docs/api/books
    // returns a list of books - you can include multiple ISBNs in a single request
    // This API returns a map instead of the standard list
    //
    // https://spotify-song-suggester-app.herokuapp.com/data/search/{search}

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
            Song s1 = new Song(s.getId(), s.getSong_name(), s.getArtist(), s.getUri());
            newSongs.add(s1);
            songService.save(s1);
        }

        System.out.println(ourSongs);
        System.out.println(newSongs);
        return new ResponseEntity<>(newSongs,
                HttpStatus.OK);
    }

    // https://spotify-song-suggester-app.herokuapp.com/data/recs/{trackid}
    @ApiOperation(value = "Retrieves Similar Songs by Track Id.", response = Song.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Songs Found", response = Song.class),
            @ApiResponse(code = 404, message = "Songs Not Found, Internal Data Server Error", response = ErrorDetail.class
            )})
    @GetMapping(value = "/recs/{trackid}",
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

        System.out.println(ourSongs);
        return new ResponseEntity<>(ourSongs,
                HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Similar Songs by Search Query", response = Song.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Songs Found", response = Song.class),
            @ApiResponse(code = 404, message = "Songs Not Found, Internal Data Server Error", response = ErrorDetail.class
            )})
    @GetMapping(value = "/recs/{search}",
            produces = {"application/json"})
    public ResponseEntity<?> ListRecsGivenSearch(HttpServletRequest request,
                                                 @PathVariable
                                                         String search)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        String requestURL = "https://spotify-api-helper.herokuapp.com/auto_search/DReaI4d55IIaiD6P9/" + search;

        ParameterizedTypeReference<List<DSSongWithImg>> responseType = new ParameterizedTypeReference<List<DSSongWithImg>>()
        {
        };
        ResponseEntity<List<DSSongWithImg>> responseEntity = restTemplate.exchange(requestURL,
                HttpMethod.GET,
                null,
                responseType);

        List<DSSongWithImg> ourSongs = responseEntity.getBody();

        System.out.println(ourSongs);
        return new ResponseEntity<>(ourSongs,
                HttpStatus.OK);
    }


}