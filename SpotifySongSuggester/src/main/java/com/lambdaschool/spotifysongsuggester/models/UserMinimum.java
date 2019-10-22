package com.lambdaschool.spotifysongsuggester.models;

import com.lambdaschool.spotifysongsuggester.logging.Loggable;

@Loggable
public class UserMinimum
{
    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
