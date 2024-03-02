package com.example.construction.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.construction.models.Role;
import com.example.construction.repositories.RoleRepository;


@Service
public class UtilisateurService {

    @Autowired
    RoleRepository roleRepository;
    public Role addRole(Role role) {
        try {
            roleRepository.save(role);
            return role;
        } catch (Exception e) {
            throw e;
        }
    }
  


  public List<Role> getAllRole() {
    return roleRepository.findAll();
  }
}
