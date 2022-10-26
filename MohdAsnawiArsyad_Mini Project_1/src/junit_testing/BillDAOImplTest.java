package junit_testing;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import dao.BillDAOImpl;
import dao.ItemDAOImpl;
import exceptions.ItemNotFoundException;
import pojo.Bill;
import pojo.BillDetail;
import pojo.User;

public class BillDAOImplTest {

	List<BillDetail> bd = new ArrayList<>();

	BillDAOImpl billDAO = new BillDAOImpl();

	ItemDAOImpl itemDAO = new ItemDAOImpl();

	// function to put user order in temporary cart
	@Test
	public void put_order_in_cart() throws ItemNotFoundException {

		String item_name = "Carrot Cake";

		double selling_price = 12.7;

		int qty = 7;

		double item_price = selling_price;

		double bd_price = item_price * qty;

		// insert item into cart
		BillDetail new_bd = new BillDetail(bd_price, item_name, qty);
		bd.add(new_bd);

		assertEquals(bd.size(), 1);

	}

	// function to generate today bill
	@Test
	public void generateTodayBills() {

		// default time zone
		ZoneId defaultZoneId = ZoneId.systemDefault();

		// creating the instance of LocalDate using the day, month, year info
		LocalDate localDate = LocalDate.now();

		// local date + atStartOfDay() + default time zone + toInstant() = Date
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		java.util.Date util_StartDate = date;
		java.sql.Date sql_StartDate = new java.sql.Date(util_StartDate.getTime());

		List<Bill> bls = billDAO.findAllBill();

		assertEquals(bls.size(), 0);

	}

	// function to find all bill
	@Test
	public void findAllBill() {

		List<Bill> bls = billDAO.findAllBill();

		assertEquals(bls.size(), 0);

	}

	// function to find all bill detail based on temp cart
	public void findAllBillDetail() {

		List<BillDetail> bls = billDAO.findAllBillDetail();

		assertNotEquals(bls.size(), 0);

	}

	public List<BillDetail> checkCurrentCart() {

		return bd;

	}

	// function create final bill
	@Test
	public void createFinalBill() {

		User user = new User("ahmad", "ahmad@gmail.com", "abc111", "User");

		assertNotEquals(bd.size(), 1);

	}

	// function to create bill detail
	public void createBillDetail() throws SQLException {

		assertNotEquals(bd.size(), 1);

	}

}
