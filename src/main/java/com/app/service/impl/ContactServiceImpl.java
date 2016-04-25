package com.app.service.impl;

import com.app.dao.ContactDao;
import com.app.entity.Contact;
import com.app.entity.User;
import com.app.service.ContactService;
import com.app.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService, EntityService<Contact> {
    @Autowired
    private ContactDao contactDao;

    public Contact find(Long id) {
        return contactDao.find(id);
    }

    @Override
    public Contact findWithLogin(Long id, String login) {
        return contactDao.find(id);
    }
    @Transactional
    public void add(Contact contact) {
        contactDao.update(contact);
    }
    @Transactional
    public Contact update(Contact contact) {
        if (!isContactFormValid(contact)){
            return null;
        }
        Contact result = contactDao.update(contact);
        return result;
    }
    @Transactional
     public void remove(Long id) {
        Contact user = contactDao.find(id);
        contactDao.remove(user);
    }
    @Transactional
    public void removeWithLogin(Long id, String login) {
        Contact user = contactDao.find(id);
        contactDao.remove(user);
    }
    public List<Contact> list() {
        return contactDao.list();
    }

    @Override
    public List<Contact> findAllByUser(User user) {
        return contactDao.findAllByUser(user);
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
}
