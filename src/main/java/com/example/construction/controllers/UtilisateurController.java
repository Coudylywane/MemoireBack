package com.example.construction.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.construction.models.Role;
import com.example.construction.services.UtilisateurService;

@RestController
@RequestMapping("/api")
public class UtilisateurController {
    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping("role")
    public Role addRole(@RequestBody Role role) {
      return utilisateurService.addRole(role);
    
    }

    @GetMapping("role")
    public ResponseEntity<?> getAllRole()
    {
        List<Role> role = utilisateurService.getAllRole();
        return ResponseEntity.ok(role);
    }

}
