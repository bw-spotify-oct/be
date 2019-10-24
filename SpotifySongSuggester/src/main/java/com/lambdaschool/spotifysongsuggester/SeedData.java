//package com.lambdaschool.spotifysongsuggester;
//
//import com.lambdaschool.spotifysongsuggester.models.Song;
//import com.lambdaschool.spotifysongsuggester.models.User;
//import com.lambdaschool.spotifysongsuggester.services.SongService;
//import com.lambdaschool.spotifysongsuggester.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//
//@Transactional
//@Component
//public class SeedData implements CommandLineRunner
//{
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    SongService songService;
//
//
//    @Override
//    public void run(String[] args) throws Exception
//    {
//        User u1 = new User("admin",
//                "password");
//        userService.save(u1);
//
//        User u2 = new User("cinnamon",
//                "password");
//        userService.save(u2);
//
//        User u3 = new User("bob",
//                "password");
//        userService.save(u3);
//
//        Song s1 = new Song("498ZVInMGDkmmNVpSWqHiZ", "May We All", "Florida Georgia Line", "spotify:track:498ZVInMGDkmmNVpSWqHiZ" );
//        Song s2 = new Song("37KWDhJ3fyBAFVcC0Hutan", "May Be", "Crooks UK", "spotify:track:37KWDhJ3fyBAFVcC0Hutan" );
//        Song s3 = new Song("6Zm5LE6EcJsec8pvua0nt9", "May We All Someday Meedt Again", "The Fureys", "spotify:track:6Zm5LE6EcJsec8pvua0nt9" );
//        Song s4 = new Song("6GIei0QWZjbrNWNwtTpiQL", "May We All - Commentary", "Florida Georgia Line", "spotify:track:6GIei0QWZjbrNWNwtTpiQL" );
//
//        songService.save(s1);
//        songService.save(s2);
//        songService.save(s3);
//        songService.save(s4);
//
//
//    }
//}