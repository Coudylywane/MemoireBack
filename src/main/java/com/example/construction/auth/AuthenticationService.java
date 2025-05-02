package com.example.construction.auth;


import com.example.construction.config1.JwtService;
import com.example.construction.models.Role;
import com.example.construction.models.Utilisateur;
import com.example.construction.repositories.RoleRepository;
import com.example.construction.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
  //  private JwtUser JwtUser;

    public AuthenticationResponse register(RegisterRequest registerRequest) {


        var user = Utilisateur.builder()
                .prenom(registerRequest.getPrenom())
                .nom(registerRequest.getNom())
                .login(registerRequest.getLogin())
                .telephoneString(registerRequest.getTelephoneString())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .status(true)
                .role(registerRequest.getRole())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword()
                )
        );
        //System.out.println(authenticationRequest.getLogin());
        var user = repository.findByLogin(authenticationRequest.getLogin())
                .orElseThrow();
        //System.out.println(((UserDetails) user).getUsername());
        var jwtToken = jwtService.generateToken((Utilisateur) user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
