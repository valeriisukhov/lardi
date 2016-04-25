package com.app.service.impl;

import com.app.dao.UserDao;
import com.app.entity.User;
import com.app.service.EntityService;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Service("userService")
public class UserServiceImpl implements UserService, EntityService<User> {
    private final transient Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    public User find(Long id) {
        return userDao.find(id);
    }
    @Transactional
    public void add(User user) {
        userDao.update(user);
    }
    @Transactional
    public User update(User user) {
        if (!isRegistrationFormValid(user)){
            return null;
        }
        user.setPassword(new ShaPasswordEncoder().encodePassword(user.getPassword(), null));
        User result = userDao.update(user);
        return result;
    }
    @Transactional
    public void remove(Long id) {
        User user = userDao.find(id);
        userDao.remove(user);
    }
    public List<User> list() {
        return userDao.list();
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public boolean isLoginUnique(String login) {
        return userDao.findByLogin(login) == null;
    }

    private boolean isRegistrationFormValid(User user) {
        boolean result = false;
        if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty() && !user.getFirstName().isEmpty() &&
                !user.getLastName().isEmpty() && !user.getForeName().isEmpty()) {
            if (!isLoginUnique(user.getLogin())) {
                return false;
            }
            String loginPattern = "^[a-zA-Z]{3,15}$";
            java.util.regex.Pattern p1 = java.util.regex.Pattern.compile(loginPattern);
            java.util.regex.Matcher m1 = p1.matcher(user.getLogin());
            result = m1.matches();
            if (!result){
                return result;
            }
            String passwordPattern = "^[\\s\\S\\d\\w]{5,15}$";
            java.util.regex.Pattern p2 = java.util.regex.Pattern.compile(passwordPattern);
            java.util.regex.Matcher m2 = p2.matcher(user.getPassword());
            result = m2.matches();
        }
        return result;
    }
}
