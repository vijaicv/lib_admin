package com.PaymentService.listner;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.PaymentService.model.Payment;
import com.PaymentService.model.ReturnMessage;
import com.PaymentService.repository.PaymentRepository;

@Service
public class KafkaConsumer {
	
	@Autowired
	PaymentRepository paymentRepo;
	
//	@Value("${borrow-day-limit}")
	int maxDaysOfBorrowing;
	
//	@Value("${fine-per-day}")
	int finePerDay;
	
	
	

	@KafkaListener(topics = "return", groupId = "lib_admin")
	public void consume(ReturnMessage message) {
		
		System.out.println("consumed message is "+ message.getDate());
		
		
		Date today = new Date();
		long diff = today.getTime() - message.getDate().getTime();
		long diffdays = diff/(24*60*60*1000);
		int borrowed_days = Math.round(diffdays);
		
		
		int fine=0;
		System.out.println("book was borrowed for "+ borrowed_days + "days");
		if(borrowed_days>maxDaysOfBorrowing) {
			System.out.println("borrow limit exceeded ");
			fine=borrowed_days*finePerDay;
			
			Payment payment = new Payment();
			payment.setBookId(message.getBookId());
			payment.setDate(message.getDate());
			payment.setUserId(message.getUserId());
			
			paymentRepo.save(payment);
		}
		System.out.println("fine = "+fine);
		
	}

}
