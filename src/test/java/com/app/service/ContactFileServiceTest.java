package com.app.service;

import com.app.ApplicationTests;
import com.app.entity.Contact;
import com.app.entity.User;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 */
public class ContactFileServiceTest extends ApplicationTests {

    @Autowired
    ContactFileService contactFileService;
    @Autowired
    UserFileService userFileService;
    private User testUser;
    private Contact testContact;
    @Value("${use.file.database.path}")
    private String fileDBPath;

    @Before
    public void init() throws IOException {
        testUser = userFileService.findByLogin("test");
        testContact = contactFileService.findWithLogin(1L,testUser.getLogin());
//        FileUtils.cleanDirectory(new File(fileDBPath+"contacts/"));
    }
    @Test
    public void findAllByUserTest(){
        List<Contact> result = contactFileService.findAllByUser(testUser);
        Assert.assertTrue(result != null);
        Assert.assertTrue(!result.isEmpty());
        Assert.assertTrue(result.get(0).equals(testContact));
        File f = new File(fileDBPath+"contacts/contact_"+testUser.getLogin()+".json");
        Assert.assertTrue(f.exists());
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
        contact = contactFileService.update(contact);
        Assert.assertTrue(contact.getId() == 2L);
        //TODO: check for string (make contact json) with string in file
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
        contact = contactFileService.update(contact);
        Assert.assertTrue(contact == null);
    }
    @Test
    public void removeContactTest(){
        boolean result = contactFileService.removeWithLogin(testContact.getId(), testUser.getLogin());
        Assert.assertTrue(result);
    }

}
