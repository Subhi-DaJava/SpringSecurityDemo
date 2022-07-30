package com.openclassrooms.controller;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * L’annotation @RestController pour la prise en compte de votre classe en tant que contrôleur REST par Spring
 * 
 * Mettre en place votre contrôleur REST, qui se chargera de créer vos pages d’accueil. Pour garantir son fonctionnement, 
 * assurez-vous de disposer d’une version à jour de Maven, et d’avoir défini le Spring Boot Maven plugin en tant que dépendance. 
 * @author Subhi
 *
 */
@RestController
public class LoginController {
	/*
	 * une méthode différente pour chaque rôle, en utilisant les classes @RolesAllowed et @RequestMapping pour associer l’URL au rôle.
	 */
	@RequestMapping("/*")
	@RolesAllowed("USER")
	public String getUser(Principal user) {
		return "Welcome, User : " + user.getName();
	}
	
	@RequestMapping("/admin")
	@RolesAllowed("ADMIN")
	public String getAdmin(Principal principal) {
		return "Welcome, Admin : " + principal.getName();
	}

}
