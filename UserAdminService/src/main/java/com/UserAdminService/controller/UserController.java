package com.UserAdminService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UserAdminService.model.User;
import com.UserAdminService.repository.UserRepository;
@RestController
public class UserController {

	@Autowired
	private UserRepository userrepo;
	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;
	int id;
	private static final String TOPIC1="newuser";
	private static final String TOPIC2="updateuser";
	private static final String TOPIC3="deleteuser";
	@PostMapping("/adduser")
	public String addUser(@RequestParam("name") String name,@RequestParam("role") String role,@RequestParam("email") String email, Model m) {
		User user=new User();
		user.setName(name);
		user.setRole(role);
		user.setEmail(email);
		try {
			userrepo.save(user);
			m.addAttribute("user",user);
			List<User> userlist=(List<User>) userrepo.findTopByOrderByIdDesc();
			User u=userlist.get(0);
			//System.out.println(""+u.getId());
			//attach message to kafka here
			
			kafkaTemplate.send(TOPIC1,u);//optional
			return "user id is "+u.getId();
		}
		catch(Exception e) {
			return "failed";
		}
	}
	//update user
	@PostMapping("/finduser")//accessing one record using id
	public User findBook(@RequestParam("id") int id, Model m) {
		User user=userrepo.findById(id).orElseGet(()->null);
		m.addAttribute("user",user);//passing data to form
		return user;//string after space is not displayed
	}
	@PostMapping("/updateuser")
	public String updateUser(@RequestParam("id") int id,@RequestParam("name") String name,@RequestParam("role") String role,@RequestParam("email") String email, Model m) {
		User user=new User();
		user.setId(id);
		user.setName(name);
		user.setRole(role);
		user.setEmail(email);
		try {
			userrepo.save(user);
			m.addAttribute("user",user);
			kafkaTemplate.send(TOPIC2,user);//optional
			return "user-update-success";
		}
		catch(Exception e) {
			return "user-update-failure";
		}
	}
	//delete
	@PostMapping("/deleteuser")//accessing one record using id
	public String deleteUser(@RequestParam("id") int id, Model m) {
		User user=userrepo.findById(id).orElseGet(()->null);
		m.addAttribute("user",user);
		try {
			userrepo.deleteById(id);
			kafkaTemplate.send(TOPIC3,user);//optional
			return "user-delete-success";
		}
		catch(Exception e) {
			return "user-delete-failure";
		}
	}
}
