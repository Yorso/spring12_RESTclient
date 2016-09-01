package com.jorge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.jorge.model.User;

/**
 * Using the Java RMI, HTTP Invoker, Hessian, and REST
 * 
 * 		HTTP Invoker to interact with another Spring application
 * 		Java RMI to interact with another Java application not using Spring
 * 		Hessian to interact with another Java application not using Spring when you need to go over	proxies and firewalls
 * 		SOAP if you have to
 * 		REST for all other cases. REST is currently the most popular option; it's simple, flexible, and cross-platform
 *
 */


/**
 * To secure a REST service:
 * 		Use HTTPS so that data transfers between the client and server are encrypted. Refer to the
 * 		Using HTTPS with Tomcat, Managing Security.
 * 	
 * 		If you want only authorized clients to query it, you can use HTTP Basic Authentication. Refer to
 * 		the Authenticating users using the default login page, Managing Security, especially, the httpBasic() method. 
 * 
 * 		Another possibility is to use an OAuth workflow. It's more complicated, but it avoids the client having to send 
 * 		a username and password at each request. That's the method chosen by Facebook and Twitter for their REST API, for example.
 *  
 */


/**
 * UserController is a standard Spring controller except for the @RestController annotation, which
 * will automatically convert the objects returned by the controller methods to JSON, using the Jackson
 * library
 *
 */

@Controller
public class UserController {
	
	/**************************************************************
	 * CLIENT SIDE
	 * 
	 * Try it: 
	 * 		Run spring13_REASTserver
	 * 		Run spring13_RESTclient
	 * 		Write in browser: http://localhost:8080/spring13_RESTclient/user/1
	 * 
	 * 
	 * Use the RestTemplate class and its getForObject() method to query the REST service and
	 * generate a User object from the JSON response
	 * 
	 * RestTemplate is a class provided by Spring that provides methods to easily query REST services
	 * and generate Java objects from the received JSON response.
	 * 
	 * If the response is a list of objects, pass an array class as a second parameter to generate an array of objects:
	 * 		User[] userList = restTemplate.getForObject(url, User[].class);
	 * 
	 */
	@RequestMapping("user/{id}") // We get an spcific user (i.e. id=2 in url string) from server: http://localhost:8080/spring13_RESTclient/user/2
	@ResponseBody
	// CLIENT SIDE.
	public String user(HttpServletRequest request, @PathVariable("id") Long userId) {
		String url = "http://localhost:8080/spring13_RESTserver/users/" + userId; // URL of the REST service to query in server
		
		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForObject(url, User.class);
		
		System.out.println("Name: " + user.getName());
		System.out.println("Age: " + user.getAge() + "\n");
		
		return "Name: <b>" + user.getName() + "</b><br/>" + "Age: <b>" + user.getAge() + "</b><br/><br/>"; // Result displaying in browser
	}
	
	// Getting usr list
	@RequestMapping("userList")
	@ResponseBody
	// CLIENT SIDE. We get the user list from server: http://localhost:8080/spring13_RESTclient/userList
	public String userList() {
		String url = "http://localhost:8080/spring13_RESTserver/users"; // URL of the REST service to query in server
		String res = "";
		
		RestTemplate restTemplate = new RestTemplate();
		User[] userList = restTemplate.getForObject(url, User[].class);
		for(User u : userList){
			System.out.println("Name: " + u.getName());
			System.out.println("Age: " + u.getAge() + "\n");
			
			res += "Name: <b>" + u.getName() + "</b><br/>" + "Age: <b>" + u.getAge() + "</b><br/><br/>"; 
		}
		return res; // Result displaying in browser
	}
	
}
