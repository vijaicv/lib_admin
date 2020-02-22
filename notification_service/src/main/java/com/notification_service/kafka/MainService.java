package com.notification_service.kafka;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification_service.models.New_book;
import com.notification_service.models.ReservationMessage;
import com.notification_service.models.user_inst;
import com.notification_service.repository.user_instRepository;


@RestController
public class MainService  {

	
	
	
	@Autowired
	user_instRepository userRepo;

	

//	@GetMapping("/consumeJsonMessage")
//	public ReservationMessage consumeJsonMessage(){
//		return messageFromTopic;
//	}
	

	@KafkaListener(groupId = "lib_admin", topics = "notify")
	public String notifyReservedUsers(ReservationMessage reservationMessage) {
		//messageFromTopic = reservationMessage;
		List<Integer> r_ids= reservationMessage.getTargets();
		String emailLog = "";
		for(int i=0;i<r_ids.size();i++) {
			int u_id=r_ids.get(i);
			user_inst user = userRepo.findById(u_id).get();
			emailLog+=">Sending email to =>"+user.getEmail()+"  message="+ reservationMessage.getMessage();  
		} 
		return emailLog;
	} 
	@KafkaListener(groupId="lib_admin",topics="newuser",containerFactory="newUserMesssageKafkaListenerContainerFactory" )
	public user_inst getNewUserMessage(user_inst newUser) {
		userRepo.save(newUser);
		return newUser;
	}
	
	@KafkaListener(groupId="lib_admin",topics="newbook",containerFactory="newBookMesssageKafkaListenerContainerFactory" )
	public String notifyTeachers(New_book newBookMessage)	{
	    String bookName=newBookMessage.getTitle();
		String bookAuthor=newBookMessage.getAuthor();
        String notifylog="";		
	    List<user_inst> teachers= userRepo.findByRole("teacher");
	    for(int j=0;j<teachers.size(); j++) {
	    	user_inst user=teachers.get(j);
	    	notifylog+="  Email:-  Subject:- New Book Added  Body:- The book "+bookName+" written by "+bookAuthor+" has been newley added. Has been sent to "+user.getEmail();
	    }
	    return notifylog;
	}
	
	
	
	
	
	
	
	
	
	
}