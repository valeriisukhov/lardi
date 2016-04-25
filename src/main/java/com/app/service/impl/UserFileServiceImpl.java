package com.app.service.impl;

import com.app.entity.User;
import com.app.service.UserFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
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
@Service("userFileService")
public class UserFileServiceImpl implements UserFileService {

    private final transient Logger LOGGER = LoggerFactory.getLogger(UserFileServiceImpl.class);
    @Value("${use.file.database}")
    private boolean useFileDB;
    @Value("${use.file.database.path}")
    public String fileDBPath;

    @Override
    public User find(Long id) {
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<User> userData = new ArrayList<>();
            try {
                userData = mapper.readValue(new File(fileDBPath+"users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
            userData = userData.stream().filter(user -> user.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
            if (userData.size() > 0){
                return userData.get(0);
            }
        }
        return null;
    }
    @Transactional
    public User update(User user) {
        if (useFileDB){
            if (!isRegistrationFormValid(user)){
                return null;
            }
            user.setPassword(new ShaPasswordEncoder().encodePassword(user.getPassword(), null));
            ObjectMapper mapper = new ObjectMapper();
            List<User> userData = new ArrayList<>();
            try {
                userData = mapper.readValue(new File(fileDBPath+"users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException e) {
                LOGGER.warn("There is no DB initializing files");
                return null;
            }
            if (userData.size() > 0) {
                boolean updated = false;
                for (User u : userData) {
                    if (u.getId().equals(user.getId())) {
                        u.setFirstName(user.getFirstName());
                        u.setLastName(user.getLastName());
                        u.setForeName(user.getForeName());
                        u.setContacts(user.getContacts());
                        u.setLogin(user.getLogin());
                        u.setPassword(user.getPassword());
                        u.setRole(user.getRole());
                        updated = true;
                    }
                }
                if (!updated) {
                    user.setId(getCurrentUserFileId());
                    userData.add(user);
                }
            } else {
                user.setId(getCurrentUserFileId()+1L);
                userData.add(user);
            }
            try {
                mapper.writeValue(new File(fileDBPath+"users.json"), userData);
            } catch (IOException e) {
                LOGGER.warn("Cant write file in current directory");
                return null;
            }
            return user;
        }
        return null;
    }
    @Transactional
    public void remove(Long id) {
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<User> userData = new ArrayList<>();
            try {
                userData = mapper.readValue(new File(fileDBPath+"users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException e) {
                LOGGER.warn("There is no DB initializing files");
            }
            userData = userData.stream().filter(user -> !user.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
            try {
                mapper.writeValue(new File(fileDBPath+"users.json"), userData);
            } catch (IOException e) {
                LOGGER.warn("Cant write file in current directory");
            }
        }
    }

    @Override
    public User findByLogin(String login) {
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<User> userData = new ArrayList<>();
            try {
                userData = mapper.readValue(new File(fileDBPath+"users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
            userData = userData.stream().filter(user -> user.getLogin().equals(login)).collect(Collectors.toCollection(ArrayList::new));
            if (userData.size() > 0){
                return userData.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean isLoginUnique(String login) {
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<User> userData = new ArrayList<>();
            try {
                userData = mapper.readValue(new File(fileDBPath+"users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
            userData = userData.stream().filter(user -> user.getLogin().equals(login)).collect(Collectors.toCollection(ArrayList::new));
            if (userData.size() > 0){
                return false;
            }
        }
        return true;
    }

    private boolean isRegistrationFormValid(User user) {
        boolean result = false;
        if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty() && !user.getFirstName().isEmpty() &&
                !user.getLastName().isEmpty() && !user.getForeName().isEmpty()) {
            if (!isLoginUnique(user.getLogin())){
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

    private Long getCurrentUserFileId(){
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            List<User> userData = new ArrayList<>();
            try {
                userData = mapper.readValue(new File(fileDBPath+"users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (userData.size() == 0){
                return 0L;
            }
            return Long.valueOf(userData.size()+1);
        }
        return null;
    }
}
