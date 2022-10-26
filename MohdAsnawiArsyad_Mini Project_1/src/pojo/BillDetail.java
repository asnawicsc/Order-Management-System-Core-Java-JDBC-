package pojo;

public class BillDetail {
	// all user attribute
	private int billID;
	private String itemname;
	private int orderQty;
	private double bdPrice;

	// getter setter
	public int getBillID() {
		return billID;
	}

	public void setBillID(int billID) {
		this.billID = billID;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItem(String itemname) {
		this.itemname = itemname;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public double getBdPrice() {
		return bdPrice;
	}

	public void setBdPrice(double bdPrice) {
		this.bdPrice = bdPrice;
	}
	
	//constructor to store bill into temporary cart 
	public BillDetail(double bdPrice,String itemname, int orderQty) {

		super();
		
		this.bdPrice = bdPrice;
		this.itemname = itemname;
		this.orderQty = orderQty;
	}
	
	//constructor to store bill after id being created
	public BillDetail(int billID,String itemname, int orderQty,double bdPrice) {

		super();
		this.billID = billID;
		this.bdPrice = bdPrice;
		this.itemname = itemname;
		this.orderQty = orderQty;
	}

}
