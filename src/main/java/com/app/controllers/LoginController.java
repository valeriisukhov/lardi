package com.app.controllers;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.service.UserFileService;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private final transient Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Value("${use.file.database}")
    private boolean useFileDB;
    @Autowired
    private UserService userService;
    @Autowired
    private UserFileService userFileService;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request,@RequestParam(required = false) String username,
                            @RequestParam(required = false) String password, Principal principal) {
        if (principal != null){
            return "redirect:/index";
        }
        if (username != null && password != null){
            User u = null;
            if (useFileDB){
                u = userFileService.findByLogin(username);
            } else {
                u = userService.findByLogin(username);
            }
            if (u == null) {
                return "login";
            }
            UserDto dto = User.toUserDto(u);
            if (dto != null && new ShaPasswordEncoder().encodePassword(password, null).equals(dto.getPassword())){
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(dto, dto.getPassword(), dto.getAuthorities());
                authentication.setDetails(request.getRemoteAddr());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "redirect:/index";
            }
        }
        return "login";
    }
}
