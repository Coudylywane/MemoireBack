package com.example.construction.dto;

import com.example.construction.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private boolean status;
    private String email;
    private String login;
    private String nom;
    private String prenom;
    private String telephoneString;
    private boolean archive;
    private Role role;




}