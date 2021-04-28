package com.company.users.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @TableGenerator(name = "User_Gen", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "User_Gen", initialValue = 10000, allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "User_Gen")
    //@Id @GeneratedValue
    private Long id;
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
