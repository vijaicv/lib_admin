package com.reservation_service.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.reservation_service.models.BookReturnMessage;


@Service
public class BookReturnListener {
	
	@Autowired
	ReservationService reservationService;
	
	@KafkaListener(topics="return", groupId="json")
	public void listen(BookReturnMessage message) {
		//TODO: change print statements to logs
	    System.out.println("recieved book return event "+ message.getBookId()+" T = "+new Date().toString() );
	    reservationService.notifyReservedStudents(message.getBookId());
	}
}
