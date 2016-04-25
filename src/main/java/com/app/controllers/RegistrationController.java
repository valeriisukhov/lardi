package com.app.controllers;

import com.app.dto.RegistrationDto;
import com.app.dto.StatusDto;
import com.app.entity.User;
import com.app.service.UserFileService;
import com.app.service.UserRoleService;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Controller
public class RegistrationController {
    private final transient Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Value("${use.file.database}")
    private boolean useFileDB;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserFileService userFileService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public StatusDto create(@RequestBody RegistrationDto dto) {
        if (dto != null){
            User u = new User();
            u.setLogin(dto.getLogin());
            u.setPassword(dto.getPassword());
            u.setFirstName(dto.getFirstName());
            u.setLastName(dto.getLastName());
            u.setForeName(dto.getForeName());
            u.setRole(userRoleService.findByName("ROLE_USER"));
            if (useFileDB){
               u = userFileService.update(u);
            } else {
                u = userService.update(u);
            }
            if (u != null){
                return new StatusDto("OK");
            }
        }
        return new StatusDto("ERROR");
    }
}
