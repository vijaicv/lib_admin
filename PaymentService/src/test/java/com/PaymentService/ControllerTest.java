package com.PaymentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.PaymentService.controller.PaymentController;
import com.PaymentService.model.Payment;
import com.PaymentService.repository.PaymentRepository;

@WebMvcTest(PaymentController.class)
public class ControllerTest {
	
	@Autowired
	PaymentController paymentController;
	
	@Autowired
	MockMvc mockmvc;
	
	@MockBean
	PaymentRepository paymentRepo;

	@Test
	public void contexLoads() throws Exception {
		assertThat(paymentController).isNotNull();
}
	

	@Test
	public void shouldReturnSuccessWithCorrectInput() throws Exception {
		
		List<Payment> payments = new ArrayList<Payment>();
		payments.add(new Payment(1,10,101,new Date(),20,"some reason"));
		payments.add(new Payment(1,10,101,new Date(),30,"some reason"));
		payments.add(new Payment(1,10,101,new Date(),50,"some reason"));
		
		
		when( paymentRepo.findByUserId(any(Integer.class))).thenReturn(payments);
		
		MvcResult content = this.mockmvc.perform(
				post("/showfine")
				.param("userId", "25"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		
		assertThat(content.getResponse().getContentAsString())
		.isEqualTo("Total fine for user is 100\n" + "Cause for the fine is some reason");
	}

}
 