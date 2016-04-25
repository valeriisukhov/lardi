package com.app.dao;

import com.app.entity.User;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface UserDao extends Dao<User>{
    User findByLogin(String login);
}
