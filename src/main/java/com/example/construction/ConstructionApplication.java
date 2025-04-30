package com.example.construction;

import com.example.construction.fixtures.UtilisateurFixture;
import com.example.construction.services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Log
@RequiredArgsConstructor
@SpringBootApplication
public class 	ConstructionApplication extends SpringBootServletInitializer implements CommandLineRunner {

	private final UtilisateurFixture utilisateurFixture;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UtilisateurService utilisateurService;

	@Override
	public void run(String... args) throws Exception {
		// utilisateurFixture.addDefaultRoles();
		// utilisateurFixture.addDefaultSuperAdmin();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConstructionApplication.class, args);
	}

}


