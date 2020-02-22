package com.reservation_service.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.reservation_service.models.Reservation;
import com.reservation_service.services.ReservationService;



@WebMvcTest(ReservationController.class)
public class controllerTest {

	@Autowired
	ReservationController controller;

	@Autowired
	MockMvc mockmvc;

	@MockBean
	ReservationService reservationService;


	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	
	

	@Test
	public void shouldReturnSuccessWithCorrectInput() throws Exception {
		
		Reservation reservation = new Reservation();
		reservation.setBookId(202);
		reservation.setUserId(25);
		reservation.setDate(new Date());
			
		
		this.mockmvc.perform(
				post("/reserve")
				.param("userId", "25")
				.param("bookId","202"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("success"));
	}
	
	
	
	
	
	@Test
	public void shouldReturnExceptionWithMissingParameter() throws Exception {
		this.mockmvc.perform(
				post("/reserve")
				.param("userId", "25"))
				.andDo(print())
				.andExpect(status().is4xxClientError());
		
		this.mockmvc.perform(
				post("/reserve")
				.param("bookId", "202"))
				.andDo(print())
				.andExpect(status().is4xxClientError());
		
		this.mockmvc.perform(post("/reserve"))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}
	
	
	
	
	
}
