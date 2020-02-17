package com.BookAdminService.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.BookAdminService.model.Book;
import com.BookAdminService.model.BookMessage;
import com.BookAdminService.repository.BookRepository;

@Component
public class BookService {

	@Autowired
	BookRepository bookrepo;
	@Autowired
	private KafkaTemplate<String, Book> kafkaTemplate;
	private static final String TOPIC1="newbook";
	private static final String TOPIC2="updatebook";
	private static final String TOPIC3="deletebook";
	public BookMessage add(String title,String author) {
		Book book=new Book();
		book.setTitle(title);
		book.setAuthor(author);
		try {
			bookrepo.save(book);
			List<Book> booklist=(List<Book>) bookrepo.findTopByOrderByIdDesc();
			Book b=booklist.get(0);
			kafkaTemplate.send(TOPIC1,b);//optional
			BookMessage msg=new BookMessage(b.getId(), "book added");
			return msg;
		}
		catch(Exception e) {
		return new BookMessage(0,"failed");
		}
	}
	public BookMessage findBook(int id) {
		Book book=bookrepo.findById(id).orElseGet(()->null);
		try {
		BookMessage msg=new BookMessage(book.getId(), "book found");
		return msg;
		}
		catch(Exception e) {
			return new BookMessage(0, "failed book search");
		}
	}
	public BookMessage updateBook(int id,String title,String author) {
		Book book=new Book();
		book.setId(id);
		book.setTitle(title);
		book.setAuthor(author);
		try {
			bookrepo.save(book);
			kafkaTemplate.send(TOPIC2,book);
			BookMessage msg=new BookMessage(book.getId(), "book updated");
			return msg;
		}
		catch(Exception e) {
			return new BookMessage(0, "failed updation");
		}
	}
	public BookMessage deleteBook(int id) {
		Book book=bookrepo.findById(id).orElseGet(()->null);
		try {
			bookrepo.deleteById(id);
			kafkaTemplate.send(TOPIC3,book);
			BookMessage msg=new BookMessage(id, "book deleted");
			return msg;
		}
		catch(Exception e) {
			return new BookMessage(0, "failed deletion");
		}
	}
}
