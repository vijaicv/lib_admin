package com.api_gateway.controllers;

import java.util.HashMap;
import java.util.Map;

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

@Controller
public class gatewayController {
	
	RestTemplate rt = new RestTemplate(); 
	
	
	
	@PostMapping("/addBook")
	public @ResponseBody String addBook(@RequestParam(value = "uid") int uid,
			@RequestParam(value = "bName") String bName,
			@RequestParam(value = "authorName") String authorName) {
		System.out.println("Alwin");	
		
		return "Book Successfully added";
	}
	
	@PostMapping("/addUser")
	public @ResponseBody String addUser(@RequestParam(value = "uName") String uName,
			@RequestParam(value= "email") String email,
			@RequestParam(value = "role") String role) {
		
		
		return "user successfully added";
	}
	
	@GetMapping("/borrowBook")
	public @ResponseBody String borrowBook(@RequestParam(value = "uid") int uid,
			@RequestParam(value = "bookid") int bookid) {
		System.out.println("Testing Pranav");
		
		String message = rt.getForObject("http://10.15.1.7:8080/circulation/borrowbook?userid=1&bookid=10", String.class);
		
		return message;
	}
	
	@PostMapping("/returnBook")
	public @ResponseBody ResponseEntity<String> returnBook(@RequestParam(value = "uid") int uid,
			@RequestParam(value="bookid") int bookid) {
//		
//		Map<String, Integer> params = new HashMap<String, Integer>();
//		params.put("userid",uid);
//		params.put("bookid",bookid);
//		System.out.println("Iam here");
//		String message = rt.postForObject("http://10.15.1.7:8080/circulation/returnbook",  params, String.class);
		
		String url = "http://10.15.1.7:8080/circulation/returnbook";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("userid", uid);
		map.add("bookid", bookid);

		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);

		ResponseEntity<String> message = rt.postForEntity( url, request , String.class );
		
		return message;
	}

	
	@PostMapping("/reserve")
	public @ResponseBody ResponseEntity<String> reserve(@RequestParam(value = "uid") int uid,
			@RequestParam(value = "bookid") int bookid) {
		
//		Map<String, Integer> params = new HashMap<String, Integer>();
//		params.put("userId",uid);
//		params.put("bookId",bookid);
//		System.out.println("Iam here");
////		
//		String message = rt.postForObject("http://192.168.43.79:8080/reserve", params,  String.class);
		String url = "http://192.168.43.79:8080/reserve";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("userid", uid);
		map.add("bookid", bookid);

		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);

		ResponseEntity<String> message = rt.postForEntity( url, request , String.class );
		
		return message;
	}
}
