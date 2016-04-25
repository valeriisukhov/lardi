package com.app.service;

import com.app.ApplicationTests;
import com.app.entity.Contact;
import com.app.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public class ContactServiceTest extends ApplicationTests {

    @Autowired
    ContactService contactService;
    @Autowired
    UserService userService;
    private User testUser;
    private Contact testContact;

    @Before
    public void init() throws IOException {
        testUser = userService.findByLogin("test");
        testContact = contactService.find(1L);
    }
    @Test
    public void findAllByUserTest(){
        List<Contact> result = contactService.findAllByUser(testUser);
        Assert.assertTrue(result != null);
        Assert.assertTrue(!result.isEmpty());
        Assert.assertTrue(result.get(0).toString().equals(testContact.toString()));
    }
    @Test
    public void storeContactTest(){
        Contact contact = new Contact();
        contact.setFirstName("firstname");
        contact.setLastName("lastname");
        contact.setForeName("forename");
        contact.setEmail("email@email.com");
        contact.setMobilePhone("+380501112233");
        contact.setHomePhone("4444444");
        contact.setAddress("address");
        contact.setUser(testUser);
        contact = contactService.update(contact);
        Assert.assertTrue(contact.getId() == 2L);
    }
    @Test
    public void storeContactsWrongDataTest(){
        Contact contact = new Contact();
        contact.setFirstName("ttt");
        contact.setLastName("ttt");
        contact.setForeName("tt");
        contact.setEmail("ertlkdfgsnkdsgh");
        contact.setMobilePhone("+38050");
        contact.setHomePhone("tele");
        contact.setAddress(null);
        contact.setUser(testUser);
        contact = contactService.update(contact);
        Assert.assertTrue(contact == null);
    }
    @Test
    public void removeContactTest(){
        int result = contactService.list().size();
        contactService.remove(testContact.getId());
        Assert.assertTrue(result > contactService.list().size());
    }

}
