package com.openclassrooms.controller;

import java.security.Principal;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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
	/**
	 * une variable finale privée pour votre classe LoginController de type OAuth2AuthenticationToken, 
	 * et intitulez-la authClientService. Elle permettra de stocker le token de façon sécurisée et immuable : 
	 */
	private final OAuth2AuthorizedClientService authorizedClientService;
	
	public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
		super();
		this.authorizedClientService = authorizedClientService;
	}

	/*
	 * une méthode différente pour chaque rôle, en utilisant les classes @RolesAllowed et @RequestMapping pour associer l’URL au rôle.
	 */
	@RequestMapping("/**")
	@RolesAllowed("USER")
	public String getUser(Principal user) {
		return "Welcome, User : " + user.getName();
	}
	
	@RequestMapping("/admin")
	@RolesAllowed("ADMIN")
	public String getAdmin(Principal principal) {
		return "Welcome, Admin : " + principal.getName();
	}
	/*
	 * Pour utiliser notre application web client avec une connexion GitHub, 
	 * vous aurez besoin de vous enregistrer sur GitHub pour obtenir une identité client et un client secret. 
	 * Ces deux appellations font référence aux traditionnels nom d’utilisateur et mot de passe pour votre app web;
	 * c'est ce qui vous permet de vous connecter au serveur d’autorisation de GitHub avec OAuth 2.0.
	 * GitHub(ou les autres ) génère dynamiquement une URL de redirection qui correspond à votre identité client. 
	 * getUserInfo) un nom générique s’applique à plusieurs fournisseurs d'identité
	 * Le Principal reste votre unique paramètre, contenant les informations de l’utilisateur, 
	 * envoyé par les serveurs d’autorisation. 
	 * 
	 * 
	 * ***
	 * Il va vous falloir trouver un moyen de mettre les attributs des utilisateurs en mémoire
	 *  et ensuite les récupérer. Il existe deux manières de travailler avec 
	 *  le contenu d’une chaîne de caractères : à l’aide d’un objet HashMap ou 
	 *  d’un objet StringBuffer. 
	 *  
	 *  *** 
	 *  Créons une méthode getter pour accéder aux informations protégées de l'utilisateur, 
	 *   situées dans le token d’accès. C’est le moment d’ajouter votre nom intégral, 
	 *   votre adresse mail et les informations de votre token d’accès à votre StringBuffer 
	 *   dans la méthode getUserInfo().
	 *   
	 *   *** 
	 *  Isolez les informations souhaitées dans un StringBuffer.
		Pour cela, il faut les extraire de l’objet Principal user avec UsernamePasswordAuthenticationToken ou OAuth2AuthenticationToken.
		Puis ajoutez ces informations dans le StringBuffer pour qu'il soit retourné par notre méthode getUserInfo().

	 *  
	 */
	@RequestMapping("/*")
	public String getUserInfo(Principal user) {
		StringBuffer userInfo = new StringBuffer();
		if(user instanceof UsernamePasswordAuthenticationToken) {
			userInfo.append(getUsernamePasswordLoginInfo(user));
		}
		else if(user instanceof OAuth2AuthenticationToken) {
			userInfo.append(getOAuth2LoginInfo(user));
		}
		
		return userInfo.toString();
	}
	/**
	 *  ***
	 *   La classe UsernamePasswordAuthenticationToken se chargera de récupérer 
	 *   le nom de l’utilisateur, après avoir authentifié le token en utilisant
	 *   la méthode getPrincipal().
	 * @param user
	 * @return
	 */
	
	private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
		// cette information sera ajoutée à l’instance du StringBuffer nommée usernameInfo
		StringBuffer usernameInfo = new StringBuffer();
		
		UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken)user);
		if(token.isAuthenticated()) {
			User u = (User) token.getPrincipal();
			usernameInfo.append("Welcome, " + u.getUsername() + ", you are successfully authenticated.");
		}
		else {
			usernameInfo.append("NA");
		}
		
		return usernameInfo;
	}
	/**
	 * La classe OAuth2AuthenticationToken contient des méthodes à utiliser pour des ressources protégées, 
	 * comme celles contenues dans l’objet user. 
	 * Créez un token intitulé authToken de type OAuth2AuthenticationToken et castez l’objet Principal user en ce nouveau type. 
	 * 
	 * ***
	 * Grâce à la classe OAuth2AuthenticationToken, l’application client a la permission d’accéder
	 * à davantage de ressources protégées, comme le token d‘accès. 
	 * @param user
	 * @return
	 */
	
	private StringBuffer getOAuth2LoginInfo(Principal user) {
		StringBuffer protectedInfo = new StringBuffer();
		
		OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
		/*
		 * Instanciez votre objet OAuth2AuthorizedClient. Vous remarquerez que 
		 * la méthode loadAuthorizedClient retourne le client qui correspond à l’ID et au nom du principal transmis en paramètre.
		 */
		OAuth2AuthorizedClient authClient = 
				this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
		
		if(authToken.isAuthenticated()) {
			/*
			 * affichez les informations de l’utilisateur lors de sa connexion. 
			 * Le nom et le mail sont des attributs qui sont extraits du principal. Vous obtenez le token d’accès.
			 * Utilisez la variable HashMap userAttributes pour récupérer l'ensemble des attributs.
			 */
			Map<String, Object> userAttributes= ((DefaultOAuth2User)authToken.getPrincipal()).getAttributes();
			/*
			 * La variable userToken se voit attribuer la valeur du token d’accès. 
			 * Elle contiendra donc la chaîne de caractères encodés du token d’accès
			 */
			String userToken = authClient.getAccessToken().getTokenValue();
			
			protectedInfo.append("Welcome, " + userAttributes.get("name") + "<br><br>");
			protectedInfo.append("email: "+ userAttributes.get("email") + "<br><br>");
			protectedInfo.append("Access Token: " + userToken + "<br><br>");
		}
		else {
			protectedInfo.append("NA");
		}
		
		return protectedInfo;
		
	}
	


}
