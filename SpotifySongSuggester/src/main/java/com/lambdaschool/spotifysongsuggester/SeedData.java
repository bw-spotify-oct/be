package com.lambdaschool.spotifysongsuggester;

import com.lambdaschool.spotifysongsuggester.models.User;
import com.lambdaschool.spotifysongsuggester.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    UserService userService;


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

    }
}