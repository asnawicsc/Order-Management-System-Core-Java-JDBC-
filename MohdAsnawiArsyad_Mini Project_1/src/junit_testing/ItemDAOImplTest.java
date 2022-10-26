package junit_testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import connection.MyConnection;
import dao.ItemDAOImpl;
import exceptions.ItemNotFoundException;
import pojo.Item;

public class ItemDAOImplTest {

	ItemDAOImpl itemDAO = new ItemDAOImpl();

	// function to create item
	@Test
	public void create_item() {

		Item itm = new Item("Lolipop", "Dessert", 1.87);

		String mns = itemDAO.create_item(itm);

		// check item being created
		assertEquals(mns, "Done");

	}

	// update specific item
	@Test
	public void updateItemDetail() {

		Item itm = new Item("Carrot Cake", "Dessert", 3.87);

		String status = itemDAO.updateItemDetail("Carrot Cake", itm);
			
		System.out.println(status);
		// check item being updated
		assertEquals(status, "Done");

	}

	// delete specific item
	@Test
	public void deleteItem() throws ItemNotFoundException {

		String updated_name = "Lolipop";

		String itm = itemDAO.deleteItem(updated_name);

		// check item being deleted
		assertEquals(itm, "Done");

	}

	// function to search item
	@Test
	public void searchItem() throws ItemNotFoundException {

		String updated_name = "Carrot Cake";
		Item item = itemDAO.findItembyName(updated_name);

		// check item exist
		assertEquals(item.getItemname(), updated_name);

	}

	// function to find all item
	@Test
	public void findAllItem() {

		int itm = itemDAO.findAllItem().size();

		assertNotEquals(itm, 0);

	}

	// function to find item
	@Test
	public void findItembyName() {
		String updated_name = "Carrot Cake";
		List<Item> itms = itemDAO.findAllItem();

		Item data = itms.stream().filter(t -> t != null && t.getItemname().equals(updated_name)).findFirst()
				.orElse(null);

		// check item exist
		assertEquals(data.getItemname(), updated_name);
	}

}
