package com.reservation_service.services;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.reservation_service.models.BookReturnMessage;
import com.reservation_service.models.Reservation;
import com.reservation_service.models.ReservationMessage;
import com.reservation_service.repositories.ReservationRepository;


@SpringBootTest
public class ReservationServiceTest {
	
	@MockBean
	ReservationRepository reservationRepo;
	
	@MockBean
	KafkaTemplate<String, ReservationMessage> template;
	
	
	@Autowired
	ReservationService reservationService;
	
	
	@Test
	public void contexLoads() throws Exception {
		assertThat(reservationService).isNotNull();
		assertThat(reservationRepo).isNotNull();
	}
	
	@Test
	public void reserveWithValidInputs() {
		
		int userId = 20;
		int bookId = 202;
		
		Reservation expectedObject = new Reservation();
		expectedObject.setBookId(bookId);
		expectedObject.setUserId(userId);
		expectedObject.setDate(new Date());
		
		
		when(reservationRepo.save(any(Reservation.class))).thenReturn(expectedObject);	

		Reservation actualObject = reservationService.reserve(20, 202);
		
		assertThat(actualObject).isEqualTo(expectedObject);
		
	}
	
	
	@Test
	public void should_throw_nullpointerexception_when_oneormore_input_is_null() {
		Integer userId = null;
		int bookId = 202;	
		assertThrows(NullPointerException.class, ()->{
			Reservation r = reservationService.reserve(userId, bookId);
			assertThat(r).isNull();
		});
	}
	
	
	

}
