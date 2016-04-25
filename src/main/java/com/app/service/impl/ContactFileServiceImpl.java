package com.app.service.impl;

import com.app.entity.Contact;
import com.app.entity.User;
import com.app.service.ContactFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Service("contactFileService")
public class ContactFileServiceImpl implements ContactFileService {
    private final transient Logger LOGGER = LoggerFactory.getLogger(ContactFileServiceImpl.class);
    @Value("${use.file.database}")
    private boolean useFileDB;
    @Value("${use.file.database.path}")
    public String fileDBPath;

    @Override
    public Contact findWithLogin(Long id, String login) {
        if (useFileDB) {
            ObjectMapper mapper = new ObjectMapper();
            List<Contact> contactData = new ArrayList<>();
            try {
                contactData = mapper.readValue(new File(fileDBPath + "contacts/contact_" + login + ".json"), mapper.getTypeFactory().constructCollectionType(List.class, Contact.class));
            } catch (IOException e) {
                LOGGER.warn("No user`s contact file found!");
                return null;
            }
            contactData = contactData.stream().filter(contact -> contact.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
            if (contactData.size() > 0) {
                return contactData.get(0);
            }
        }
        return null;
    }
    @Transactional
    public Contact update(Contact contact) {
        if (useFileDB){
            if (!isContactFormValid(contact)){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            List<Contact> contactData = new ArrayList<>();
            try {
                contactData = mapper.readValue(new File(fileDBPath+"contacts/contact_"+contact.getUser().getLogin()+".json"), mapper.getTypeFactory().constructCollectionType(List.class, Contact.class));
            } catch (IOException e) {
                LOGGER.warn("No user`s contact file found!");
                // user has no contacts, we need to create file through first time
                try {
                    mapper.writeValue(new File(fileDBPath + "contacts/contact_" + contact.getUser().getLogin() + ".json"), new ArrayList<>());
                } catch (IOException e1) {
                    LOGGER.warn("Can`t create user contact file");
                    return null;
                }
            }
            if (contactData.size() > 0){
                boolean updated = false;
                for (Contact c: contactData){
                    if (c.getId().equals(contact.getId())){
                        c.setFirstName(contact.getFirstName());
                        c.setLastName(contact.getLastName());
                        c.setForeName(contact.getForeName());
                        c.setAddress(contact.getAddress());
                        c.setEmail(contact.getEmail());
                        c.setHomePhone(contact.getHomePhone());
                        c.setMobilePhone(contact.getMobilePhone());
                        updated = true;
                    }
                }
                if (!updated){
                    contact.setId(getCurrentContactFileId(contact.getUser().getLogin()));
                    contactData.add(contact);
                }
            } else {
                contact.setId(getCurrentContactFileId(contact.getUser().getLogin())+1L);
                contactData.add(contact);
            }
            try {
                mapper.writeValue(new File(fileDBPath+"contacts/contact_"+contact.getUser().getLogin()+".json"), contactData);
            } catch (IOException e) {
                LOGGER.warn("Can`t write contact file");
                return null;
            }
            return contact;
        }
        return null;
    }

    @Transactional
    public boolean removeWithLogin(Long id, String login) {
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<Contact> contactData = new ArrayList<>();
            try {
                contactData = mapper.readValue(new File(fileDBPath+"contacts/contact_"+login+".json"), mapper.getTypeFactory().constructCollectionType(List.class, Contact.class));
            } catch (IOException e) {
                LOGGER.warn("Can`t obtain contact data");
                return false;
            }
            contactData = contactData.stream().filter(contact -> !contact.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
            try {
                mapper.writeValue(new File(fileDBPath+"contacts/contact_"+login+".json"), contactData);
                return true;
            } catch (IOException e) {
                LOGGER.warn("Error during contact deleting from file");
            }
        }
        return false;
    }

    @Override
    public List<Contact> findAllByUser(User user) {
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<Contact> contactData = new ArrayList<>();
            try {
                contactData = mapper.readValue(new File(fileDBPath+"contacts/contact_"+user.getLogin()+".json"), mapper.getTypeFactory().constructCollectionType(List.class, Contact.class));
            } catch (Exception e) {
                // user has no contacts, we need to create file through first time
                try {
                    mapper.writeValue(new File(fileDBPath + "contacts/contact_" + user.getLogin() + ".json"), new ArrayList<>());
                } catch (IOException e1) {
                    LOGGER.warn("Can`t create user contact file");
                    return null;
                }
            }
            if (contactData.size() >= 0){
                return contactData;
            }
        }
        return null;
    }


    private boolean isContactFormValid(Contact contact) {
        boolean result = false;
        if (contact.getLastName().isEmpty() || contact.getLastName().length() < 4 ||
                contact.getFirstName().isEmpty() || contact.getFirstName().length() < 4 ||
                contact.getForeName().isEmpty() || contact.getForeName().length() < 4 ||
                contact.getMobilePhone().isEmpty()){
            return result;
        }
        String phonePattern = "^\\+[3]{1}[8]{1}[0]{1}[0-9]{9,9}$";
        java.util.regex.Pattern p1 = java.util.regex.Pattern.compile(phonePattern);
        java.util.regex.Matcher m1 = p1.matcher(contact.getMobilePhone());
        result = m1.matches();
        if (!result){
            return result;
        }
        if (!contact.getHomePhone().isEmpty()){
            String homePattern="\\d{5,15}";
            java.util.regex.Pattern p3 = java.util.regex.Pattern.compile(homePattern);
            java.util.regex.Matcher m3 = p3.matcher(contact.getHomePhone());
            result = m3.matches();
            if (!result){
                return result;
            }
        }
        if (!contact.getEmail().isEmpty()){
            String emailPattern = "^([a-zA-Z0-9_.-]+)@([a-z]+)(\\.)([a-z]{1,10})(\\.){0,1}([a-z]{0,3})$";
            java.util.regex.Pattern p2 = java.util.regex.Pattern.compile(emailPattern);
            java.util.regex.Matcher m2 = p2.matcher(contact.getEmail());
            result = m2.matches();
        }
        return result;
    }

    private Long getCurrentContactFileId(String userLogin){
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<User> contactData = new ArrayList<>();
            try {
                contactData = mapper.readValue(new File(fileDBPath+"contacts/contact_"+userLogin+".json"), mapper.getTypeFactory().constructCollectionType(List.class, Contact.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (contactData.size() == 0){
                return 0L;
            }
            return Long.valueOf(contactData.size()+1);
        }
        return 0L;
    }
}
