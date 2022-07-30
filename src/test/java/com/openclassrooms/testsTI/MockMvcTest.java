package com.openclassrooms.testsTI;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Nous allons utiliser un objet MockMVC. Ce dernier crée une fausse version de votre application web, et lance les méthodes qu’il comprend, afin que la fonctionnalité de votre app ne soit pas interrompue. Il utilise les éléments de SpringBootTest et JUnit 5, mais vous permet de créer une instance de l’intégralité de votre configuration d’application web dédiée au test.
 * Commencez par ajouter les annotations pour@ExtendWith(SpringExtension.class) et @AutoconfigureMockMvc .
 * Pour cela, il est préférable de se servir de SpringBootTest , car il s’agit 
 * d’un test d’intégration de l’application web au serveur web. N’oubliez pas d’injecter les dépendances du MockMvc. 
 *  ****
 *   * ***
 * Prenons l’exemple d’un test qui utilise vos identifiants de connexion. 
 * Vous pouvez avoir recours à ce type de test avec le formLogin()par défaut, 
 * si des utilisateurs sont connectés à la base de données. Vous pouvez créer un faux compte destiné aux tests,
 * en pensant à le supprimer lorsque vous aurez terminé, pour des raisons de sécurité.
 * 
 * ***
 * Testez votre application et la couche web avec SpringBootTest. 
 * Utilisez MockMvc pour créer une copie de votre application web destinée aux tests.
 * Testez votre authentification en ayant recours à l'objet MockMvc.

 * @author Subhi
 *
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class MockMvcTest {
	@Autowired
	private MockMvc mvc;
	
	/*
	 * il est préférable de se servir de SpringBootTest , car il s’agit d’un test d’intégration de
	 *  l’application web au serveur web. N’oubliez pas d’injecter les dépendances du MockMvc. 
	 *  ***
	 *  Ensuite, mettez en place la mention @BeforeEach pour construire la fausse copie avant de lancer les tests.
	 * Il faut que la copie de votre application web soit créée avant que vous procédiez aux méthodes annotées @Test.
	 * Nous utilisons la classe MockMvcBuilders pour évaluer les codes de réponse sur votre app test MVC. 
	 */
	@Autowired
	private WebApplicationContext context;
	
	/*
	 *  Nous utilisons la classe MockMvcBuilders pour évaluer les codes de réponse sur votre app test MVC.
	 *  La méthode est intitulée setup(), et utilise webAppContextSetup()pour ajouter la couche web,  
	 *  apply(springSecurity())pour ensuite ajouter la chaîne de filtres de sécurité de Spring Security ainsi que la méthode build(),
	 *  et créer une nouvelle instance de type MockMvc. En bref, cette procédure vous permettra de
	 *  créer une application web destinée à effectuer des tests. 
	 *  ***
	 *  Comme vous l’aurez déduit, la dépendance de chaîne de filtres de sécurité est injectée dans cette instance,
	 *   d’où l’ajout de la série de règles de sécurité pour Spring Security, pour l’instance MVC.
	 */
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
				
	}
	
	/*
	 * Ces tests ont recours aux méthodes perform(),  andDo()et andExpect().
	 * Elles disposent toutes de codes de réponse qui seront intégrés à MockMvcBuilders pour évaluer s’il s’agit d’une réussite,
	 *  d’une erreur ou d’un échec. 
	 * La méthode perform()crée une requête GET en dehors de la méthode de test. 
	 * La classe ResultActions contient le andDo()pour exécuter une action générale, et le andExpect(), 
	 * pour vérifier un certain résultat. La classe SecurityMockMvcResultMatchers vérifie les méthodes de ResultActions
	 *  avec les méthodes authenticated()et unauthenticated().

	 */
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		mvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
	}
	
	/*
	 * Testez l’authentification en ajoutant les identifiants corrects grâce à la méthode authenticated().
	 */
	@Test
	public void userLoginTest() throws Exception {
		mvc.perform(formLogin("/login").user("springuser").password("user123")).andExpect(authenticated());
	}
	
	/*
	 * Faites un test pour vous assurer que des identifiants incorrects ne donnent pas lieu à l’authentification 
	 * de l’utilisateur, avec la méthode unauthenticated(). 
	 */
	@Test
	public void userLoginFailed() throws Exception {
		mvc.perform(formLogin("/login").user("springuser").password("wrongpassword")).andExpect(unauthenticated());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
