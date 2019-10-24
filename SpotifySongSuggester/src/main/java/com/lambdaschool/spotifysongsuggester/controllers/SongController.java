package com.lambdaschool.spotifysongsuggester.controllers;

import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.ErrorDetail;
import com.lambdaschool.spotifysongsuggester.models.Song;
import com.lambdaschool.spotifysongsuggester.services.SongService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/songs")
public class SongController
{
    @Autowired
    private SongService songService;
    private static final Logger logger = LoggerFactory.getLogger(SongService.class);


    @ApiOperation(value = "Deletes a Song by Id", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author Deleted Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting Author", response = ErrorDetail.class
            )})
    @DeleteMapping("/song/{songid}")
    public ResponseEntity<?> deleteSongById(HttpServletRequest request, @PathVariable long songid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        songService.delete(songid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a Song by Track Id.", response = Song.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author Found", response = Song.class),
            @ApiResponse(code = 404, message = "Author Not Found", response = ErrorDetail.class
            )})
    @GetMapping(value = "/track/{trackid}",
            produces = {"application/json"})
    public ResponseEntity<?> getSongByTrackId(HttpServletRequest request, @PathVariable String trackid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        Song s = songService.findSongByTrackId(trackid);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds a New Song.", notes = "The newly created song id will be sent in the location header.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New Song Added Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error Adding New Song", response = ErrorDetail.class
            )})
    @PostMapping(value = "/song",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewSong( HttpServletRequest request, @Valid @RequestBody Song song) throws URISyntaxException
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        Song newSong = songService.save(song);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newAuthorURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{authorid}").buildAndExpand(newSong.getSongid()).toUri();
        responseHeaders.setLocation(newAuthorURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "returns all Songs",
            response = Song.class,
            responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve (0..N)"), @ApiImplicitParam(name = "size",
            dataType = "integer",
            paramType = "query",
            value = "Number of records per page."), @ApiImplicitParam(name = "sort",
            allowMultiple = true,
            dataType = "string",
            paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). " + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    @GetMapping(value = "/songs",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(HttpServletRequest request,
                                          @PageableDefault(page = 0,
                                                  size = 100)
                                                  Pageable pageable)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Song> myUsers = songService.findAll(pageable);
        return new ResponseEntity<>(myUsers,
                HttpStatus.OK);
    }
}
