package users;

import java.util.Scanner;

import dao.BillDAO;
import dao.ItemDAO;
import dao.MenuDAO;
import exceptions.ItemNotFoundException;
import exceptions.MenuNotFoundException;
import pojo.Item;

public class AdminThread extends Thread {

	private ItemDAO itemDAO;
	private BillDAO billDAO;
	private MenuDAO menuDAO;

	public AdminThread(ItemDAO itemDAO, BillDAO billDAO, MenuDAO menuDAO) {
		this.itemDAO = itemDAO;
		this.billDAO = billDAO;
		this.menuDAO = menuDAO;
	}

	@Override
	public void run() {

		// admin menu
		StringBuilder sbMenu = new StringBuilder("-----------BILL-------------- \n\n");
		sbMenu.append("Press 1 see all bills generated today \n")
				.append("Press 2 see all total bills for current month \n\n")
				.append("-----------CRUD ITEM-------------- \n\n").append("Press 3 to create items  \n")
				.append("Press 4 to update items using itemname  \n")
				.append("Press 5 to delete items using itemname \n").append("Press 6 to search items using itemname \n")
				.append("Press 7 to list all items  \n\n").append("-----------CRUD MENU-------------- \n\n")
				.append("Press 8 to create menu \n").append("Press 9 to insert item to menu \n")
				.append("Press 10 to remove item from menu \n").append("Press 11 to search menu with detail\n")
				.append("Press 12 to delete menu \n").append("Press 13 to list all menu \n\n")
				.append("-----------LOG OUT-------------- \n\n").append("Press 0 to logout");

		Scanner scanner = new Scanner(System.in);
		while (true) {

			System.out.println(sbMenu);

			int choice = scanner.nextInt();

			if (choice == 0) {
				// exit application
				System.out.println("Thanks for using Surabi  Billing System.");
				break;
			} else if (choice == 1) {
				// generate all bill make by today
				billDAO.generateTodayBills();

				System.out.println();
			} else if (choice == 2) {
				// generate all bill for this month
				billDAO.generateMonthBills();

				System.out.println();
			} else if (choice == 3) {
				// function to create item
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the item name :");
				String itemname = sc.nextLine();
				Scanner sc2 = new Scanner(System.in);
				System.out.println("Enter the item category :");
				String category = sc.nextLine();
				Scanner sc3 = new Scanner(System.in);
				System.out.println("Enter the item selling price :");
				double sellingPrice = sc.nextDouble();

				Item item = new Item(itemname, category, sellingPrice);

				itemDAO.create_item(item);

				System.out.println(itemname + " created succesfully");

				System.out.println();
			} else if (choice == 4) {
				// show all item list
				itemDAO.listAllItem();

				System.out.println();

				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the item name to update:");
				String itemname = sc.nextLine();

				// function to update item base on item name
				try {
					itemDAO.updateItem(itemname);
				} catch (ItemNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();
			} else if (choice == 5) {

				// show all item list
				itemDAO.listAllItem();

				System.out.println();

				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the item name to delete:");
				String itemname = sc.nextLine();
				// function to delete item base on item name
				try {
					itemDAO.deleteItem(itemname);
				} catch (ItemNotFoundException e) {

					e.printStackTrace();
				}

			} else if (choice == 6) {

				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the item name to search:");
				String itemname = sc.nextLine();
				// function to search item base on item name
				try {
					itemDAO.searchItem(itemname);
				} catch (ItemNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();
			} else if (choice == 7) {

				itemDAO.listAllItem();

				System.out.println();
			} else if (choice == 8) {
				// function to create new menu
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the menu name :");
				String menuName = sc.nextLine();
				Scanner sc2 = new Scanner(System.in);
				System.out.println("Enter the menu description :");
				String menuDesc = sc.nextLine();

				menuDAO.createMenu(menuName, menuDesc);

				System.out.println();
			} else if (choice == 9) {
				// function to put item base into selected menu
				menuDAO.listAllMenu();
				Scanner sc = new Scanner(System.in);
				System.out.println("Select Menu name to put in item :");
				String menu_names = sc.nextLine();

				try {
					try {
						menuDAO.createMenuItem(menu_names, itemDAO);
					
					} catch (ItemNotFoundException e) {

						e.printStackTrace();
					}
				} catch (MenuNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();
			} else if (choice == 10) {
				// function to remove item from selected menu
				menuDAO.listAllMenu();
				Scanner sc = new Scanner(System.in);
				System.out.println("Select Menu name to remove an item :");
				String menu_names = sc.nextLine();

				try {
					try {
						menuDAO.removeItemFromMenu(menu_names, itemDAO);

						System.out.println("remove item from menu succesfull");
					} catch (ItemNotFoundException e) {

						e.printStackTrace();
					}
				} catch (MenuNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();
			} else if (choice == 11) {
				// function to see menu detail with item in it
				menuDAO.listAllMenu();
				System.out.println();
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter Menu name to see detail :");
				String menu_names = sc.nextLine();

				try {
					menuDAO.searchMenuWithDetail(menu_names);
				} catch (MenuNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();
			} else if (choice == 12) {
				// function to delete menu base on menu name , 
				//when menu deleted, all item in it also deleted
				menuDAO.listAllMenu();
				System.out.println();
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter Menu name to delete :");
				String menu_names = sc.nextLine();

				try {
					menuDAO.deleteMenu(menu_names);
				} catch (MenuNotFoundException e) {

					e.printStackTrace();
				}

				System.out.println();

			} else if (choice == 13) {
				//function list all menu 
				menuDAO.listAllMenu();

				System.out.println();

			} else {
				System.out.println("Wrong choice");
			}

		}

	}

}
