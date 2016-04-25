package com.app.dto;

import com.app.entity.Contact;
import com.app.entity.User;
import com.app.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String foreName;
    private List<Contact> contacts;
    private UserRole role;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getForeName() {
        return foreName;
    }
    public void setForeName(String foreName) {
        this.foreName = foreName;
    }
    public List<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> roles = new ArrayList<>();
        roles.add(this.getRole());
        return roles;
    }
    public User toUser(){
        User u = new User();
        u.setLogin(this.getLogin());
        u.setPassword(this.getPassword());
        u.setId(this.getId());
        u.setFirstName(this.getFirstName());
        u.setLastName(this.getLastName());
        u.setForeName(this.getForeName());
        u.setRole(this.getRole());
        u.setContacts(this.getContacts());
        return u;
    }
}
