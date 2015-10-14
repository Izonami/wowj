package com.wow.loginserver.model.data;

import java.util.Objects;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public final class AccountInfo
{
    private final String _login;
    private final String _passHash;

    public AccountInfo(final String login, final String passHash)
    {
        Objects.requireNonNull(login, "login");
        Objects.requireNonNull(passHash, "passHash");

        if(login.isEmpty())
        {
            throw new IllegalArgumentException("login");
        }
        if(passHash.isEmpty())
        {
            throw new IllegalArgumentException("passHash");
        }

        _login = login.toLowerCase();
        _passHash = passHash;
    }

    public boolean checkPassHash(final String passHash)
    {
        return _passHash.equals(passHash);
    }

    public String getLogin()
    {
        return _login;
    }
}
