package com.app.service;

import com.app.entity.UserRole;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface UserRoleService extends EntityService<UserRole> {
    UserRole findByName(String name);
}
