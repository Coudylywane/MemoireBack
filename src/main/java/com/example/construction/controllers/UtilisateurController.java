package com.example.construction.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.construction.models.Article;
import com.example.construction.models.Utilisateur;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.construction.models.Role;
import com.example.construction.services.UtilisateurService;

@RestController
@RequestMapping("/api")
public class UtilisateurController {
    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping("/role")
    public Role addRole(@RequestBody Role role) {
      return utilisateurService.addRole(role);
    
    }

    @GetMapping("/role")
    public ResponseEntity<?> getAllRole()
    {
        List<Role> role = utilisateurService.getAllRoles();
        return ResponseEntity.ok(role);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody Utilisateur user) throws IOException {
        try{
            return ResponseEntity.ok(utilisateurService.addUser(user));
        }catch (Exception e) {
            String errorMessage = "Erreur lors de l'ajout de l'utilisateur : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUtilisateurPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Utilisateur> userPage = utilisateurService.getUtilisateurPage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("user", userPage.getContent());
            response.put("currentPage", userPage.getNumber());
            response.put("totalItems", userPage.getTotalElements());
            response.put("totalPages", userPage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/user/role")
    public ResponseEntity<?> getUserByRole()
    {
        List<Utilisateur> user = utilisateurService.getUsersByRole("CLIENT");
        return ResponseEntity.ok(user);
    }


}
