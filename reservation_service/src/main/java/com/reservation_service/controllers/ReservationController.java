package com.reservation_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation_service.services.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@PostMapping("/reserve")
	public String reserveBook(@RequestParam int userId, @RequestParam int bookId) {
		System.out.println("reserving book");
		reservationService.reserve(userId, bookId);
		return "success";
	}
}
