package com.circulation_service.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.circulation_service.models.Circulation;
import com.circulation_service.repositories.CirculationRepository;

@RestController
@RequestMapping("/circulation")
public class CirculationServiceResource {

	@Autowired
	KafkaTemplate<String, Circulation> kt;
	private static final String  topic = "Firsttopic";
	
	@Autowired
	private CirculationRepository circulationRepository;
	
	//Deleting the data from database of the returned book
	@GetMapping("/returnbook")
	public String returnBook (@RequestParam("userid") int userid,@RequestParam("bookid") int bookid) {
		
		Circulation cdel=circulationRepository.findByUserIdAndBookId(userid, bookid);
		circulationRepository.delete(cdel);
		
		//Sending message to the Message System
		Circulation c = new Circulation();
		c.setBookId(bookid);
		c.setUserId(userid);
		c.setDate(new Date());

		
		kt.send(topic, c);
		return "Successfully returned book";
	}
	//Adding data into the circulation table
	@GetMapping("/borrowbook")
	public String borrowBook(@RequestParam("userid") int userid,@RequestParam("bookid") int bookid)
	{
		Circulation circulation=new Circulation();
		
		circulation.setUserId(userid);
		circulation.setBookId(bookid);
		circulation.setDate(new Date());
		
		circulationRepository.save(circulation);
		
		return "Added";
	}
	
}
