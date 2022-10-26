package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import exceptions.CustomException;
import pojo.User;

public interface UserDAO {

	// function to login to the system
	User login(String username, String password);

	// function to register default user
	String register(User user) throws CustomException;
	
	//function to find all user in database
	List<User> findAllUser();
	
	 User mapToUser(ResultSet rs)throws SQLException;
	

}
