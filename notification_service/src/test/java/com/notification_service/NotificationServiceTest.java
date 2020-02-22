


package com.notification_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito.Then;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.notification_service.kafka.MainService;
import com.notification_service.models.New_book;
import com.notification_service.models.ReservationMessage;
import com.notification_service.models.user_inst;
import com.notification_service.repository.user_instRepository;


@SpringBootTest
class NotificationServiceTest {
	@Autowired
	 private MainService service;
	@MockBean
     private  user_instRepository repository;
	
	
	@Test
	 public void getNewUserMessageTest() {
		    user_inst newuser= new user_inst(20,"teacher","teach@gmail.com");
		    service.getNewUserMessage(newuser);
		    verify(repository,times(1)).save(any(user_inst.class));
	}
	@Test
	 public void notifyReservedUsersTest() {
		  List<Integer> list = new ArrayList<Integer>();
		  list.add(1);
		     ReservationMessage reservationMessage = new ReservationMessage();
			 reservationMessage.setMessage("notified");
			 reservationMessage.setTargets(list);
			 user_inst user = new user_inst(1,"student","st@something.com");
	         when(repository.findById(any(Integer.class))).thenReturn(Optional.of(user));
		     String response = service.notifyReservedUsers(reservationMessage);
		 
		 assertThat(response).isEqualTo(">Sending email to =>st@something.com  message=notified");
		 
	 }
	@Test
	public void notifyTeachersTest() {
		New_book book=new New_book();
		book.setTitle("As you like it");
		book.setAuthor("Shakespeare");
		List<user_inst>user1 = new  ArrayList<user_inst>();
	    user1.add(new user_inst(2, "teacher", "t1@gmail.com"));
		when(repository.findByRole(any(String.class))).thenReturn(user1);
	    String resp=service.notifyTeachers(book);
		
	    assertThat(resp).isEqualTo("  Email:-  Subject:- New Book Added  Body:- The book As you like it written by Shakespeare has been newley added. Has been sent to t1@gmail.com");
    }
				
	}
	

