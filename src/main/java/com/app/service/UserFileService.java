package com.app.service;

import com.app.dto.RegistrationDto;
import com.app.entity.User;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface UserFileService {
    User find(Long id);
    User findByLogin(String login);
    void remove(Long id);
    User update(User user);
    boolean isLoginUnique(String login);
}
