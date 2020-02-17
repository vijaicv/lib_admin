package com.BookAdminService.controller;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.BookAdminService.model.BookMessage;
import com.BookAdminService.services.BookService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
	
    @InjectMocks
    BookController bookController;
    
    @Mock
    BookService bookService;
	
	@Test
	public void testAddBook() {
		try {
			BookMessage bookMessage = new BookMessage();
			bookMessage.setId(1);
			bookMessage.setMessage("book added");
			MockHttpServletRequest request = new MockHttpServletRequest();
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
			Mockito.when(bookService.add(Mockito.anyString(), Mockito.anyString())).thenReturn(bookMessage);
			BookMessage bookMessageResponse = bookController.addBook("Harry Potter", "J. K. Rowling");
			System.out.println("bookMessageResponse :" + bookMessageResponse);
			System.out.println("bookService :" + bookService);
			assertEquals(bookMessage.getId(), bookMessageResponse.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
