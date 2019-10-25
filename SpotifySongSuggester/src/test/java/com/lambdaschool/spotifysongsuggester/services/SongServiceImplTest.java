package com.lambdaschool.spotifysongsuggester.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lambdaschool.spotifysongsuggester.SpotifySongSuggesterApplication;
import com.lambdaschool.spotifysongsuggester.exceptions.ResourceNotFoundException;
import com.lambdaschool.spotifysongsuggester.models.Song;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpotifySongSuggesterApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SongServiceImplTest
{
    @Autowired
    private SongService songService;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void AsetUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @After
    public void BtearDown() throws Exception
    {
    }

    @Test
    public void CfindSongByTrackId()
    {
        assertEquals("May We All", songService.findSongByTrackId("498ZVInMGDkmmNVpSWqHiZ").getSong_name());
    }

    @Test
    public void DfindSongById()
    {
        assertEquals("May Be", songService.findSongById(5).getSong_name());
    }

    @Test
    public void Esave()
    {
        String s5tID = "song5trackid";
        String s5sname = "song5song_name";
        String s5uri = "spotify:track:song5trackid";

        Song s5 = new Song(s5tID, s5sname, "s5artist", s5uri);

        Song addSong = songService.save(s5);

        assertNotNull(addSong);

        Song foundSong = songService.findSongById(addSong.getSongid());

        assertEquals(addSong.getSong_name(), foundSong.getSong_name());
    }

    @Test
    public void FfindAll()
    {
        assertEquals(4, songService.findAll(Pageable.unpaged()).size());
    }

    @Test
    public void Gupdate()
    {
        Song s6 = new Song(null, "New Name", "New Artist", null);
        s6.setSongid(568);

        System.out.println(songService.findAll(Pageable.unpaged()).toString());;
        Song updatedS1 = songService.update(s6,6);

        assertEquals("New Name".toLowerCase(), updatedS1.getSong_name());
        assertEquals("New Artist".toLowerCase(), updatedS1.getArtist());

    }

    @Test
    public void HdeleteFound()
    {
        songService.delete(7);
        assertEquals(3, songService.findAll(Pageable.unpaged()).size());
    }

    @Test
    public void IdeleteNotFound()
    {
        Assertions.assertThrows(ResourceNotFoundException.class, () ->  songService.delete(56));
    }
}