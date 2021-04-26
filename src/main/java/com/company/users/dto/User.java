package com.company.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String title;
    private String firstname;
    private String lastname;
    private String gender;
    private Long empid;
    private Address address;
}
