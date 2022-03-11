package com.appraisal.entities;

import com.appraisal.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;

    private Role userRole = Role.EMPLOYEE;
}