package com.lambdaschool.spotifysongsuggester.services;

import com.lambdaschool.spotifysongsuggester.exceptions.ResourceFoundException;
import com.lambdaschool.spotifysongsuggester.exceptions.ResourceNotFoundException;
import com.lambdaschool.spotifysongsuggester.logging.Loggable;
import com.lambdaschool.spotifysongsuggester.models.Song;
import com.lambdaschool.spotifysongsuggester.models.User;
import com.lambdaschool.spotifysongsuggester.repositories.SongRepository;
import com.lambdaschool.spotifysongsuggester.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private SongRepository songrepos;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userrepos.findByUsername(username.toLowerCase());
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername().toLowerCase(),
                user.getPassword(), user.getAuthority()
        );
    }

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username,
                                           Pageable pageable)
    {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase(),
                pageable);
    }

    @Override
    public List<User> findAll(Pageable pageable)
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll(pageable)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user)
    {
        if (userrepos.findByUsername(user.getUsername().toLowerCase()) != null)
        {
            throw new ResourceFoundException(user.getUsername() + " is already taken!");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setFavorites(user.getFavorites());

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user,
                       long id)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        User authenticatedUser = userrepos.findByUsername(authentication.getName());

        if (id == authenticatedUser.getUserid())
        {
            User currentUser = findUserById(id);

            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername().toLowerCase());
            }

            if (user.getPassword() != null)
            {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            if (user.getFavorites() != null)
            {
                currentUser.setFavorites(user.getFavorites());
            }

            return userrepos.save(currentUser);
        } else
        {
            throw new ResourceNotFoundException(id + " Not current user");
        }
    }

    @Transactional
    @Override
    public void addSongToFav(long userid, String trackid) throws ResourceNotFoundException, ResourceFoundException
    {
        userrepos.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));

        Song newSong = songrepos.findSongByTrackid(trackid);

        if(userrepos.checkSonginFavorites(userid, newSong.getSongid()).getCount() <=0)
        {
            userrepos.insertSongintoFavorites(userid, newSong.getSongid());
        }
        else
        {
            throw new ResourceFoundException("Song already in User Favorites");
        }
    }

    @Transactional
    @Override
    public void deleteSongFromFav(long userid, String trackid) throws ResourceNotFoundException
    {
        Song newSong = songrepos.findSongByTrackid(trackid);

        if(userrepos.checkSonginFavorites(userid, newSong.getSongid()).getCount() <=0)
        {
            throw new ResourceNotFoundException("Song not in User Favorites");

        }
        else
        {
            userrepos.deleteSongfromFavorites(userid, newSong.getSongid());
        }
    }
}
