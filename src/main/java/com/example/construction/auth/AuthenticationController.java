package com.example.construction.auth;


import com.example.construction.dto.UserDto;
import com.example.construction.models.Utilisateur;
import com.example.construction.services.UtilisateurService;
import com.example.construction.services.Utilitaire;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private Utilitaire utilitaire;
    private final ObjectMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/connected-user")
    public ResponseEntity<UserDto> connectedUser() {
        try {
            Utilisateur utilisateur = utilisateurService.connectedUser();
            UserDto userDto = mapper.convertValue(utilisateur, UserDto.class);
            return ResponseEntity.ok(userDto);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
