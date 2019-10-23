package com.lambdaschool.spotifysongsuggester;

import com.lambdaschool.spotifysongsuggester.models.Favorites;
import com.lambdaschool.spotifysongsuggester.models.Song;
import com.lambdaschool.spotifysongsuggester.models.User;
import com.lambdaschool.spotifysongsuggester.services.SongService;
import com.lambdaschool.spotifysongsuggester.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    UserService userService;

    @Autowired
    SongService songService;


    @Override
    public void run(String[] args) throws Exception
    {
        User u1 = new User("admin",
                           "password");
        userService.save(u1);

        User u2 = new User("cinnamon",
                "password");
        userService.save(u2);

        User u3 = new User("bob",
                "password");
        userService.save(u3);

        Song s1 = new Song("498ZVInMGDkmmNVpSWqHiZ", "May We All", "Florida Georgia Line" );
        Song s2 = new Song("37KWDhJ3fyBAFVcC0Hutan", "May Be", "Crooks UK" );
        Song s3 = new Song("6Zm5LE6EcJsec8pvua0nt9", "May We All Someday Meedt Again", "The Fureys" );
        Song s4 = new Song("6GIei0QWZjbrNWNwtTpiQL", "May We All - Commentary", "Florida Georgia Line" );

        songService.save(s1);
        songService.save(s2);
        songService.save(s3);
        songService.save(s4);

//        ArrayList<Favorites> favs = new ArrayList<>();
//
////        favs.add(new Favorites(new User(), s1));
////        favs.add(new Favorites(new User(), s2));
////        favs.add(new Favorites(new User(), s3));
////        favs.add(new Favorites(new User(), s4));
//
//        favs.add(new Favorites(u1, s1));
//        favs.add(new Favorites(u1, s2));
//        favs.add(new Favorites(u1, s3));
//        favs.add(new Favorites(u1, s4));
//
//        userService.update(new User(u1.getUsername(), u1.getPassword(), favs), u1.getUserid());

//        userService.addSongToFav(u1.getUserid(),s1.getSongid());
//        userService.addSongToFav(u1.getUserid(), s2.getSongid());
//        userService.addSongToFav(u1.getUserid(), s3.getSongid());
//        userService.addSongToFav(u1.getUserid(), s4.getSongid());
//
//        userService.addSongToFav(u2.getUserid(), s1.getSongid());
//        userService.addSongToFav(u2.getUserid(), s4.getSongid());
//
//        userService.addSongToFav(u3.getUserid(), s2.getSongid());
//        userService.addSongToFav(u3.getUserid(), s3.getSongid());

//        userService.addSongToFav(u1.getUserid(), s1.getTrackid());
//        userService.addSongToFav(u1.getUserid(), s2.getTrackid());
//        userService.addSongToFav(u1.getUserid(), s3.getTrackid());
//        userService.addSongToFav(u1.getUserid(), s4.getTrackid());
//
//        userService.addSongToFav(u2.getUserid(), s1.getTrackid());
//        userService.addSongToFav(u2.getUserid(), s4.getTrackid());
//
//        userService.addSongToFav(u3.getUserid(), s2.getTrackid());
//        userService.addSongToFav(u3.getUserid(), s3.getTrackid());


    }
}