package com.PaymentService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PaymentService.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	List<Payment>findByUserId(int userId);
	

}
