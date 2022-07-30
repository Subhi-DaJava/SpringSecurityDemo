package com.openclassrooms.controller;
import com.openclassrooms.controller.LoginController;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * D'abord, ajoutez @ExtendWith(SpringExtension.class).
 *  La notation @ExtendWith s’ajoute à l'architecture extensive de JUnit 5, 
 *  pour que vous puissiez vous en servir dans le cadre de vos tests. Parmi eux se trouve la classe SpringExtension, 
 *  qui prévoit le contexte du test. En résumé, elle ajoute les dépendances à vos tests, vous évitant ainsi de vous en charger. 
 * 
 * @author Subhi
 *
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginControllerTest {
	// une instance du LoginController.
	// Autowird ajoutera implicitement l’injection de dépendance pour vous assurer une configuration lisse, 
	//sans qu'il faille l'ajouter manuellement pour la classe LoginController.
	@Autowired 
	private LoginController login;
	
	@Test
	public void ControllerNotNullTest() {
		//l’instance de votre contrôleur ne doit pas être nulle lorsque vous le lancez.
		assertThat(login).isNotNull();
	}
}
