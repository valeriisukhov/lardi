package com.app.dao;

import com.app.entity.UserRole;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface UserRoleDao extends Dao<UserRole>{
    UserRole findByName(String name);
}
