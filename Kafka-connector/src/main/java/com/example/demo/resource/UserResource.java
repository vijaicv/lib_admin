package com.example.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;

@RestController
@RequestMapping("kafka")
public class UserResource {

	@Autowired
	KafkaTemplate<String, User> kt;
	private static final String  topic = "newtopic";
	
	@GetMapping("/publish/{name}")
	public String publish (@PathVariable("name") String msg) {
		User u = new User();
		u.setName(msg);
		u.setDep("IT");
		u.setSalary(2333333);
		
		kt.send(topic, u);
		return "Successfull";
	}
	
	
}
