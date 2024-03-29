package com.lambdaschool.spotifysongsuggester.repositories;

import com.lambdaschool.spotifysongsuggester.models.User;
import com.lambdaschool.spotifysongsuggester.view.JustTheCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long>
{
    User findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String name,
                                                  Pageable pageable);

    @Query(value = "SELECT COUNT(*) as count FROM favorites WHERE userid = :userid AND songid = :songid", nativeQuery = true)
    JustTheCount checkSonginFavorites(long userid, long songid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM favorites WHERE userid = :userid AND songid = :songid")
    void deleteSongfromFavorites(long userid,
                                 long songid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO favorites(userid, songid) VALUES (:userid, :songid)",
            nativeQuery = true)
    void insertSongintoFavorites(long userid,
                                 long songid);

    @Query(value = "SELECT COUNT(*) as count FROM favoriteimagesongs WHERE userid = :userid AND imagesongid = :imagesongid", nativeQuery = true)
    JustTheCount checkImageSonginFavorites(long userid, long imagesongid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM favoriteimagesongs WHERE userid = :userid AND imagesongid = :imagesongid")
    void deleteImageSongfromFavorites(long userid,
                                 long imagesongid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO favoriteimagesongs(userid, imagesongid) VALUES (:userid, :imagesongid)",
            nativeQuery = true)
    void insertImageSongintoFavorites(long userid,
                                 long imagesongid);
}
