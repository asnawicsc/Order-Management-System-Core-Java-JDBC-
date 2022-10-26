package dao;

import java.util.List;

import exceptions.ItemNotFoundException;
import pojo.Item;

public interface ItemDAO {
	
	//function to create new item
	String create_item(Item item);
	
	//function to update item base on it name
	void updateItem(String itemname) throws ItemNotFoundException;
	
	//function to delete item base on it name
	String deleteItem(String itemname) throws ItemNotFoundException;
	
	//function search item base on it name
	void searchItem(String itemname) throws ItemNotFoundException;
	
	//function to list all item
	void listAllItem();
	
	//function to find all item
	List<Item> findAllItem();
	
	//function to find specific item base on it name
	Item findItembyName(String new_itemname);




}
