package com.company.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private String postcode;
}
