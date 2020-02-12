package com.api_gateway.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/*
 * This controller is exclusively to handle the requests corresponding to boroowing or returning a book
 * 
 * This controller also gets the list of users who had borrowed the book
 */


/*
 * Setting the class as a controller and the initail mapping as circulation
 */

@Controller
@RequestMapping(value="circulation")
public class circulationServiceController {
	
	
	// creating a rest template inorder to communicate with the remaining service that are
	// independently running.
	RestTemplate rt = new RestTemplate();
	
	//creating an instance of http header from spring boot
	HttpHeaders headers = new HttpHeaders();
	
	//Creating a multivalued map to set as the parameter for the request body
	MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
	
	HttpEntity<MultiValueMap<String, Integer>> request;

	
	//getting the value from properties file which is already defined
	@Value("${circulationService.url}")
	private String url;

	/*
	 * creating a rest endpoint for borrowing a book.
	 * calls the circulation service and passes the value from the user.
	 * gets back the response from the service and giving back to the user.
	 */
	
	@PostMapping("/borrow")
	public @ResponseBody ResponseEntity<String> borrowBook(@RequestParam(value = "uid") int uid,
			@RequestParam(value = "bookid") int bookid) {
		
		
		//updating the url wrt the service
		String updatedurl= url+"borrow";
		System.out.println("URL: "+updatedurl);
		
		
		//Setting up the http header as to communicate with the service using http method.
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		//creating a multivalued map inorder to pass the parameters to the service.
		map.add("userid", uid);
		map.add("bookid", bookid);

		//creating the http entity so as to encapsule the header and body for giving it the service as 
		// a single entity
		request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		
		/*
		 * calling the service using the rest template post method
		 * getting back the response as a http entity.
		 * storing it in a variable called message
		 */
		ResponseEntity<String> message = rt.postForEntity( updatedurl, request , String.class );
		
		//returning to the user
		return message;
	}
	
	
	/*
	 * creating a rest endpoint for returning a book.
	 * calls circulation service and gives the parameter to the return endpoint
	 * collecting the response and returning to the user.
	 */
	
	@PostMapping("/return")
	public @ResponseBody HttpEntity<String> returnBook(@RequestParam(value = "uid") int uid,
			@RequestParam(value="bookid") int bookid){
		
		//updating the url wrt service endpoint
		String updatedurl = url+"return";
		System.out.println("URL:"+updatedurl);
		
		//Setting up the http header as to communicate with the service using http method.
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		//creating a multivalued map inorder to pass the parameters to the service.
		map.add("userid", uid);
		map.add("bookid", bookid);
		
		//creating the http entity so as to encapsule the header and body for giving it the service as 
		// a single entity
		request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);

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
