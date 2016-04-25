package com.app.service;

import com.app.ApplicationTests;
import com.app.entity.User;
import com.app.entity.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public class UserServiceTest extends ApplicationTests {
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserService userService;
    private User testUser;
    private UserRole testUserRole;

    @Before
    public void init() throws IOException {
        testUser = userService.list().get(0);
        testUserRole = userRoleService.findByName("ROLE_USER");
    }

    @Test
    public void findUserByIdTest(){
        User result = userService.find(testUser.getId());
        Assert.assertTrue(result != null);
    }
    @Test
    public void findUserByLoginTest(){
        User result = userService.findByLogin(testUser.getLogin());
        Assert.assertTrue(result != null);
        Assert.assertTrue(result.getId() > 0);
    }
    @Test
    public void storeUserTest(){
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setForeName("forename");
        user.setRole(testUserRole);
        user.setContacts(null);
        user = userService.update(user);
        Assert.assertTrue(user != null);
        Assert.assertTrue(user.getId() > 0);
    }
    @Test
    public void storeUserWithAlreadyExistLoginTest(){
        User user = new User();
        user.setLogin("test");
        user.setPassword("testtest");
        user.setFirstName("test");
        user.setLastName("test");
        user.setForeName("test");
        user.setRole(testUserRole);
        user.setContacts(null);
        user = userService.update(user);
        Assert.assertTrue(user == null);
    }
    @Test
    public void storeUserWrongDataTest(){
        User user = new User();
        user.setLogin("new");
        user.setPassword("pwd");
        user.setFirstName("t");
        user.setLastName("t");
        user.setForeName("t");
        user.setRole(testUserRole);
        user.setContacts(null);
        user = userService.update(user);
        Assert.assertTrue(user == null);
    }

}
