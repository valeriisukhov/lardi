package com.app.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Entity
@Table(name = "contacts")
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name", length = 128, nullable=false)
    private String firstName;
    @Column(name="last_name", length = 128, nullable=false)
    private String lastName;
    @Column(name="fore_name", length = 128, nullable=false)
    private String foreName;
    @Column(name="mobile_phone", length = 128, nullable=false)
    private String mobilePhone;
    @Column(name="home_phone", length = 128, nullable=true)
    private String homePhone;
    @Column(name="address", length = 128, nullable=true)
    private String address;
    @Column(name="email", length = 128, nullable=true)
    private String email;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Contact() {
        super();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!id.equals(contact.id)) return false;
        if (!firstName.equals(contact.firstName)) return false;
        if (!lastName.equals(contact.lastName)) return false;
        if (!foreName.equals(contact.foreName)) return false;
        if (!mobilePhone.equals(contact.mobilePhone)) return false;
        if (homePhone != null ? !homePhone.equals(contact.homePhone) : contact.homePhone != null) return false;
        if (address != null ? !address.equals(contact.address) : contact.address != null) return false;
        if (email != null ? !email.equals(contact.email) : contact.email != null) return false;
        return !(user != null ? !user.equals(contact.user) : contact.user != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + foreName.hashCode();
        result = 31 * result + mobilePhone.hashCode();
        result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", foreName='" + foreName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
