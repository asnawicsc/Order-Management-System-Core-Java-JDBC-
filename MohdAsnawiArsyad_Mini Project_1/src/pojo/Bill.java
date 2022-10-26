package pojo;

import java.util.Date;

public class Bill {
	// all user attribute
	private int billID;
	private Date billDate;
	private double total;
	private String username;

	// getter setter
	public int getBillID() {
		return billID;
	}

	public void setBillID(int billID) {
		this.billID = billID;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	// constructor
	public Bill(int billID, Date billDate, double total, String username) {
		super();
		this.billID = billID;
		this.billDate = billDate;
		this.total = total;
		this.username = username;
	}

}
