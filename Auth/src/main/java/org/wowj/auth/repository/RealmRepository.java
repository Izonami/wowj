package org.wowj.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wowj.auth.entity.RealmEntity;

/**
 * Created by kuksin-mv on 15.10.2015.
 */
@Repository
public interface RealmRepository extends JpaRepository<RealmEntity, Integer>
{
}
