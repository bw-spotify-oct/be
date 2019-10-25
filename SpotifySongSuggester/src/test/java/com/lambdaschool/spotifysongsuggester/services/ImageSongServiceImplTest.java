package com.lambdaschool.spotifysongsuggester.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lambdaschool.spotifysongsuggester.SpotifySongSuggesterApplication;
import com.lambdaschool.spotifysongsuggester.exceptions.ResourceNotFoundException;
import com.lambdaschool.spotifysongsuggester.models.ImageSong;
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
class ImageSongServiceImplTest
{
    @Autowired
    private ImageSongService imageSongService;

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
    public void CfindImageSongByTrackId()
    {
        assertEquals("Abacus", imageSongService.findImageSongByTrackId("4dCtnx80A8X9rJOMjBTugX").getSong_name());
    }

    @Test
    public void DfindImageSongById()
    {
        assertEquals("Devil's Work", imageSongService.findImageSongById(11).getSong_name());
    }

    @Test
    public void Esave()
    {
        String i5tID = "imgsong5trackid";
        String i5sname = "imgsong5song_name";
        String i5uri = "spotify:track:imgsong5trackid";
        String i5imageurl = "http://i5imageurl";

        ImageSong i5 = new ImageSong(i5tID, i5sname, "i5artist", i5uri, i5imageurl, i5imageurl, i5imageurl);

        ImageSong addImageSong = imageSongService.save(i5);

        assertNotNull(addImageSong);

        ImageSong foundImageSong = imageSongService.findImageSongById(addImageSong.getImagesongid());

        assertEquals(addImageSong.getSong_name(), foundImageSong.getSong_name());
    }

    @Test
    public void FfindAll()
    {
        assertEquals(4, imageSongService.findAll(Pageable.unpaged()).size());
    }

    @Test
    public void Gupdate()
    {
        ImageSong i6 = new ImageSong(null, "New Name", "New Artist", null, null, null, null);
        i6.setImagesongid(568);

        System.out.println(imageSongService.findAll(Pageable.unpaged()).toString());;
        ImageSong updatedI1 = imageSongService.update(i6,8);

        assertEquals("New Name".toLowerCase(), updatedI1.getSong_name());
        assertEquals("New Artist".toLowerCase(), updatedI1.getArtist());

    }

    @Test
    public void HdeleteFound()
    {
        imageSongService.delete(9);
        assertEquals(3, imageSongService.findAll(Pageable.unpaged()).size());
    }

    @Test
    public void IdeleteNotFound()
    {
        Assertions.assertThrows(ResourceNotFoundException.class, () ->  imageSongService.delete(586));
    }
}