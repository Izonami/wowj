package org.wowj.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wowj.auth.entity.AccountEntity;

/**
 * Created by kuksin-mv on 15.10.2015.
 */
@Repository
@Transactional
public interface AccountRepository extends JpaRepository<AccountEntity, Long>
{
    @Query("SELECT a FROM AccountEntity a WHERE a.username = :username")
    AccountEntity findByUsername(@Param("username") String username);
}
