package com.app.entity;

import com.app.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 64, nullable=false, unique = true)
    private String login;
    @Column(length = 64, nullable=false)
    private String password;
    @Column(name="first_name", length = 128, nullable=false)
    private String firstName;
    @Column(name="last_name", length = 128, nullable=false)
    private String lastName;
    @Column(name="fore_name", length = 128, nullable=false)
    private String foreName;
    @Column(nullable = false)
    private UserRole role;
    @OneToMany(mappedBy = "user",
               orphanRemoval = true,
               fetch = FetchType.EAGER)
    private List<Contact> contacts;

    public User() {
        super();
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
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public List<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    public static UserDto toUserDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setForeName(user.getForeName());
        dto.setContacts(user.getContacts());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!foreName.equals(user.foreName)) return false;
        if (!role.equals(user.role)) return false;
        return !(contacts != null ? !contacts.equals(user.contacts) : user.contacts != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + foreName.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", foreName='" + foreName + '\'' +
                '}';
    }
}
