package com.app.dao.impl;

import com.app.dao.UserRoleDao;
import com.app.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends DaoImpl<UserRole> implements UserRoleDao {
    private final transient Logger LOGGER = LoggerFactory.getLogger(UserRoleDaoImpl.class);

    public UserRoleDaoImpl() {
        super(UserRole.class);
    }

    @Override
    public UserRole findByName(String name) {
        UserRole role = entityManager.createQuery("select r from UserRole r where r.name = :name", UserRole.class)
                .setParameter("name", name)
                .getSingleResult();
        return role;
    }
}
