package com.lambdaschool.spotifysongsuggester.controllers;

import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.User;
import com.lambdaschool.spotifysongsuggester.models.UserMinimum;
import com.lambdaschool.spotifysongsuggester.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Loggable
@RestController
public class OpenController
{
    private static final Logger logger = LoggerFactory.getLogger(OpenController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "/createnewuser",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest httpServletRequest,
                                        @RequestParam(defaultValue = "true")
                                                boolean getaccess,
                                        @Valid
                                        @RequestBody
                                                UserMinimum newminuser) throws URISyntaxException
    {
        logger.trace(httpServletRequest.getMethod()
                                       .toUpperCase() + " " + httpServletRequest.getRequestURI() + " accessed");

        // Create the user
        User newuser = new User();

        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());

        newuser = userService.save(newuser);

        // set the location header for the newly created resource - to another controller!
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                                                    .buildAndExpand(newuser.getUserid())
                                                    .toUri();
        responseHeaders.setLocation(newUserURI);

        String theToken = "";

        return new ResponseEntity<>(theToken,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }

    @ApiIgnore
    @GetMapping("favicon.ico")
    void returnNoFavicon()
    {
        logger.trace("favicon.ico endpoint accessed!");
    }
}