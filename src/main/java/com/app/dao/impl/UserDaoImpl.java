package com.app.dao.impl;

import com.app.dao.UserDao;
import com.app.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Repository("userDao")
public class UserDaoImpl extends DaoImpl<User> implements UserDao {
    private final transient Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        try {
            User user = entityManager.createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
            return user;
        } catch (Exception e){
            return null;
        }
    }
}
