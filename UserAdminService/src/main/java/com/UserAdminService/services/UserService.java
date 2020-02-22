package com.UserAdminService.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.UserAdminService.model.User;
import com.UserAdminService.model.UserMessage;
import com.UserAdminService.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	UserRepository userrepo;
	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;
	private static final String TOPIC1="newuser";
	private static final String TOPIC2="updateuser";
	private static final String TOPIC3="deleteuser";
	public UserMessage add(String name,String role, String email) {
		User user=new User();
		user.setName(name);
		user.setRole(role);
		user.setEmail(email);
		try {
			userrepo.save(user);
			List<User> userlist=(List<User>) userrepo.findTopByOrderByIdDesc();
			User u=userlist.get(0);
			kafkaTemplate.send(TOPIC1,u);//optional
			UserMessage msg=new UserMessage(u.getId(), "user added");
			return msg;
		}
		catch(Exception e) {
		return new UserMessage(0,"user add failed");
		}
	}
	public UserMessage findUser(int id) {
		User user=userrepo.findById(id).orElseGet(()->null);
		try {
		UserMessage msg=new UserMessage(user.getId(), "user found");
		return msg;
		}
		catch(Exception e) {
			return new UserMessage(0, "failed user search");
		}
	}
	public UserMessage updateUser(int id,String name,String role,String email) {
		User user=new User();
		user.setId(id);
		user.setName(name);
		user.setRole(role);
		user.setEmail(email);
		try {
			userrepo.save(user);
			kafkaTemplate.send(TOPIC2,user);
			UserMessage msg=new UserMessage(user.getId(), "user updated");
			return msg;
		}
		catch(Exception e) {
			return new UserMessage(0, "failed updation");
		}
	}
	public UserMessage deleteBook(int id) {
		User book=userrepo.findById(id).orElseGet(()->null);
		try {
			userrepo.deleteById(id);
			kafkaTemplate.send(TOPIC3,book);
			UserMessage msg=new UserMessage(id, "user deleted");
			return msg;
		}
		catch(Exception e) {
			return new UserMessage(0, "failed deletion");
		}
	}
}
