package com.reservation_service.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.reservation_service.models.Reservation;
import com.reservation_service.models.ReservationMessage;
import com.reservation_service.repositories.ReservationRepository;



@Component
public class ReservationService {
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	KafkaTemplate<String, ReservationMessage> template;

	public Reservation reserve(int userId,int bookId) {
		Reservation reservation = new Reservation();
		reservation.setBookId(bookId);
		reservation.setUserId(userId);
		reservation.setDate(new Date());
		return reservationRepo.save(reservation);
	}
	
	
	public void notifyReservedStudents(int bookId) {
		List<Reservation> reservations = reservationRepo.findByBookId(bookId);
		List<Integer> listOfReservedStudents = new ArrayList<Integer>();
		
		for(Reservation r : reservations) {
			listOfReservedStudents.add(r.getUserId());
		}
		
		ReservationMessage r_message = new ReservationMessage(listOfReservedStudents, "Book has returned");
		template.send("notify", r_message);
	}
	
	public void deleteUserWhoClaimed(int userId, int bookId) {
		
		Reservation r = reservationRepo.findByUserIdAndBookId(userId, bookId);
		reservationRepo.delete(r);
		System.out.println("User Deleted");
		
	}

}
