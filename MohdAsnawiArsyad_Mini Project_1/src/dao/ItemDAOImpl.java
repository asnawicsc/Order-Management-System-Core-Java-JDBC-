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
import pojo.Item;

public class ItemDAOImpl implements ItemDAO {

	// declare database connection
	Connection con;

	public ItemDAOImpl() {

		try {
			con = MyConnection.getConnection().conn;
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// function to create item
	@Override
	public String create_item(Item item) {
		
		String status=null;
		if (item != null) {

			// prepare statement
			String sql = "insert into item values(?,?,?)";
			PreparedStatement stmt;
			try {
				stmt = con.prepareStatement(sql);

				stmt.setString(1, item.getItemname());
				stmt.setString(2, item.getCategory());
				stmt.setDouble(3, item.getSellingPrice());

				// execute query for create new item
				stmt.executeUpdate();
				status="Done";
			} catch (SQLException e) {
				status="Fail";
				e.printStackTrace();
			}

		}
		return status;
	}

	// update specific item
	@Override
	public void updateItem(String itemname) throws ItemNotFoundException {

		Item item = findItembyName(itemname);
		if (Objects.isNull(item)) {
			ItemNotFoundException exp = new ItemNotFoundException("No item found with name: " + itemname);
			throw exp;
		} else {

			// show detail item
			specificItem(itemname);
			System.out.println();
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter 1 to update name | Enter 2 to update category | Enter 3 to update sellingPrice");
			int choice = sc.nextInt();

			// asking admin to choose in order for update
			if (choice == 1) {
				Scanner sc1 = new Scanner(System.in);
				System.out.println("Enter name to update");
				String new_itemname = sc1.nextLine();

				Item update_item_name = new Item(new_itemname, item.getCategory(), item.getSellingPrice());

				updateItemDetail(itemname, update_item_name);

				System.out.println("Itemname succesfully updated");

			} else if (choice == 2) {

				Scanner sc2 = new Scanner(System.in);
				System.out.println("Enter category to update");
				String new_category = sc2.nextLine();

				Item update_cat = new Item(item.getItemname(), new_category, item.getSellingPrice());

				updateItemDetail(itemname, update_cat);

				System.out.println("Category succesfully updated");
			} else if (choice == 3) {

				Scanner sc3 = new Scanner(System.in);
				System.out.println("Enter selling price to update");
				double new_sellingprice = sc3.nextDouble();

				Item update_price = new Item(item.getItemname(), item.getCategory(), new_sellingprice);

				updateItemDetail(itemname, update_price);

				System.out.println("Selling price succesfully updated");
			} else {
				System.out.println("Invalid input!");
			}

		}

	}
	
	//update specific item
	public String updateItemDetail(String itemname, Item item) {
		
		String status = null;
		// prepare statement
		String sql = "UPDATE item SET itemname=?,category=?,sellingPrice=? WHERE itemname =?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, item.getItemname());
			stmt.setString(2, item.getCategory());
			stmt.setDouble(3, item.getSellingPrice());
			stmt.setString(4, itemname);

			stmt.executeUpdate();
			
			status = "Done";

		} catch (Exception e) {
			
			status = "Fail";
			e.printStackTrace();
		}
		
		return status;

	}
	
	//delete  specific item
	@Override
	public String deleteItem(String itemname) throws ItemNotFoundException {
		String status =null;
		Item item = findItembyName(itemname);
		if (Objects.isNull(item)) {
			ItemNotFoundException exp = new ItemNotFoundException("No item found with name: " + itemname);
			throw exp;
		} else {
			


			// prepare statement
			String sql = "DELETE FROM item WHERE itemname =?";
			PreparedStatement stmt;
			try {
				stmt = con.prepareStatement(sql);

				stmt.setString(1, item.getItemname());

				stmt.executeUpdate();
				
				status="Done";
				
				System.out.println(itemname +" succesfully deleted!");

			} catch (Exception e) {
				e.printStackTrace();
				
				status= "Fail";
			}

		}
		
		return status;

	}
	
	//function to search item
	@Override
	public void searchItem(String itemname) throws ItemNotFoundException {
		Item item = findItembyName(itemname);
		if (Objects.isNull(item)) {
			ItemNotFoundException exp = new ItemNotFoundException("No item found with name: " + itemname);
			throw exp;
		} else {
			
			// show detail item
			specificItem(itemname);
			
		}

	}
	
	//function to display specific item
	private void specificItem(String itemname) {
		List<Item> itms = findAllItem();

		String header = "Itemname" + "\t" + "\t" + "Category" + "\t" + "SellingPrice";
		System.out.println("============================================================");
		System.out.println(header);
		System.out.println("============================================================");

		itms.stream().filter(t -> t.getItemname().equals(itemname)).forEach(t -> {
			String row = t.getItemname() + "\t\t" + t.getCategory() + "\t\t" + t.getSellingPrice() + "\t\t";
			System.out.println(row);
		});

	}
	
	//function to display list of item
	@Override
	public void listAllItem() {
		List<Item> itms = findAllItem();

		String header = "Itemname" + "\t" + "\t" + "Category" + "\t" + "SellingPrice";
		System.out.println("============================================================");
		System.out.println(header);
		System.out.println("============================================================");

		itms.stream().forEach(t -> {
			String row = t.getItemname() + "\t\t" + t.getCategory() + "\t\t" + t.getSellingPrice() + "\t\t";
			System.out.println(row);
		});

	}
	
	//function to find all item
	public List<Item> findAllItem() {

		List<Item> items = new ArrayList<>();

		try {
			String sql = "SELECT * FROM item";

			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Item item = mapToItem(rs);
				items.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;

	}

	// function to map all item to Item object
	private Item mapToItem(ResultSet rs) throws SQLException {

		String itemname = rs.getString("itemname");
		String category = rs.getString("category");
		double sellingPrice = rs.getDouble("sellingPrice");

		Item item = new Item(itemname, category, sellingPrice);

		return item;
	}

	// function to find item
	public Item findItembyName(String itemname) {
		List<Item> itms = findAllItem();

		return itms.stream().filter(t -> t != null && t.getItemname().equals(itemname)).findFirst().orElse(null);
	}

}
