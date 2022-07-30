package com.openclassrooms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Les annotations Configuration et EnableWebSecurity pour désigner 
 * la classe SpringSecurityConfig en tant que configuration de la chaîne de filtres de sécurité 
 * pour l’app Spring Boot, et pour la création de la chaîne. 
 * @author Subhi
 *
 */
/*
 * Etendre la classe SpringSecurityConfig avec WebSecurityConfigurerAdapter. 
 * Cela définit la classe SpringSecurityConfig en tant que configuration Spring Security. 
 */
/*
 * L'annotation EnableWebSecurity. Cela permettra de vous assurer que l’application web Spring sache importer 
 * votre configuration Spring Security sécurisée. 
 */
@Configuration
@EnableWebSecurity 
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	/**
	 * La méthode configure() avec la classe AuthenticationManagerBuilder 
	 * pour gérer la série de règles d’authentification. 
	 * 
	 * une chaîne de filtres de sécurité pour l'étape d’authentification,
	 * utiliser AuthenticationManagerBuilder.
	 * 
	 * Ce filtre permet non seulement de créer des identifiants encodés, mais également de les assigner à des rôles.
	 * 
	 * Si vous souhaitez que votre authentification soit réalisée en connexion avec une base de données, 
	 * vous pouvez mettre en place une authentification basée sur JDBC (Java Database Connectivity) de la même manière.
	 *  Au lieu de recourir à auth.inMemoryAuthentication, utilisez auth.jdbcAuthentication() et ajoutez une configuration de base de données. 
	 *  Voici quelques infos pratiques sur cette procédure. https://www.baeldung.com/spring-security-jdbc-authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication() // auth.inMemoryAuthentication, cela signifie que les identifiants créés seront stockés dans la mémoire,
									 //  plutôt que dans un jeton ou dans une base de données.
		.withUser("springuser").password(passwordEncoder().encode("user123")).roles("USER")
		.and()
		.withUser("springadmin").password(passwordEncoder().encode("admin123")).roles("ADMIN","USER");
		
	}
	/**
	 * La méthode configure() qui prend en paramètre un objet HTTPSecurity 
	 * pour faire passer toutes les requêtes HTTP à travers la chaîne de filtres de sécurité, 
	 * et configurez le formulaire de connexion par défaut avec la méthode form Login().
	   À présent, regardons en détail chacune de ces étapes.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()  //la méthode authorizeRequests() pour définir les rôles
		.antMatchers("/admin").hasRole("ADMIN") // la méthode antMatchers() pour définir l'association des rôles USER (utilisateur) et ADMIN (administrateur) avec des pages
		.antMatchers("/user").hasRole("USER")
		.anyRequest().authenticated() //anyRequest().authenticated() pour vous permettre d’utiliser le formulaire ci-dessous pour l’authentification.
		.and()
		.formLogin();
		
	}
	/**
	 * Cela va vous permettre de choisir le type d'algorithme de hachage (BCrypt) avec lequel vous souhaitez encoder votre mot de passe. 
	 * BCrypt, un des algorithmes d’encodage les plus reconnus en ce qui concerne les mots de passe.
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
