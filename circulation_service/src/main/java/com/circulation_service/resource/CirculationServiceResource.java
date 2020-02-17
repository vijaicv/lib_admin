package com.circulation_service.resource;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.circulation_service.models.Circulation;
import com.circulation_service.repositories.CirculationRepository;

@RestController
@RequestMapping("/circulation")
public class CirculationServiceResource {

	//kafka topic name given
	
	@Autowired
	KafkaTemplate<String, Circulation> kt;
	private static final String  topic = "return";
	
	@Autowired
	private static final String topicborrow="borrow";
	
	@Autowired
	private CirculationRepository circulationRepository;
	
	//Deleting the data from database of the returned book
	@PostMapping("/return")
	public String returnBook (@RequestParam("userid") int userid,@RequestParam("bookid") int bookid) {
		
		Circulation cdel=circulationRepository.findByUserIdAndBookId(userid, bookid);

		if(cdel!=null)
		{
			
			System.out.println("Book Returned\n"+userid+"\t"+bookid);
			
			//Sending message to the Message System
			kt.send(topic, cdel);
			circulationRepository.delete(cdel);
			return "Successfully returned book";
		}
		else
		{
			return "Book is not in Database";
		}
	}
	//Adding data into the circulation table
	@PostMapping("/borrow")
	public String borrowBook(@RequestParam("userid") int userid,@RequestParam("bookid") int bookid)
	{
		Circulation[] cborrow=circulationRepository.findAllByUserId(userid);
		int brln=0;
		brln=cborrow.length;
		System.out.println(brln);
		
		if(brln<3)
		{
		System.out.println(userid+"\t"+bookid);
		Circulation circulation=new Circulation();
		
		circulation.setUserId(userid);
		circulation.setBookId(bookid);
		circulation.setDate(new Date());
		System.out.println("Book Borrowed id :"+bookid);
		circulationRepository.save(circulation);
		kt.send(topicborrow, circulation);
		
		return "Book Borrowed";
		}
		else
		{
			return "Book Borrowing limit is Three";
		}
	}
	
	@GetMapping("/booklist")
	public String bookList(@RequestParam("userid")int userid)
	{
		Circulation[] c=circulationRepository.findAllByUserId(userid);
		
		System.out.println("Book allocated list Test\n");
		int ln=c.length;
		String str=new String();
		
		for(int i=0;i<ln;i++)
		{
			str=str+Integer.toString(c[i].getBookId())+",";
		}
		
		System.out.println(str);
		
		return str;
	}
	

	
}