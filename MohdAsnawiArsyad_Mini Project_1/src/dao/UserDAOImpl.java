package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exceptions.CustomException;
import connection.MyConnection;
import pojo.User;

public class UserDAOImpl implements UserDAO {

	// create list to store user
	List<User> list_user = new ArrayList<User>();

	// declare db connection
	Connection con;

	public UserDAOImpl() {

		try {
			con = MyConnection.getConnection().conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// function to login and authorize user
	@Override
	public User login(String username, String password) {
		User user = null;

		PreparedStatement stmt;
		try {

			String queryString = "SELECT * FROM user where username=? and password=?";
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				user = mapToUser(rs);

			}

			return user;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;

	}
	
	//function to find all user
	public List<User> findAllUser() {

		List<User> users = new ArrayList<>();

		try {
			String sql = "SELECT * FROM user";

			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User user = mapToUser(rs);
				users.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;

	}

	// function to register new user
	@Override
	public String register(User user) throws CustomException {
		
		String status = null;
		// prepare statement
		String sql = "insert into user values(?,?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmailID());
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getUserType());

			// execute query
			stmt.executeUpdate();
			
			status="Done";
			
			System.out.println("Register new user is done");

		} catch (SQLException e) {
			status ="Fail";

			e.printStackTrace();
		}
		return status;

	}
	
	//function to map user data
	public User mapToUser(ResultSet rs) throws SQLException {

		String username = rs.getString("username");
		String emailID = rs.getString("emailID");
		String password = rs.getString("password");
		String userType = rs.getString("userType");

		User user = new User(username, emailID, password, userType);

		return user;
	}
	


}
