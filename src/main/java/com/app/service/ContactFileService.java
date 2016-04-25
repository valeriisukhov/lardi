package com.app.service;

import com.app.dto.ContactDto;
import com.app.entity.Contact;
import com.app.entity.User;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */

public interface ContactFileService {
    Contact findWithLogin(Long id, String login);
    Contact update(Contact contact);
    boolean removeWithLogin(Long id, String login);
    List<Contact> findAllByUser(User user);
}
