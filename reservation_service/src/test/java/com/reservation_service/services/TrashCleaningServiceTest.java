package com.reservation_service.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.reservation_service.models.Reservation;
import com.reservation_service.repositories.ReservationRepository;

@SpringBootTest
public class TrashCleaningServiceTest {

	@Autowired
	TrashCleaningService trashCleaningService;

	@MockBean
	ReservationRepository reservationRepo;

	@Test
	public void withValidData() {

		List<Reservation> oldReservations= new ArrayList<Reservation>();
		List<Reservation> emptyreservations= new ArrayList<Reservation>();

		Date today = new Date();
		Calendar cal =new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -30);

		oldReservations.add(new Reservation(1,25,202,cal.getTime()));
		oldReservations.add(new Reservation(2,26,202,cal.getTime()));
		oldReservations.add(new Reservation(3,27,202,cal.getTime()));

		when(reservationRepo.findByDateLessThan(any(Date.class))).thenReturn(oldReservations);
		trashCleaningService.deleteOldReservations();
		verify(reservationRepo,times(1)).deleteAll(any(List.class));

	}


	@Test
	public void when_there_are_no_reservations_to_delete() {
		List<Reservation> emptyreservations= new ArrayList<Reservation>();
		when(reservationRepo.findByDateLessThan(any(Date.class))).thenReturn(emptyreservations);
		trashCleaningService.deleteOldReservations();
		verify(reservationRepo,times(0)).deleteAll();
	}
	
	
}
