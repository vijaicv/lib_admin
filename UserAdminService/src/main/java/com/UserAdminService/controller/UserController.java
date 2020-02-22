package com.UserAdminService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UserAdminService.model.User;
import com.UserAdminService.model.UserMessage;
import com.UserAdminService.repository.UserRepository;
import com.UserAdminService.services.UserService;
@RestController
public class UserController {

	@Autowired
	UserService userService;
	@PostMapping("/adduser")
	public UserMessage addUser(@RequestParam("name") String name,@RequestParam("role") String role,@RequestParam("email") String email) {
		System.out.println("adding new user");
		UserMessage msg=new UserMessage();
		msg=userService.add(name,role,email);
		return msg;
	}
	//finding user
	@PostMapping("/finduser")//accessing one record using id
	public UserMessage findUser(@RequestParam("id") int id) {
		System.out.println("finding the user");
		UserMessage msg=new UserMessage();
		msg=userService.findUser(id);
		return msg;
	}
	//updating user
	@PostMapping("/updateuser")
	public UserMessage updateUser(@RequestParam("id") int id,@RequestParam("name") String name,@RequestParam("role") String role,@RequestParam("email") String email) {
		System.out.println("updating user");
		UserMessage msg=new UserMessage();
		msg=userService.updateUser(id,name,role,email);
		return msg;
	}
	//deleting user
	@PostMapping("/deleteuser")//accessing one record using id
	public UserMessage deleteUser(@RequestParam("id") int id) {
		System.out.println("deleting user");
		UserMessage msg=new UserMessage();
		msg=userService.deleteBook(id);
		return msg;
	}
}
