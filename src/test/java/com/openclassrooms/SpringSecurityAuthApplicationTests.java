package com.openclassrooms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * En quoi consiste contextLoads()?
 * Chaque fois que vous ouvrez votre application web Spring Boot, vous chargez une instance de WebApplicationContext, 
 * qui étend votre ApplicationContext. Il s’agit d’une librairie qui prépare vos servlets à créer une instance d’application web.
 * L’ApplicationContext lance votre application Spring Boot depuis votre méthode main()dans votre fichier
 * SpringSecurityApplication.java . Un test tel que @WebMvcTest injecte des dépendances pour la couche web, 
 * et charge la WebApplicationContext. @SpringBootTest intègre votre application avec la couche web, 
 * afin que la WebApplicationContext et la ApplicationContext soient chargées. 
 * 
 * Il est important d’avoir ces informations, car si votre ApplicationContext ne charge pas,
 * votre application web ne s’ouvrira pas du tout.

 * @author Subhi
 *
 */
@SpringBootTest
class SpringSecurityAuthApplicationTests {

	@Test
	void contextLoads() {
	}

}
