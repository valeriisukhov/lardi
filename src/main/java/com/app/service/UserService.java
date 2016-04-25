package com.app.service;

import com.app.dto.RegistrationDto;
import com.app.entity.User;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface UserService extends EntityService<User> {
    User findByLogin(String login);
    boolean isLoginUnique(String login);
}
