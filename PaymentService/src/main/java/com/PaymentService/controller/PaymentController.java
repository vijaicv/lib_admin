package com.PaymentService.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PaymentService.model.Payment;
import com.PaymentService.model.ReturnMessage;
import com.PaymentService.repository.PaymentRepository;

@RestController
public class PaymentController {
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	
//	@GetMapping("/")
//	public String add() {
//		Payment p = new Payment();
//		p.setBookId(204);
//		p.setDate(new Date());
//		p.setFine(20);
//		p.setUserId(1);
//		
//		
//		paymentRepo.save(p);
//		return "success";
//	}
	
	@KafkaListener(topics = "return", groupId = "lib_admin")
	public void recieve(ReturnMessage message) {
		System.out.println("message recieved"+ message.getDate());
	}
	
	
	@PostMapping("/showfine")
	private String addUser(@RequestParam("userId")Integer userId){
    List<Payment> paymentobj = paymentRepo.findByUserId(userId);
    int totalFine = 0;
    for(Payment p:paymentobj) {
    	totalFine+=p.getFine();
    	
    }
		
		return "Total fine for user is "+ totalFine;

	}

}
