package dao;

import exceptions.ItemNotFoundException;
import exceptions.MenuNotFoundException;
import pojo.Menu;

public interface MenuDAO {
	
	//function to create new menu
	String createMenu(String menuName, String menuDesc);
	
	//function to insert item into menu
	void createMenuItem(String menu_names, ItemDAO itemDAO) throws MenuNotFoundException, ItemNotFoundException;
	
	//function to remove item from menu
	void removeItemFromMenu(String menu_names, ItemDAO itemDAO)  throws MenuNotFoundException, ItemNotFoundException;
	
	//function to see menu with item detail in menu
	void searchMenuWithDetail(String menu_names) throws MenuNotFoundException;
	
	//function to delete menu and all item in the menu
	void deleteMenu(String menu_names) throws MenuNotFoundException;
	
	//function to list all menu
	void listAllMenu();

}
