package com.BookAdminService.services;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;


import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.kafka.core.KafkaTemplate;

import com.BookAdminService.model.Book;
import com.BookAdminService.model.BookMessage;
import com.BookAdminService.repository.BookRepository;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class BookServiceTest {
	
	@Mock
	BookRepository bookrepo;
	
	@Mock
	KafkaTemplate<String, Book> kafkaTemplate;
	
	@InjectMocks
	BookService bookService;
	
	
	@Test
	public void testAddBookService() {
		try {
			BookMessage bookMessage = new BookMessage();
			bookMessage.setId(1);
			bookMessage.setMessage("book added");
			
			Book book=new Book();
			book.setTitle("Harry Potter");
			book.setAuthor("J. K. Rowling");
			Book book2=new Book();
			book2.setTitle("Harry Potter");
			book2.setAuthor("J. K. Rowling");
			
			List<Book> bookList = Arrays.asList(book);
			Mockito.when(bookrepo.save(book)).thenReturn(book2);
			Mockito.when(bookrepo.findTopByOrderByIdDesc()).thenReturn(bookList);
			BookMessage message = bookService.add("Harry Potter","J. K. Rowling");
			System.out.println("---->" + message.getMessage());
			
			assertEquals(message.getMessage(), "book added");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
