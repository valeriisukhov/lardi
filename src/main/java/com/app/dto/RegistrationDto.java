package com.app.dto;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public class RegistrationDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String foreName;

    public RegistrationDto() {
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
}
