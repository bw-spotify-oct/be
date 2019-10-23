package com.lambdaschool.spotifysongsuggester.controllers;

import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.ErrorDetail;
import com.lambdaschool.spotifysongsuggester.models.User;
import com.lambdaschool.spotifysongsuggester.services.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // http://localhost:2019/users/users/?page=1&size=1
    // http://localhost:2019/users/users/?sort=username,desc&sort=<field>,asc
    @ApiOperation(value = "returns all Users",
                  response = User.class,
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
    @GetMapping(value = "/users",
                produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(HttpServletRequest request,
                                          @PageableDefault(page = 0,
                                                           size = 100)
                                                  Pageable pageable)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<User> myUsers = userService.findAll(pageable);
        return new ResponseEntity<>(myUsers,
                                    HttpStatus.OK);
    }


    // http://localhost:2019/users/user/7
    @GetMapping(value = "/user/{userId}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserById(HttpServletRequest request,
                                         @PathVariable
                                                 Long userId)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/cinnamon
    @GetMapping(value = "/user/name/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserByName(HttpServletRequest request,
                                           @PathVariable
                                                   String userName)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/getusername
    @GetMapping(value = "/getusername",
                produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request,
                                                Authentication authentication)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        return new ResponseEntity<>(authentication.getPrincipal(),
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/getuserinfo
    @GetMapping(value = "/getuserinfo",
                produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(HttpServletRequest request,
                                                Authentication authentication)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    @ApiOperation(value = "Edits User associated with Sent Id.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Edited Successfully", response = void.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Error Editing User", response = ErrorDetail.class
            )})
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody
                                                User updateUser,
                                        @PathVariable
                                                long id)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.update(updateUser,
                           id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/users/user/14
    @ApiOperation(value = "Deletes a User by Id", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Deleted Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting User", response = ErrorDetail.class
            )})
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request,
                                            @PathVariable
                                                    long id)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Saves Song to User Favorites.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Song Added to Favorites Successfully", response = void.class),
            @ApiResponse(code = 404, message = "Song or User Not Found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Error Adding Song to Favorites", response = ErrorDetail.class
            )})
    @PostMapping("/{userid}/song/{songid}")
    public ResponseEntity<?> postSongtoFavorites(HttpServletRequest request, @PathVariable long userid,
                                            @PathVariable long songid)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.addSongToFav(userid, songid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Deletes a Song from Favorites", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Song Deleted Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting Song", response = ErrorDetail.class
            )})
    @DeleteMapping("/{userid}/song/{songid}")
    public ResponseEntity<?> deleteSongFromFavorites(HttpServletRequest request, @PathVariable long userid,
                                                     @PathVariable long songid)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.deleteSongFromFav(userid, songid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @ApiOperation(value = "Saves Song to User Favorites.", response = void.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Song Added to Favorites Successfully", response = void.class),
//            @ApiResponse(code = 404, message = "Song or User Not Found", response = ErrorDetail.class),
//            @ApiResponse(code = 500, message = "Error Adding Song to Favorites", response = ErrorDetail.class
//            )})
//    @PostMapping("/{userid}/song/{trackid}")
//    public ResponseEntity<?> postSongtoFavorites(HttpServletRequest request, @PathVariable long userid,
//                                                 @PathVariable String trackid)
//    {
//        logger.trace(request.getMethod()
//                .toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        userService.addSongToFav(userid, trackid);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @ApiOperation(value = "Deletes a Song from Favorites", response = void.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Song Deleted Successfully", response = void.class),
//            @ApiResponse(code = 500, message = "Error deleting Song", response = ErrorDetail.class
//            )})
//    @DeleteMapping("/{userid}/song/{trackid}")
//    public ResponseEntity<?> deleteSongFromFavorites(HttpServletRequest request, @PathVariable long userid,
//                                                     @PathVariable String trackid)
//    {
//        logger.trace(request.getMethod()
//                .toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        userService.deleteSongFromFav(userid, trackid);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}