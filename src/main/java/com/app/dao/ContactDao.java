package com.app.dao;

import com.app.entity.Contact;
import com.app.entity.User;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface ContactDao extends Dao<Contact> {
    List<Contact> findAllByUser(User user);
}
