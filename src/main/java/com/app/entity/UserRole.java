package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Entity
@Table(name = "user_roles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRole implements GrantedAuthority,Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32, nullable=false)
    private String name;

    public UserRole() {
        super();
    }

    public UserRole(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name;
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserRole))
            return false;
        UserRole role = (UserRole)obj;
        return role.getName().equals(getName());
    }
}
