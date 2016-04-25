package com.app.dto;

import com.app.entity.User;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String foreName;
    private String mobilePhone;
    private String homePhone;
    private String address;
    private String email;
    private User user;

    public ContactDto() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getHomePhone() {
        return homePhone;
    }
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
