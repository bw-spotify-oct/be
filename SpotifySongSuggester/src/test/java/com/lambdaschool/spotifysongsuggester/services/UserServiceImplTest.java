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
class USerServiceImplTest
{
    @Autowired
    private UserService userService;

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
    public void CFindByName()
    {
        assertEquals(3, userService.findByName("bob").getUserid());
    }
}