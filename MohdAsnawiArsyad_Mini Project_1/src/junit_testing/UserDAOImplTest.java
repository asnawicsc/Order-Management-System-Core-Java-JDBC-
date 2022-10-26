package junit_testing;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import exceptions.CustomException;

import dao.UserDAOImpl;
import pojo.User;

public class UserDAOImplTest {

	UserDAOImpl userDAO = new UserDAOImpl();

	// function to login and authorize user
	@Test
	public void login() {

		String username = "asnawi";
		String password = "abc123";

		// check first user name same with asnawi
		assertEquals(userDAO.findAllUser().get(0).getUsername(), username);
		assertEquals(userDAO.findAllUser().get(0).getPassword(), password);

	}

	// function to register new user
	@Test
	public void register() throws CustomException {

		User user = new User("ahmad", "ahmad@gmail.com", "abc111", "User");

		String register = userDAO.register(user);
		// prepare statement
		// check first register success or not
		assertEquals(register, "Done");

	}

	// function to find all user
	@Test
	public void findAllUser() {

		// check first user database got default got 3
		assertEquals(userDAO.findAllUser().size(), 3);

	}



}
