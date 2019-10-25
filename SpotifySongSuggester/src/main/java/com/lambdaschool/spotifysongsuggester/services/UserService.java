package com.lambdaschool.spotifysongsuggester.services;

import com.lambdaschool.spotifysongsuggester.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService
{
    UserDetails loadUserByUsername(String username);

    List<User> findAll(Pageable pageable);

    List<User> findByNameContaining(String username,
                                    Pageable pageable);

    User findUserById(long id);

    User findByName(String name);

    void delete(long id);

    User save(User user);

    User update(User user,
                long id);

    void addSongToFav(long userid, String trackid);

    void deleteSongFromFav(long userid, String trackid);

    void addImageSongToFav(long userid, String trackid);

    void deleteImageSongFromFav(long userid, String trackid);
}