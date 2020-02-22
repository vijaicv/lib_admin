package com.PaymentService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.PaymentService.listner.KafkaConsumer;
import com.PaymentService.model.Payment;
import com.PaymentService.model.ReturnMessage;
import com.PaymentService.repository.PaymentRepository;


@SpringBootTest
class PaymentServiceApplicationTests {
	@Autowired
	private KafkaConsumer kafkaconsumer;

	
	@MockBean
	private PaymentRepository paymentRepo;
	@Test
	void consumeTest() {
		ReturnMessage returnMessage = new ReturnMessage();
		returnMessage.setUserId(1);
		returnMessage.setBookId(202);
		returnMessage.setDate(new GregorianCalendar(2020,Calendar.FEBRUARY,15).getTime());
		kafkaconsumer.consume(returnMessage);
		Payment payment = new Payment();
		payment.setBookId(returnMessage.getBookId());
		payment.setDate(returnMessage.getDate());
		payment.setUserId(returnMessage.getUserId());
		payment.setFine(10);
		payment.setCause("borrow limit exceeded ");
		verify(paymentRepo,times(1)).save(any(Payment.class));
	}

}
