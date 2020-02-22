package com.api_gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("book")
public class adminBookServiceController {

	// creating a rest template inorder to communicate with the remaining service that are
	// independently running.
	@Autowired
	RestTemplate rt;

	//creating an instance of http header from spring boot
	HttpHeaders headers;

	//Creating a multivalued map to set as the parameter for the request body

	HttpEntity<MultiValueMap<String, String>> request;


	//getting the value from properties file which is already defined
	@Value("${adminBookService.url}")
	private String url;

	@PostMapping("/addBook")
	public @ResponseBody HttpEntity<String> addBook(@RequestParam(value = "bName") String bName,
			@RequestParam(value = "authorName") String authorName){

		//updating the url wrt the service
		String updatedurl= url;
		System.out.println("URL: "+updatedurl);


		
		//Setting up the http header as to communicate with the service using http method.
		headers =  new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		//creating a multivalued map inorder to pass the parameters to the service.
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();

		map.add("title", bName);
		map.add("author", authorName);

		//creating the http entity so as to encapsule the header and body for giving it the service as 
		// a single entity
		request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		/*
		 * calling the service using the rest template post method
		 * getting back the response as a http entity.
		 * storing it in a variable called message
		 */
		ResponseEntity<String> message = rt.postForEntity( updatedurl, request , String.class );

		//returning to the user
		return message;

	}


}
