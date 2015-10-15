package org.wowj.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wowj.auth.entity.AccountEntity;
import org.wowj.auth.repository.AccountRepository;
import org.wowj.auth.service.AccountService;

import java.util.List;

/**
 * Created by kuksin-mv on 15.10.2015.
 */
@Service
public class AccountServiceImpl implements AccountService
{
    @Autowired
    private AccountRepository accountRep;

    @Override
    public AccountEntity readAccount(final Long id)
    {
        return this.accountRep.findOne(id);
    }

    @Override
    public List<AccountEntity> readAccounts()
    {
        return this.accountRep.findAll();
    }

    @Override
    public AccountEntity createOrUpdateAccount(final AccountEntity accountEntity)
    {
        return this.accountRep.save(accountEntity);
    }

    @Override
    public void deleteAccount(final AccountEntity accountEntity)
    {
        this.accountRep.delete(accountEntity);
    }

    @Override
    public AccountEntity readAccountByUserName(final String login)
    {
        return this.accountRep.findByUsername(login);
    }
}
