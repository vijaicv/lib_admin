package com.reservation_service.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.reservation_service.models.BookReturnMessage;

@Service
public class BookBorrowListener {
	
	@Autowired
	ReservationService reservationService;
	
	@KafkaListener(topics="borrow", groupId="lib_admin")
	public void listen(BookReturnMessage message) {
		//TODO: change print statements to logs
		System.out.println("details "+ message.getBookId()+" user id "+ message.getUserId());
		try {
			reservationService.deleteUserWhoClaimed(message.getUserId(), message.getBookId());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("USer has not reserved Book");
		}
		
	}

}
