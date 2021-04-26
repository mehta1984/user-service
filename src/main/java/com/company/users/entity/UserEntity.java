package com.company.users.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    private @Id @GeneratedValue Long id;
    private String title;
    private String firstname;
    private String lastname;
    private String gender;
    private Long empid;
    private String street;
    private String city;
    private String state;
    private String postcode;

}
