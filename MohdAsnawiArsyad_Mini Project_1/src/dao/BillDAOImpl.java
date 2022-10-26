package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import connection.MyConnection;
import exceptions.ItemNotFoundException;
import pojo.Bill;
import pojo.BillDetail;
import pojo.Item;
import pojo.User;

public class BillDAOImpl implements BillDAO {

	List<BillDetail> bd = new ArrayList<>();

	// declare database connection
	Connection con;

	public BillDAOImpl() {

		try {
			con = MyConnection.getConnection().conn;
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// function to generate today bill
	@Override
	public void generateTodayBills() {

		// default time zone
		ZoneId defaultZoneId = ZoneId.systemDefault();

		// creating the instance of LocalDate using the day, month, year info
		LocalDate localDate = LocalDate.now();

		// local date + atStartOfDay() + default time zone + toInstant() = Date
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		java.util.Date util_StartDate = date;
		java.sql.Date sql_StartDate = new java.sql.Date(util_StartDate.getTime());

		List<Bill> bls = findAllBill();
		if (bls.size() != 0) {

			for (Bill i : bls) {
				if (i != null) {

					System.out.println();

					if (i.getBillDate().equals(sql_StartDate)) {
						System.out.println();
						System.out.println();
						getBill(i.getBillID());
						System.out.println();
						listBillDetail(i.getBillID());

						System.out.println();
						System.out.println();
					}

				}

			}
		} else {
			System.out.println("No bill being created yet!");
		}

	}

	// function to generate first date of month
	public static Date getFirstDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// function to generate all bill base on current month
	@Override
	public void generateMonthBills() {
		Date today = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		Date firstDay = getFirstDateOfMonth(new Date());

		Date lastDayOfMonth = calendar.getTime();

		List<Bill> bls = findAllBillThisMonth(firstDay, lastDayOfMonth);
		


		if (bls.size() != 0) {
			
			double total_month = bls.stream().mapToDouble(t -> t.getTotal()).sum();
			System.out.println();
			System.out.println("Total sales for this month: "+total_month);
			for (Bill i : bls) {
				if (i != null) {

					System.out.println();
					System.out.println();
					getBill(i.getBillID());
					System.out.println();
					listBillDetail(i.getBillID());

					System.out.println();
					System.out.println();

				}

			}
		} else {

			System.out.println("No bill being created yet!");
		}

	}

	// function to put user order in temporary cart
	@Override
	public void put_order_in_cart(User user, ItemDAO itemDAO, String item_name, int qty) throws ItemNotFoundException {

		Item item = itemDAO.findItembyName(item_name);
		if (Objects.isNull(item)) {
			ItemNotFoundException exp = new ItemNotFoundException("No item found with name: " + item_name);
			throw exp;
		} else {

			double item_price = item.getSellingPrice();

			double bd_price = item_price * qty;

			// insert item into cart
			BillDetail new_bd = new BillDetail(bd_price, item_name, qty);
			bd.add(new_bd);

			System.out.println("Item succesfully added into cart");

		}

	}

	// function to fill all bill on current month
	public List<Bill> findAllBillThisMonth(Date firstDay, Date lastDayOfMonth) {

		List<Bill> bills = new ArrayList<>();
		
	

		java.util.Date util_StartDate = firstDay;
		java.sql.Date sql_StartDate = new java.sql.Date(util_StartDate.getTime());

		java.util.Date util_EndDate = lastDayOfMonth;
		java.sql.Date sql_EndtDate = new java.sql.Date(util_EndDate.getTime());

		try {
			String sql = "SELECT * FROM bill Where billDate >=? and billDate <=?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setDate(1, sql_StartDate);
			stmt.setDate(2, sql_EndtDate);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Bill bill = mapToBill(rs);
				bills.add(bill);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bills;

	}

	// function to find all bill
	public List<Bill> findAllBill() {

		List<Bill> bills = new ArrayList<>();

		try {
			String sql = "SELECT * FROM bill";

			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Bill bill = mapToBill(rs);
				bills.add(bill);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bills;

	}

	// function to map all item to Item object
	private Bill mapToBill(ResultSet rs) throws SQLException {

		int billID = rs.getInt("billID");
		Date billDate = rs.getDate("billDate");
		double total = rs.getDouble("total");
		String username = rs.getString("username");

		Bill bill = new Bill(billID, billDate, total, username);

		return bill;
	}

	// function to find all bill detail based on temp cart
	public List<BillDetail> findAllBillDetail() {

		List<BillDetail> bd = new ArrayList<>();

		try {
			String sql = "SELECT * FROM billdetail";

			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				BillDetail bil = mapToBillDetail(rs);
				bd.add(bil);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bd;

	}

	// function to map bill detail
	private BillDetail mapToBillDetail(ResultSet rs) throws SQLException {

		int billID = rs.getInt("billID");
		String itemname = rs.getString("itemname");
		int orderQty = rs.getInt("orderQty");
		double bdPrice = rs.getDouble("bdPrice");

		BillDetail bd = new BillDetail(billID, itemname, orderQty, bdPrice);

		return bd;
	}

	public List<BillDetail> checkCurrentCart() {

		return bd;

	}

	// function create final bill
	@Override
	public int createFinalBill(User user) {

		int billID = 0;

		double total = bd.stream().mapToDouble(t -> t.getBdPrice()).sum();

		// default time zone
		ZoneId defaultZoneId = ZoneId.systemDefault();

		// creating the instance of LocalDate using the day, month, year info
		LocalDate localDate = LocalDate.now();

		// local date + atStartOfDay() + default time zone + toInstant() = Date
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		java.util.Date util_StartDate = date;
		java.sql.Date sql_StartDate = new java.sql.Date(util_StartDate.getTime());

		// prepare statement
		String sql = "insert into bill(billDate,total,username) values(?,?,?)";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setDate(1, sql_StartDate);
			stmt.setDouble(2, total);
			stmt.setString(3, user.getUsername());

			// execute query for create new item
			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();

			if (rs != null && rs.next()) {
				createBillDetail(rs.getInt(1));
				billID = rs.getInt(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return billID;

	}

	// function to create bill detail
	private void createBillDetail(int j) throws SQLException {

		for (BillDetail i : bd) {
			if (i != null) {

				// prepare statement
				String sql = "insert into billdetail(billID,itemname,orderQty,bdPrice) values(?,?,?,?)";
				PreparedStatement stmt2;
				try {
					stmt2 = con.prepareStatement(sql);

					stmt2.setInt(1, j);
					stmt2.setString(2, i.getItemname());
					stmt2.setInt(3, i.getOrderQty());
					stmt2.setDouble(4, i.getBdPrice());

					// execute query for create new item
					stmt2.executeUpdate();

				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	// function to display bill
	@Override
	public void getBill(int billid) {
		List<Bill> bls = findAllBill();

		String header = "Bill ID" + "\t\t" + "Bill Date" + "\t\t" + "Total" + "\t\t" + "Username";
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(header);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		bls.stream().filter(t -> t.getBillID() == billid).forEach(t -> {
			String row = t.getBillID() + "\t\t" + t.getBillDate() + "\t\t" + t.getTotal() + "\t\t" + t.getUsername()
					+ "\t\t";
			System.out.println(row);
		});

	}

	// function to display list bill detail
	@Override
	public void listBillDetail(int billid) {
		List<BillDetail> bd = findAllBillDetail();

		String header = "Bill ID" + "\t\t" + "Item Name" + "\t\t" + "Quantity" + "\t\t" + "Price";
		System.out.println("=======================================================================");
		System.out.println(header);
		System.out.println("=======================================================================");

		bd.stream().filter(t -> t.getBillID() == billid).forEach(t -> {
			String row = t.getBillID() + "\t\t" + t.getItemname() + "\t\t" + t.getOrderQty() + "\t\t" + t.getBdPrice()
					+ "\t\t";
			System.out.println(row);
		});

	}

}
