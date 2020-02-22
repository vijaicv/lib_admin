package com.circulation_servive.resourceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.circulation_service.models.Circulation;
import com.circulation_service.repositories.CirculationRepository;
import com.circulation_service.resource.CirculationServiceResource;

@WebMvcTest(CirculationServiceResource.class)
@ContextConfiguration(classes = CirculationServiceResource.class)
class Circulation_service_Resource_Test {
	
	@Autowired
	CirculationServiceResource resource;
	
	@MockBean
	KafkaTemplate<String, Circulation> kt;
	
	@MockBean
	private CirculationRepository circulationRepository;
	
	@Autowired
	MockMvc mockmvc;

	@Test
	void load() throws Exception {
		assertThat(resource).isNotNull();
	}

	@Test
	public void borrowSuccess() throws Exception {
		Circulation[] c=new Circulation[] {
				new Circulation(1, 25, 202, new Date()),
				new Circulation(2, 25, 203, new Date()),
		};
		when(circulationRepository.findAllByUserId(any(Integer.class))).thenReturn(c);
		this.mockmvc.perform(
				post("/circulation/borrow")
				.param("userid","10")
				.param("bookid","10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("Book Borrowed"));
	}
	
	@Test
	public void bookreturn()throws Exception {
		this.mockmvc.perform(
				post("/circulation/return")
				.param("userid","20")
				.param("bookid","20"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("Book is not in Database"));
	}
	
	@Test
	public void booklist() throws Exception {
		Circulation[] c=new Circulation[] {
				new Circulation(1, 26, 202, new Date()),
				new Circulation(2, 26, 203, new Date()),
		};
		when(circulationRepository.findAllByUserId(any(Integer.class))).thenReturn(c);
		this.mockmvc.perform(
				post("/circulation/booklist")
				.param("userid","26"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("202,203,"));
	}

}
