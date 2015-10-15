package org.wowj.auth.service;

import org.wowj.auth.entity.AccountEntity;

import java.util.List;

/**
 * Created by kuksin-mv on 15.10.2015.
 */
public interface AccountService
{
    AccountEntity readAccount(Long id);
    List<AccountEntity> readAccounts();
    AccountEntity createOrUpdateAccount(AccountEntity accountEntity);
    void deleteAccount(AccountEntity accountEntity);
    AccountEntity readAccountByUserName(String login);
}
