package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//function build jdbc connection
public class MyConnection {
	private static MyConnection single_connection = null;
	public Connection conn;

	private MyConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/surabi", "root", "123456");
	}

	public static MyConnection getConnection() throws SQLException {
		if (single_connection == null) {
			single_connection = new MyConnection();
		}
		return single_connection;
	}

}
