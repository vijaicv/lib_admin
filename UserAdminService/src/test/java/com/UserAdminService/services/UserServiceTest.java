package com.UserAdminService.services;
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

import com.UserAdminService.model.User;
import com.UserAdminService.model.UserMessage;
import com.UserAdminService.repository.UserRepository;
import com.UserAdminService.services.UserService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
	
	@Mock
	UserRepository userrepo;
	
	@Mock
	KafkaTemplate<String, User> kafkaTemplate;
	
	@InjectMocks
	UserService userService;
	
	
	@Test
	public void testAddUserService() {
		try {
			UserMessage userMessage = new UserMessage();
			userMessage.setId(1);
			userMessage.setMessage("user added");
			
			User user=new User();
			user.setName("user");
			user.setRole("admin");
			user.setEmail("admin@company.com");
			
			User user2=new User();
			user2.setName("user");
			user2.setRole("admin");
			user2.setEmail("admin@company.com");
			
			List<User> userList = Arrays.asList(user);
			Mockito.when(userrepo.save(user)).thenReturn(user2);
			Mockito.when(userrepo.findTopByOrderByIdDesc()).thenReturn(userList);
			UserMessage message = userService.add("user", "admin", "admin@company.com");
			System.out.println("---->" + message.getMessage());
			
			assertEquals(message.getMessage(), "user added");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
