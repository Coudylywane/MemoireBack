//package com.example.construction.fixtures;
//
//
//import com.example.construction.models.Role;
//import com.example.construction.models.Utilisateur;
//import com.example.construction.services.UtilisateurService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class UtilisateurFixture {
//    @Autowired
//    private UtilisateurService utilisateurService;
//    @Autowired
//    private PasswordEncoder bCryptPasswordEncoder;
//
//    public void addDefaultRoles() {
//        List<Role> rList = utilisateurService.getAllRoles();
//        if (rList == null || rList.size() <= 0 ) {
//            Role[] roles = {
//                new Role(null, "SUPER_ADMIN"),
//                new Role(null, "ADMIN"),
//            };
//            System.out.println(utilisateurService.addAllRole(Arrays.asList(roles)));
//        }
//    }
//
//    public void addDefaultSuperAdmin() {
//        Role role = utilisateurService.getRoleByLibelle("SUPER_ADMIN");
//        List<Utilisateur> users = utilisateurService.getUsersByRole("SUPER_ADMIN");
//        if (role != null && users.size() <= 0) {
//            Utilisateur defaultUser = new Utilisateur(
//                null, "Admin", "Admin", "774315331", "admin@gmail.com",
//                    bCryptPasswordEncoder.encode("admin@passer"),false,true,role
//            );
//            utilisateurService.addUser(defaultUser);
//            System.out.println("default admin added successfully");
//        }
//    }

//
//}
package com.example.construction.fixtures;


import com.example.construction.models.Role;
import com.example.construction.models.Utilisateur;
import com.example.construction.services.UtilisateurService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UtilisateurFixture {

    private final UtilisateurService utilisateurService;

    private final PasswordEncoder bCryptPasswordEncoder;

    public void addDefaultRoles() {
        List<Role> rList = utilisateurService.getAllRoles();
        if (rList == null || rList.size() <= 0 ) {
            Role[] profils = {
                    new Role(null, "SUPER_ADMIN"),
                    new Role(null, "ADMIN"),
                    new Role(null, "S"),
                    new Role(null, "CP"),
                    new Role(null, "GS")
            };
            utilisateurService.addAllRole(Arrays.asList(profils));
            System.out.println(utilisateurService.addAllRole(Arrays.asList(profils)));
        }
    }

    public void addDefaultSuperAdmin() {
        Role role = utilisateurService.getRoleByLibelle("SUPER_ADMIN");
        List<Utilisateur> users = utilisateurService.getUsersByRole("SUPER_ADMIN");
        if (role != null && users.size() <= 0) {
            Utilisateur defaultUser = new Utilisateur(
                null, "Admin", "Admin", "774315331", "admin@gmail.com",
                    bCryptPasswordEncoder.encode("admin@passer"),false,true,null
            );
            utilisateurService.addUser(defaultUser);  // Ajouter l'utilisateur à la base de données
            System.out.println("default admin added successfully");
        }
    }
}
