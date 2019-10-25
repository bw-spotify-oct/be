package com.lambdaschool.spotifysongsuggester.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.spotifysongsuggester.models.*;
import com.lambdaschool.spotifysongsuggester.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

// mocking service to test controller
// Due to reliance on security, cannot test
//     getCurrentUserInfo
//     getCurrentUserName

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

        Song s1 = new Song("498ZVInMGDkmmNVpSWqHiZ", "May We All", "Florida Georgia Line", "spotify:track:498ZVInMGDkmmNVpSWqHiZ" );
        Song s2 = new Song("37KWDhJ3fyBAFVcC0Hutan", "May Be", "Crooks UK", "spotify:track:37KWDhJ3fyBAFVcC0Hutan" );
        Song s3 = new Song("6Zm5LE6EcJsec8pvua0nt9", "May We All Someday Meet Again", "The Fureys", "spotify:track:6Zm5LE6EcJsec8pvua0nt9" );

        ImageSong i1 = new ImageSong("4dCtnx80A8X9rJOMjBTugX", "Abacus", "Shahmen", "spotify:track:4dCtnx80A8X9rJOMjBTugX", "https://i.scdn.co/image/ab67616d0000b273abf3bc26d99bf63678dda17f", "https://i.scdn.co/image/ab67616d00001e02abf3bc26d99bf63678dda17f", "https://i.scdn.co/image/ab67616d00004851abf3bc26d99bf63678dda17f");
        ImageSong i2 = new ImageSong("4owRIrDAJPqNWGf2SGC3H8", "Mount Sinai", "$uicideBoy$", "spotify:track:4owRIrDAJPqNWGf2SGC3H8", "https://i.scdn.co/image/b2973a811b05159425b99c9286c951c83a9c9d8f", "https://i.scdn.co/image/e870655e804bc9d193ce19620b0dfa957b888251", "https://i.scdn.co/image/cbbfa5144bb2ea65a8f90e30272927a1c0cef1c4");
        ImageSong i3 = new ImageSong("7fMYjEMZYwNs2hO16BZstm", "Fuck Everybody", "Terror Reid", "spotify:track:7fMYjEMZYwNs2hO16BZstm", "https://i.scdn.co/image/ab67616d0000b273da692533de4109c09f5d525c", "https://i.scdn.co/image/ab67616d00001e02da692533de4109c09f5d525c", "https://i.scdn.co/image/ab67616d00004851da692533de4109c09f5d525c");

        //user 0
        ArrayList<Favorite> favorites0 = new ArrayList<>();
        favorites0.add(new Favorite(new User(), s1));
        favorites0.add(new Favorite(new User(), s2));
        favorites0.add(new Favorite(new User(), s3));

        ArrayList<FavoriteImageSong> favoriteImageSongs0 = new ArrayList<>();
        favoriteImageSongs0.add(new FavoriteImageSong(new User(), i1));
        favoriteImageSongs0.add(new FavoriteImageSong(new User(), i2));
        favoriteImageSongs0.add(new FavoriteImageSong(new User(), i3));

        User u0 = new User("testUser0", "password", favorites0, favoriteImageSongs0);

        u0.setUserid(100);
        userList.add(u0);

        //user 1
        ArrayList<Favorite> favorites1 = new ArrayList<>();
        favorites1.add(new Favorite(new User(), s2));
        favorites1.add(new Favorite(new User(), s3));

        ArrayList<FavoriteImageSong> favoriteImageSongs1 = new ArrayList<>();
        favoriteImageSongs1.add(new FavoriteImageSong(new User(), i2));
        favoriteImageSongs1.add(new FavoriteImageSong(new User(), i3));

        User u1 = new User("testUser1", "password", favorites1, favoriteImageSongs1);

        u1.setUserid(101);
        userList.add(u1);

        //user 2
        ArrayList<Favorite> favorites2 = new ArrayList<>();
        favorites2.add(new Favorite(new User(), s1));

        ArrayList<FavoriteImageSong> favoriteImageSongs2 = new ArrayList<>();
        favoriteImageSongs1.add(new FavoriteImageSong(new User(), i1));

        User u2 = new User("testUser2", "password", favorites2, favoriteImageSongs2);

        u2.setUserid(102);
        userList.add(u2);

        favorites2 = new ArrayList<>();
        favorites2.add(new Favorite(new User(), s2));
        favoriteImageSongs2 = new ArrayList<>();
        favoriteImageSongs2.add(new FavoriteImageSong(new User(), i2));
        User u3 = new User("testUser3", "password", favorites2, favoriteImageSongs2);
        u3.setUserid(103);
        userList.add(u3);

        favorites2 = new ArrayList<>();
        favorites2.add(new Favorite(new User(), s2));
        favoriteImageSongs2 = new ArrayList<>();
        favoriteImageSongs2.add(new FavoriteImageSong(new User(), i2));
        User u4 = new User("testUser4", "password", favorites2, favoriteImageSongs2);
        u4.setUserid(104);
        userList.add(u4);

        System.out.println("\n*** Seed Data ***");
        for (User u : userList)
        {
            System.out.println(u);
        }
        System.out.println("*** Seed Data ***\n");
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        String apiUrl = "/users/users";

        Mockito.when(userService.findAll(any(Pageable.class))).thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getCurrentUserInfo() throws Exception
    {
        // requires security which we have turned off for unit test
        // refer to integration testing for test of this method
    }

    // @PostMapping("/user/user/song/{trackid}")
    // userService.addSongToFav(userid, trackid);
    @Test
    public void postSongtoFavorites() throws Exception
    {
        // requires security which we have turned off for unit test

        /*
        String apiUrl = "/user/user/song/{trackid}";

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl,"37KWDhJ3fyBAFVcC0Hutan");

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
         */

    }

    @Test
    public void deleteSongFromFavorites() throws Exception
    {
        // requires security which we have turned off for unit test

        /*
        String apiUrl = "/users/user/song/{trackid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "37KWDhJ3fyBAFVcC0Hutan");

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
         */
    }

    @Test
    public void getCurrentUserFavorites() throws Exception
    {
        // requires security which we have turned off for unit test

        /*
        String apiUrl = "/users/user/favorites";

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
         */

    }

    // @PostMapping("/user/user/song/images/{trackid}")
    // userService.addSongToFav(userid, trackid);
    @Test
    public void postImageSongtoFavorites() throws Exception
    {
        // requires security which we have turned off for unit test

        /*
        String apiUrl = "/user/user/song/images/{trackid}";

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl, "4owRIrDAJPqNWGf2SGC3H8");

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
         */
    }

    @Test
    public void deleteImageSongFromFavorites() throws Exception
    {
        // requires security which we have turned off for unit test

        /*
        String apiUrl = "/users/user/song/images/{trackid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "4owRIrDAJPqNWGf2SGC3H8");

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
         */
    }

    @Test
    public void getCurrentUserFavoritesWithImages() throws Exception
    {
        // requires security which we have turned off for unit test

        /*
        String apiUrl = "/users/user/favorites/images";

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
         */

    }
}