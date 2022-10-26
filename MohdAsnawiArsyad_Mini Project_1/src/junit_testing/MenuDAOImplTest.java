package junit_testing;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dao.MenuDAOImpl;

import pojo.Menu;

public class MenuDAOImplTest {

	MenuDAOImpl menuDAO = new MenuDAOImpl();

	// function to create new menu
	@Test
	public void createMenu() {

		String mns = menuDAO.createMenu("Snack", "Kudap Kudap");

		// check menu being created
		assertEquals(mns, "Done");

	}

	// function to find all menu available
	@Test
	public void findAllMenu() {
		
		int size = menuDAO.findAllMenu().size();
		
		System.out.println(size);
		assertNotEquals(menuDAO.findAllMenu().size(), 4);

	}


	// function to create specific menu item
	@Test
	public void createSpecificMenuItem() {

		String createMenuItem = menuDAO.createSpecificMenuItem("Dinner", "Pudding", "Dessert", 12.50);

		assertEquals(createMenuItem, "Done");

	}
	
	// function to find all menu item
	@Test
	public void findAllMenuItem() {

		assertNotEquals(menuDAO.findAllMenuItem().size(), 4);

	}


	// function to find menu base on menu names
	@Test
	public void findMenubyName() {

		String menu_names = "Dinner";

		List<Menu> mns = menuDAO.findAllMenu();

		Menu itm = mns.stream().filter(t -> t != null && t.getMenuName().equals(menu_names)).findFirst().orElse(null);

		assertEquals(itm.getMenuName(), menu_names);
	}

	// calling function to delete Menu
	@Test
	public void deletedMenuAndMenuItem() {

		String menu_names = "Snack";

		String status = menuDAO.deletedMenuAndMenuItem(menu_names);

		assertEquals(status, "Done");
	}

}
