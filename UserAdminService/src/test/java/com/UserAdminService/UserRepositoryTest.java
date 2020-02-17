package com.UserAdminService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.UserAdminService.model.User;
import com.UserAdminService.repository.UserRepository;

import scala.collection.generic.BitOperations.Int;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager entityManager;
	@Test
	public void testAdduser() {
		User user=new User();
		user.setName("sajina");
		user.setRole("student");
		user.setEmail("sajinaadfo@gmail.com");
		Int id=entityManager.persistAndGetId(user, Int.class);
		assertNotNull(id);
		User user1 = userRepository.findByName("sajina");
		assertNotNull(user);
		assertEquals(user1.getName(), user.getName());
		assertEquals(user1.getRole(), user.getRole());
		assertEquals(user1.getEmail(), user.getEmail());
	}
	
	/*@Test
	public void testFindUser() {

		User user = new User();
		user.setName("sajina");
		user.setRole("student");
		user.setEmail("sajinaadfo@gmail.com");
		userRepository.save(user);
		User user1 = userRepository.findByName("sajina");
		assertNotNull(user);
		assertEquals(user1.getName(), user.getName());
		assertEquals(user1.getRole(), user.getRole());
		assertEquals(user1.getEmail(), user.getEmail());
	}
	
	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setName("sajina");
		user.setRole("student");
		user.setEmail("sajinaadfo@gmail.com");
		userRepository.save(user);
		*/
}
