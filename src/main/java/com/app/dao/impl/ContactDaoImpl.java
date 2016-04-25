package com.app.dao.impl;

import com.app.dao.ContactDao;
import com.app.entity.Contact;
import com.app.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Repository("contactDao")
public class ContactDaoImpl extends DaoImpl<Contact> implements ContactDao {
    private final transient Logger LOGGER = LoggerFactory.getLogger(getClass());
    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> findAllByUser(User user) {
        List<Contact> list = new ArrayList<>();
        try {
            list = entityManager.createQuery("select c from Contact c where c.user = :user", Contact.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
