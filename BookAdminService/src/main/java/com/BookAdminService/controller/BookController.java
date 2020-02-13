package com.BookAdminService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BookAdminService.model.Book;
import com.BookAdminService.repository.BookRepository;

@RestController
public class BookController {

	@Autowired
	private BookRepository bookrepo;
	@Autowired
	private KafkaTemplate<String, Book> kafkaTemplate;
	int id;
	private static final String TOPIC1="newbook";
	private static final String TOPIC2="updatebook";
	private static final String TOPIC3="deletebook";
	@PostMapping("/addbook")
	public String addUser(@RequestParam("title") String title,@RequestParam("author") String author, Model m) {
		Book book=new Book();
		book.setTitle(title);
		book.setAuthor(author);
		try {
			bookrepo.save(book);
			m.addAttribute("book",book);
			List<Book> booklist=(List<Book>) bookrepo.findTopByOrderByIdDesc();
			Book b=booklist.get(0);
			//System.out.println(""+b.getId());
			//attach message to kafka here
			
			kafkaTemplate.send(TOPIC1,b);//optional
			return "book id is "+b.getId();
		}
		catch(Exception e) {
			return "failed";
		}
	}
	//update book
	@PostMapping("/findbook")//accessing one record using id
	public Book findBook(@RequestParam("id") int id, Model m) {
		Book book=bookrepo.findById(id).orElseGet(()->null);
		m.addAttribute("book",book);//passing data to form
		return book;//string after space is not displayed
	}
	@PostMapping("/updatebook")
	public String updateBook(@RequestParam("id") int id,@RequestParam("title") String title,@RequestParam("author") String author, Model m) {
		Book book=new Book();
		book.setId(id);
		book.setTitle(title);
		book.setAuthor(author);
		try {
			bookrepo.save(book);
			m.addAttribute("book",book);
			kafkaTemplate.send(TOPIC2,book);
			return "bookupdatesuccess";
		}
		catch(Exception e) {
			return "bookupdatefailure";
		}
	}
	@PostMapping("/deletebook")//accessing one record using id
	public String deleteBook(@RequestParam("id") int id, Model m) {
		Book book=bookrepo.findById(id).orElseGet(()->null);
		m.addAttribute("book",book);
		try {
			bookrepo.deleteById(id);
			kafkaTemplate.send(TOPIC3,book);
			return "book-delete-success";
		}
		catch(Exception e) {
			return "book-delete-failure";
		}
	}
}
