package com.app.service.impl;

import com.app.dao.UserRoleDao;
import com.app.entity.UserRole;
import com.app.service.UserRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService{
    private final transient Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRoleDao userRoleDao;

    @Value("${use.file.database}")
    private boolean useFileDB;
    @Value("${use.file.database.path}")
    public String fileDBPath;

    public UserRole find(Long id) {
        return userRoleDao.find(id);
    }
    @Transactional
    public void add(UserRole userRole) {
        userRoleDao.add(userRole);
    }
    @Transactional
    public UserRole update(UserRole userRole) {
        return userRoleDao.update(userRole);
    }
    @Transactional
    public void remove(Long id) {
        UserRole userRole = userRoleDao.find(id);
        userRoleDao.remove(userRole);
    }
    public List<UserRole> list() {
        return userRoleDao.list();
    }
    @Override
    public UserRole findByName(String name) {
        UserRole role = null;
        if (useFileDB){
            ObjectMapper mapper = new ObjectMapper();
            try {
                role = mapper.readValue(new File(fileDBPath+"userRole.json"), UserRole.class);
            } catch (IOException e) {
                LOGGER.warn("No File was Found " + e);
                return null;
            }
            if (role.getName().equals(name)){
                return role;
            }
            return null;
        }
        role = userRoleDao.findByName(name);
        if(role == null) {
            LOGGER.warn("UserRole " + name + " not found");
            return null;
        }
        return role;
    }
}
