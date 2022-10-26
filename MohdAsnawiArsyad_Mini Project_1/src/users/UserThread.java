package users;

import java.util.List;
import java.util.Scanner;

import dao.BillDAO;
import dao.ItemDAO;
import dao.MenuDAO;
import dao.UserDAO;
import exceptions.ItemNotFoundException;
import exceptions.MenuNotFoundException;
import pojo.BillDetail;
import pojo.User;

public class UserThread extends Thread {

	private User user;
	private MenuDAO menuDAO;
	private BillDAO billDAO;
	private ItemDAO itemDAO;

	public UserThread(User user, ItemDAO itemDAO, MenuDAO menuDAO, BillDAO billDAO) {
		this.menuDAO = menuDAO;
		this.billDAO = billDAO;
		this.itemDAO = itemDAO;
		this.user = user;
	}

	// run user thread
	@Override
	public void run() {
		// user menu
		StringBuilder sbMenu = new StringBuilder("Press 1 to see all items availables in the menu \n");
		sbMenu.append("Press 2 to make an order \n").append("Press 0 to exit & see my final bill");

		Scanner scanner = new Scanner(System.in);
		while (true) {

			System.out.println(sbMenu);

			int choice = scanner.nextInt();

			if (choice == 0) {

				// check current cart, if not empty will display final bill
				if (billDAO.checkCurrentCart().isEmpty()) {
					System.out.println("Thanks for using Surabi  Billing System.");
				} else {

					int billid = billDAO.createFinalBill(user);
					System.out.println();
					System.out.println("---------------Final Bill--------------");
					billDAO.getBill(billid);
					System.out.println();
					System.out.println("---------------Bill Detail--------------");
					billDAO.listBillDetail(billid);

					System.out.println();

					System.out.println("Thanks for using Surabi  Billing System.");

				}

				break;
			} else if (choice == 1) {
				// display item available based on selected menu
				menuDAO.listAllMenu();
				System.out.println();
				System.out.println("Enter menu name to see all item available");
				Scanner sc = new Scanner(System.in);
				String menu_names = sc.nextLine();

				try {
					menuDAO.searchMenuWithDetail(menu_names);
				} catch (MenuNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();
			} else if (choice == 2) {
				// create order
				menuDAO.listAllMenu();
				System.out.println();
				System.out.println("Enter menu name to see all item available for order");
				Scanner sc = new Scanner(System.in);
				String menu_names = sc.nextLine();

				try {
					menuDAO.searchMenuWithDetail(menu_names);
					System.out.println();
					System.out.println("Enter item to name to order");
					Scanner sc1 = new Scanner(System.in);
					String item_name = sc1.nextLine();
					System.out.println("Enter how many plate order");
					Scanner sc2 = new Scanner(System.in);
					String qty = sc2.nextLine();

					try {

						try {
							int integer = Integer.parseInt(qty);

							billDAO.put_order_in_cart(user, itemDAO, item_name, integer);
						} catch (NumberFormatException e) {
							System.out.println("Error! Invalid integer for quantity Try again.");
						}

					} catch (ItemNotFoundException e) {

						e.printStackTrace();
					}

				} catch (MenuNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();

			} else {
				System.out.println("Wrong choice");
			}

		}

	}
}
