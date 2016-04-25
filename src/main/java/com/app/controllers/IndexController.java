package com.app.controllers;

import com.app.dto.ContactDto;
import com.app.dto.StatusDto;
import com.app.dto.UserDto;
import com.app.entity.Contact;
import com.app.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Controller
public class IndexController {
    private final transient Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Value("${use.file.database}")
    private boolean useFileDB;
    @Autowired
    private UserService userService;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactFileService contactFileService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String home(Principal principal){
        if (principal != null){
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        UserDto dto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Contact> list = new ArrayList<>();
        if(useFileDB){
            list = contactFileService.findAllByUser(dto.toUser());
        } else {
            list = contactService.findAllByUser(dto.toUser());
        }
        if (list == null){
            list = new ArrayList<>();
        }
        for (Contact c : list){
            c.setUser(null);
        }
        String jsonInString = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonInString= mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Cant evaluate data for this user");
        }
        model.addAttribute("list", jsonInString);
        return "index";
    }

    @RequestMapping(value = "/saveContact", method = RequestMethod.POST)
    @ResponseBody
    public StatusDto saveContact(@RequestBody ContactDto dto){
        UserDto loggedInUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dto != null && loggedInUser != null){
            Contact contact = new Contact();
            contact.setLastName(dto.getLastName());
            contact.setFirstName(dto.getFirstName());
            contact.setForeName(dto.getForeName());
            contact.setMobilePhone(dto.getMobilePhone());
            contact.setHomePhone(dto.getHomePhone());
            contact.setAddress(dto.getAddress());
            contact.setEmail(dto.getEmail());
            if (useFileDB){
                contact.setUser(userFileService.findByLogin(loggedInUser.getLogin()));
                contact = contactFileService.update(contact);
            } else {
                contact.setUser(userService.findByLogin(loggedInUser.getLogin()));
                contact = contactService.update(contact);
            }
            if (contact != null){
                StatusDto res = new StatusDto("OK");
                res.setData(contact.getId());
                return res;
            }
        }
        return new StatusDto("ERROR");
    }
    @RequestMapping(value = "/removeContact", method = RequestMethod.POST)
    @ResponseBody
    public StatusDto removeContact(@RequestParam int id){
        UserDto loggedInUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id > 0 && loggedInUser != null){
            if (useFileDB){
                boolean res = contactFileService.removeWithLogin(Long.valueOf(id), loggedInUser.getLogin());
                if (res){
                    return new StatusDto("OK");
                }
            } else {
                try {
                    contactService.removeWithLogin(Long.valueOf(id), loggedInUser.getLogin());
                    return new StatusDto("OK");
                } catch (Exception e){
                    LOGGER.warn("Error during saving contact");
                }
            }
        };
        return new StatusDto("ERROR");
    }
    @RequestMapping(value = "/editContact", method = RequestMethod.POST)
    @ResponseBody
    public StatusDto editContact(@RequestParam int id, @RequestBody ContactDto dto){
        UserDto loggedInUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id > 0 && loggedInUser != null){
            Contact contact = null;
            if (useFileDB){
                contact = contactFileService.findWithLogin(Long.valueOf(id),loggedInUser.getLogin());
            } else {
                contact = contactService.findWithLogin(Long.valueOf(id),loggedInUser.getLogin());
            }
            if (contact == null){
                return new StatusDto("ERROR");
            }
            contact.setLastName(dto.getLastName());
            contact.setFirstName(dto.getFirstName());
            contact.setForeName(dto.getForeName());
            contact.setMobilePhone(dto.getMobilePhone());
            contact.setHomePhone(dto.getHomePhone());
            contact.setAddress(dto.getAddress());
            contact.setEmail(dto.getEmail());
            if (useFileDB){
                contact = contactFileService.update(contact);
            } else {
                contact = contactService.update(contact);
            }
            if (contact != null){
                return new StatusDto("OK");
            }
        };
        return new StatusDto("ERROR");
    }
}
