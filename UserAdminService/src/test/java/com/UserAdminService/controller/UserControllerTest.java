package com.UserAdminService.controller;
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

import com.UserAdminService.model.UserMessage;
import com.UserAdminService.services.UserService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
    @InjectMocks
    UserController userController;
    
    @Mock
    UserService userService;
	
	@Test
	public void testAddUser() {
		try {
			UserMessage userMessage = new UserMessage();
			userMessage.setId(1);
			userMessage.setMessage("user added");
			MockHttpServletRequest request = new MockHttpServletRequest();
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
			Mockito.when(userService.add(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(userMessage);
			UserMessage userMessageResponse = userController.addUser("user", "admin", "mail");
			System.out.println("userMessageResponse :" + userMessageResponse);
			System.out.println("userService :" + userService);
			assertEquals(userMessage.getId(), userMessageResponse.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
