package com.app.service;

import com.app.dto.ContactDto;
import com.app.entity.Contact;
import com.app.entity.User;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface ContactService extends EntityService<Contact> {
    List<Contact> findAllByUser(User user);
    Contact findWithLogin(Long id, String login);
    void removeWithLogin(Long id, String login);
}
