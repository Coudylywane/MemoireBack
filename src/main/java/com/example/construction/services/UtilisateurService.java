//package com.example.construction.services;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.construction.models.Utilisateur;
//import com.example.construction.repositories.UtilisateurRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.example.construction.models.Role;
//import com.example.construction.repositories.RoleRepository;
//import lombok.extern.java.Log;
//
//
//
//@Service
//@Log
//public class UtilisateurService {
//    private String[] ADMIN_ROLES = {"ADMIN", "SUPER_ADMIN"};
//    @Autowired
//    private  UtilisateurRepository utilisateurRepository;
//    @Autowired
//    private  RoleRepository roleRepository;
//    private BCryptPasswordEncoder encoder;
//
//
//    public UtilisateurService() {
//    }
//
//    public Utilisateur connectedUser() {
//        try {
//            SecurityContext context = SecurityContextHolder.getContext();
//            Authentication authentication = context.getAuthentication();
//            String login = "";
//            if (authentication.getPrincipal() instanceof UserDetails) {
//                UserDetails user = (UserDetails) authentication.getPrincipal();
//                login = user.getUsername();
//            }
//            if (authentication.getPrincipal() instanceof String)
//                login = (String) authentication.getPrincipal();
//            return utilisateurRepository
//                    .findByLogin(login)
//                    .orElse(null);
//        } catch (Exception e) {
//            log.severe(e.getLocalizedMessage());
//            throw e;
//        }
//    }
//
//    public Role addRole(Role role) {
//        try {
//            roleRepository.save(role);
//            return role;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public Role getRoleByLibelle(String libelle) {
//        try {
//            return roleRepository.findByLibelle(libelle);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public boolean addAllRole(List<Role> roles) {
//        try {
//            roleRepository.saveAll(roles);
//            return true;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public List<Utilisateur> getUsersByRole(String role) {
//        return utilisateurRepository.findByRole(role).orElse(new ArrayList<>());
//    }
//
//    public Utilisateur addUser(Utilisateur user) {
//        try {
//            utilisateurRepository.save(user);
//            return user;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public boolean hasRoles(String[] roles) {
//        Utilisateur conectedUser = connectedUser();
//        for (String role : roles) {
//            if (role.equals(conectedUser.getRole().getLibelle())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public Page<Utilisateur> getAllUserNotArchive(Pageable pageable) {
//        if (this.hasRoles(ADMIN_ROLES)) {
//            return utilisateurRepository.findByAllPeagable(pageable);
//        } else {
//            throw new AccessDeniedException("You don't have permission to access this resource");
//        }
//    }
//
//
//  public List<Role> getAllRoles() {
//    return roleRepository.findAll();
//  }
//}
package com.example.construction.services;

import com.example.construction.config1.UserPrincipal;
import com.example.construction.models.Article;
import com.example.construction.models.Role;
import com.example.construction.models.Utilisateur;
import com.example.construction.repositories.RoleRepository;
import com.example.construction.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log
@RequiredArgsConstructor
public class UtilisateurService {
    public String[] ADMIN_ROLES = { "ADMIN", "SUPER_ADMIN" };

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder;

    public Utilisateur connectedUser() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            String login = "";

            if (authentication.getPrincipal() instanceof UserPrincipal) {
                Utilisateur user = (Utilisateur) authentication.getPrincipal();
                login = user.getLogin();


            }
            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                login = userDetails.getUsername();

            }
            if (authentication.getPrincipal() instanceof String username) {
                login = username;

            }
            log.info("login connected user : " + login);
            return utilisateurRepository
                    .findByLogin(login)
                    .orElse(null);
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            throw e;
        }
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public boolean hasRoles(String[] profils) {
        Utilisateur conectedUser = connectedUser();
        for (String role : profils) {
            if (role.equals(conectedUser.getRole().getLibelle())) {
                return true;
            }
        }
        return false;
    }

    public boolean addAllRole(List<Role> profils) {
        try {
            roleRepository.saveAll(profils);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public Role getRoleByLibelle(String libelle) {
        try {
            return roleRepository.findByLibelle(libelle);
        } catch (Exception e) {
            return null;
        }
    }

    public Utilisateur addUser(Utilisateur user) {
        try {
            utilisateurRepository.save(user);
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Utilisateur> getUsersByRole(String profil) {
        return utilisateurRepository.findByRole(profil).orElse(new ArrayList<>());
    }

        public Role addRole(Role role) {
        try {
            roleRepository.save(role);
            return role;
        } catch (Exception e) {
            throw e;
        }
    }

    public Page<Utilisateur> getUtilisateurPage(Pageable pageable){
        return utilisateurRepository.findByAllPeagable(pageable);
    }
}

