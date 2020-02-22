package com.api_gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
class ReservationTestController {
	
	@Autowired
	MockMvc mock;
	
	@MockBean
	RestTemplate rt;

	@Test
	void testReservationController() throws Exception {
		ResponseEntity<String> message = new ResponseEntity<String>("success", HttpStatus.OK);
		when(rt.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class))).thenReturn(message);
		mock.perform(post("http://localhost:8080/reserve/reserveBook").param("uid", "101").param("bookid", "202")).andDo(print());
		
	}
	
//	@Test
//	void testCirculationController() throws Exception{
//		ResponseEntity<String> message = new ResponseEntity<String>("success", HttpStatus.OK);
//		when(rt.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class))).thenReturn(message);
//		mock.perform(post("http://localhost:8080/circulation/borrow").param("uid", "101").param("bookid", "202")).andDo(print());
//	}
	
	
	

}
