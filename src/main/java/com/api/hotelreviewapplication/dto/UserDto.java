package com.api.hotelreviewapplication.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String rolename;
}
