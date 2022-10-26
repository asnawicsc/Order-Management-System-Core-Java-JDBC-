package dao;

import java.util.List;

import exceptions.ItemNotFoundException;
import pojo.BillDetail;
import pojo.User;

public interface BillDAO {
	
	//function to generate all today bill and bill detail
	void generateTodayBills();
	
	//function to generate all current month bill and bill detail
	void generateMonthBills();
	
	//function to check current cart
	List<BillDetail> checkCurrentCart();
	
	//function to put order in cart
	void put_order_in_cart(User user, ItemDAO itemDAO, String item_name, int qty) throws ItemNotFoundException;
	
	//function to create final bill and return billID
	int createFinalBill(User user);
	
	//function to get bill base on billID
	void getBill(int billid);
	
	//function to get bill detail base on billID
	void listBillDetail(int billid);

}
