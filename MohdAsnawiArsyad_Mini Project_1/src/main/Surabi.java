package main;

import java.util.Scanner;

import dao.BillDAO;
import dao.BillDAOImpl;
import dao.ItemDAO;
import dao.ItemDAOImpl;
import dao.MenuDAO;
import dao.MenuDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import exceptions.CustomException;
import pojo.User;
import users.AdminThread;
import users.UserThread;

//import users.UserThread;

//main application
public class Surabi {

	// default condition for login
	private static boolean isLogin = false;

	// create a class and an interface for user and product
	static UserDAO userDAO = new UserDAOImpl();
	static ItemDAO itemDAO = new ItemDAOImpl();
	static BillDAO billDAO = new BillDAOImpl();
	static MenuDAO menuDAO = new MenuDAOImpl();

	public static void main(String[] args) {

		// build menu
		StringBuilder sbUser = new StringBuilder("Welcome to Surabi  Billing System \n");
		sbUser.append("--------------------------- \n").append("Press A for Login \n")
				.append("Press B for Register new User \n").append("Press E to exit");

		// start looping for menu
		while (true) {
			if (!isLogin) {

				System.out.println(sbUser);

				Scanner sc = new Scanner(System.in);

				String option = sc.nextLine().toUpperCase();

				if ("A".equals(option)) {
					doLogin();
				} else if ("B".equals(option)) {
					// do register new customer

					Scanner sc1 = new Scanner(System.in);

					System.out.println("Enter your username");
					String username = sc1.nextLine();
					System.out.println("Enter your password");
					String password = sc1.nextLine();
					System.out.println("Enter your email");
					String email = sc1.nextLine();
					// register new customer 
					User new_user = new User(username, email, password, "User");
					try {
						userDAO.register(new_user);
					} catch (CustomException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("New User succesfully created");
					System.out.println();

				} else if ("E".equals(option)) {
					System.out.println("Good Bye!!! Thanks for using Surabi  Billing System.");
					break;
				} else {
					System.out.println("Invalid choice, Please try again..");
				}

			}
		}

	}

	// login function
	private static void doLogin() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter your username");
		String username = sc.next();

		System.out.println("Enter your password");
		String password = sc.next();

		User user = userDAO.login(username, password);

		if (user != null) {
			System.out.println("Login Successful");
			System.out.println();
			// run thread for each user type
			if (user.getUserType().equals("Admin")) {
				AdminThread admin = new AdminThread(itemDAO, billDAO, menuDAO);
				admin.start();
			} else {
				UserThread user_t = new UserThread(user, itemDAO, menuDAO, billDAO);
				user_t.start();
			}

			isLogin = true;

		} else {
			System.out.println("Login Fails");
			System.out.println();
		}
	}

}
