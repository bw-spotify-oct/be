package com.lambdaschool.spotifysongsuggester;
import com.lambdaschool.spotifysongsuggester.models.ImageSong;
import com.lambdaschool.spotifysongsuggester.models.Song;
import com.lambdaschool.spotifysongsuggester.models.User;
import com.lambdaschool.spotifysongsuggester.services.ImageSongService;
import com.lambdaschool.spotifysongsuggester.services.SongService;
import com.lambdaschool.spotifysongsuggester.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    @Autowired
    ImageSongService imageSongService;

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

        Song s1 = new Song("498ZVInMGDkmmNVpSWqHiZ", "May We All", "Florida Georgia Line", "spotify:track:498ZVInMGDkmmNVpSWqHiZ" );
        Song s2 = new Song("37KWDhJ3fyBAFVcC0Hutan", "May Be", "Crooks UK", "spotify:track:37KWDhJ3fyBAFVcC0Hutan" );
        Song s3 = new Song("6Zm5LE6EcJsec8pvua0nt9", "May We All Someday Meet Again", "The Fureys", "spotify:track:6Zm5LE6EcJsec8pvua0nt9" );
        Song s4 = new Song("6GIei0QWZjbrNWNwtTpiQL", "May We All - Commentary", "Florida Georgia Line", "spotify:track:6GIei0QWZjbrNWNwtTpiQL" );

        songService.save(s1);
        songService.save(s2);
        songService.save(s3);
        songService.save(s4);

        ImageSong i1 = new ImageSong("4dCtnx80A8X9rJOMjBTugX", "Abacus", "Shahmen", "spotify:track:4dCtnx80A8X9rJOMjBTugX", "https://i.scdn.co/image/ab67616d0000b273abf3bc26d99bf63678dda17f", "https://i.scdn.co/image/ab67616d00001e02abf3bc26d99bf63678dda17f", "https://i.scdn.co/image/ab67616d00004851abf3bc26d99bf63678dda17f");
        ImageSong i2 = new ImageSong("4owRIrDAJPqNWGf2SGC3H8", "Mount Sinai", "$uicideBoy$", "spotify:track:4owRIrDAJPqNWGf2SGC3H8", "https://i.scdn.co/image/b2973a811b05159425b99c9286c951c83a9c9d8f", "https://i.scdn.co/image/e870655e804bc9d193ce19620b0dfa957b888251", "https://i.scdn.co/image/cbbfa5144bb2ea65a8f90e30272927a1c0cef1c4");
        ImageSong i3 = new ImageSong("7fMYjEMZYwNs2hO16BZstm", "Fuck Everybody", "Terror Reid", "spotify:track:7fMYjEMZYwNs2hO16BZstm", "https://i.scdn.co/image/ab67616d0000b273da692533de4109c09f5d525c", "https://i.scdn.co/image/ab67616d00001e02da692533de4109c09f5d525c", "https://i.scdn.co/image/ab67616d00004851da692533de4109c09f5d525c");
        ImageSong i4 = new ImageSong("6XH0KeCZ0nRysAeSJYRFFg", "Devil's Work", "Joyner Lucas", "spotify:track:6XH0KeCZ0nRysAeSJYRFFg", "https://i.scdn.co/image/ab67616d0000b273cbcb1a1ee403d164ed482aad", "https://i.scdn.co/image/ab67616d00001e02cbcb1a1ee403d164ed482aad", "https://i.scdn.co/image/ab67616d00004851cbcb1a1ee403d164ed482aad");

        imageSongService.save(i1);
        imageSongService.save(i2);
        imageSongService.save(i3);
        imageSongService.save(i4);

    }
}