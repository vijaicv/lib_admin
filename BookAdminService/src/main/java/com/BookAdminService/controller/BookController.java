package com.BookAdminService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BookAdminService.model.BookMessage;
import com.BookAdminService.services.BookService;
@RestController
public class BookController {

	@Autowired
	BookService bookService;
	@PostMapping("/addbook")
	public BookMessage addBook(@RequestParam("title") String title,@RequestParam("author") String author) {
		System.out.println("adding new book");
		BookMessage msg=new BookMessage();
		msg=bookService.add(title, author);
		return msg;
	}
	//finding book
	@PostMapping("/findbook")//accessing one record using id
	public BookMessage findBook(@RequestParam("id") int id) {
		System.out.println("finding the book");
		BookMessage msg=new BookMessage();
		msg=bookService.findBook(id);
		return msg;
	}
	//updating book
	@PostMapping("/updatebook")
	public BookMessage updateBook(@RequestParam("id") int id,@RequestParam("title") String title,@RequestParam("author") String author) {
		System.out.println("updating book");
		BookMessage msg=new BookMessage();
		msg=bookService.updateBook(id, title, author);
		return msg;
	}
	//deleting book
	@PostMapping("/deletebook")//accessing one record using id
	public BookMessage deleteBook(@RequestParam("id") int id) {
		System.out.println("deleting book");
		BookMessage msg=new BookMessage();
		msg=bookService.deleteBook(id);
		return msg;
	}
}
