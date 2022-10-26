package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import connection.MyConnection;
import exceptions.ItemNotFoundException;
import exceptions.MenuNotFoundException;
import pojo.Item;
import pojo.Menu;
import pojo.MenuItem;

public class MenuDAOImpl implements MenuDAO {

	// declare database connection
	Connection con;

	public MenuDAOImpl() {

		try {
			con = MyConnection.getConnection().conn;
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// function to create new menu
	@Override
	public String createMenu(String menuName, String menuDesc) {

		String status = null;
		if (menuName != null && menuDesc != null) {

			// prepare statement
			String sql = "insert into menu values(?,?)";
			PreparedStatement stmt;
			try {
				stmt = con.prepareStatement(sql);

				stmt.setString(1, menuName);
				stmt.setString(2, menuDesc);

				// execute query for create new menu
				stmt.executeUpdate();

				status = "Done";
				
				System.out.println("created menu  succesfull");

			} catch (SQLException e) {

				status = "Fail";

				e.printStackTrace();
			}

		}
		return status;

	}

	// function to find all menu available
	public List<Menu> findAllMenu() {

		List<Menu> menus = new ArrayList<>();

		try {
			String sql = "SELECT * FROM menu";

			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Menu menu = mapToMenu(rs);
				menus.add(menu);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return menus;

	}

	// function to map menu
	private Menu mapToMenu(ResultSet rs) throws SQLException {
		String menuName = rs.getString("menuName");
		String menuDesc = rs.getString("menuDesc");

		Menu menu = new Menu(menuName, menuDesc);

		return menu;
	}

	// function to find all menu item
	public List<MenuItem> findAllMenuItem() {

		List<MenuItem> menu_items = new ArrayList<>();

		try {
			String sql = "SELECT * FROM menuitem";

			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MenuItem menu_item = mapToMenuItem(rs);
				menu_items.add(menu_item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return menu_items;

	}

	// function to map menu item
	private MenuItem mapToMenuItem(ResultSet rs) throws SQLException {
		String menuName = rs.getString("menuName");
		String itemname = rs.getString("itemname");
		String category = rs.getString("category");
		double price = rs.getDouble("price");

		MenuItem menu_item = new MenuItem(menuName, itemname, category, price);

		return menu_item;
	}

	// function to add item into specific menu
	@Override
	public void createMenuItem(String menu_names, ItemDAO itemDAO) throws MenuNotFoundException, ItemNotFoundException {

		Menu menu = findMenubyName(menu_names);
		if (Objects.isNull(menu)) {
			MenuNotFoundException exp = new MenuNotFoundException("No menu found with name: " + menu_names);
			throw exp;
		} else {

			itemDAO.listAllItem();

			Scanner item = new Scanner(System.in);
			System.out.println("Enter itemname to put into " + menu_names + " menu");
			String new_itemname = item.nextLine();

			Item itm = itemDAO.findItembyName(new_itemname);

			if (Objects.isNull(itm)) {
				ItemNotFoundException exp = new ItemNotFoundException("No item found with name: " + menu_names);
				throw exp;
			} else {

				createSpecificMenuItem(menu_names, itm.getItemname(), itm.getCategory(), itm.getSellingPrice());

			}

		}

	}

	public String createSpecificMenuItem(String menu_names, String itemname, String category, double sellingPrice) {
		String mi = null;
		// prepare statement
		String sql = "insert into menuitem values(?,?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, menu_names);
			stmt.setString(2, itemname);
			stmt.setString(3, category);
			stmt.setDouble(4, sellingPrice);

			// execute query for create new menuitem
			stmt.executeUpdate();

			mi = "Done";
			
			System.out.println("created menu item succesfull");

		} catch (SQLException e) {
			mi = "Fail";
			e.printStackTrace();
		}
		return mi;

	}

	// function to list all menu items base on menu name
	private void listAllMenuItemBasedOnMenuName(String menu_names) {
		List<MenuItem> menu_items = findAllMenuItem();

		String header = "Menu Name" + "\t" + "\t" + "Item Name" + "\t" + "Category" + "\t" + "Price";
		System.out.println("============================================================");
		System.out.println(header);
		System.out.println("============================================================");

		menu_items.stream().filter(t -> t.getMenuName().equals(menu_names)).forEach(t -> {
			String row = t.getMenuName() + "\t\t" + t.getItemname() + "\t\t" + t.getCategory() + "\t\t"
					+ t.getSellingPrice() + "\t\t";
			System.out.println(row);
		});

	}

	// function display specific menu based on menu name
	private void specificMenu(String menu_names) {
		List<Menu> mns = findAllMenu();

		String header = "Menu Name" + "\t" + "\t" + "Menu Description";
		System.out.println("============================================================");
		System.out.println(header);
		System.out.println("============================================================");

		mns.stream().filter(t -> t.getMenuName().equals(menu_names)).forEach(t -> {
			String row = t.getMenuName() + "\t\t" + t.getMenuDesc() + "\t\t";
			System.out.println(row);
		});

	}

	// function to find menu base on menu names
	private Menu findMenubyName(String menu_names) {
		List<Menu> mns = findAllMenu();

		return mns.stream().filter(t -> t != null && t.getMenuName().equals(menu_names)).findFirst().orElse(null);
	}

	// function to remove item from specific menu
	@Override
	public void removeItemFromMenu(String menu_names, ItemDAO itemDAO)
			throws MenuNotFoundException, ItemNotFoundException {
		Menu menu = findMenubyName(menu_names);
		if (Objects.isNull(menu)) {
			MenuNotFoundException exp = new MenuNotFoundException("No menu found with name: " + menu_names);
			throw exp;
		} else {

			listAllMenuItemBasedOnMenuName(menu_names);

			Scanner item = new Scanner(System.in);
			System.out.println("Enter itemname to remove from " + menu_names + " menu");
			String new_itemname = item.nextLine();

			Item itm = itemDAO.findItembyName(new_itemname);

			if (Objects.isNull(itm)) {
				ItemNotFoundException exp = new ItemNotFoundException("No item found with name: " + menu_names);
				throw exp;
			} else {

				// prepare statement
				String sql = "DELETE FROM menuitem WHERE itemname =? and menuName =?";
				PreparedStatement stmt;
				try {
					stmt = con.prepareStatement(sql);

					stmt.setString(1, new_itemname);
					stmt.setString(2, menu_names);

					// execute query for create delete menu item
					stmt.executeUpdate();

				} catch (SQLException e) {

					e.printStackTrace();
				}

			}

		}

	}

	// function to search menu & details item in menu based on menu name
	@Override
	public void searchMenuWithDetail(String menu_names) throws MenuNotFoundException {
		Menu menu = findMenubyName(menu_names);
		if (Objects.isNull(menu)) {
			MenuNotFoundException exp = new MenuNotFoundException("No menu found with name: " + menu_names);
			throw exp;
		} else {
			System.out.println();
			System.out.println("-------------------Menu----------------------");
			specificMenu(menu_names);
			System.out.println();
			System.out.println("---------------------Menu Item---------------");
			listAllMenuItemBasedOnMenuName(menu_names);
		}

	}

	// function to delete menu and delete all item in menu
	@Override
	public void deleteMenu(String menu_names) throws MenuNotFoundException {
		Menu menu = findMenubyName(menu_names);
		if (Objects.isNull(menu)) {
			MenuNotFoundException exp = new MenuNotFoundException("No menu found with name: " + menu_names);
			throw exp;
		} else {
			Scanner item = new Scanner(System.in);
			System.out.println("All item related to menu will be deleted once main menu deleted");
			System.out.println("Press 1 to confirm delete | Prease 2 to cancel");
			int option = item.nextInt();

			if (option == 1) {

				deletedMenuAndMenuItem(menu_names);

			}
		}

	}

	// calling function to delete Menu
	public String deletedMenuAndMenuItem(String menu_names) {

		String status = null;
		// prepare statement
		String sql = "DELETE FROM menu WHERE menuName =?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, menu_names);

			// execute query for create new item
			stmt.executeUpdate();

			deleteMenuItem(menu_names);

			status = "Done";

			System.out.println("Menu & MenuItem succesfully deleted");

		} catch (SQLException e) {

			e.printStackTrace();

			status = "Fail";
		}

		return status;

	}

	// calling function to delete Menu Item
	private void deleteMenuItem(String menu_names) {
		// prepare statement
		String sql = "DELETE FROM menuitem WHERE menuName =?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, menu_names);

			// execute query for create new item
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// function to list all menu available
	@Override
	public void listAllMenu() {
		List<Menu> mns = findAllMenu();

		String header = "Menu Name" + "\t" + "\t" + "Menu Description";
		System.out.println("============================================================");
		System.out.println(header);
		System.out.println("============================================================");

		mns.stream().forEach(t -> {
			String row = t.getMenuName() + "\t\t" + t.getMenuDesc() + "\t\t";
			System.out.println(row);
		});

	}

}
