package pojo;

public class Item {

	// all item attribute
	private String itemname;
	private String category;
	private double sellingPrice;

	// getter and setter
	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Item(String itemname, String category, double sellingPrice) {
		super();
		this.itemname = itemname;
		this.category = category;
		this.sellingPrice = sellingPrice;
	}

	// to string function
	@Override
	public String toString() {
		return "Item [itemname=" + itemname + ", category=" + category + "]";
	}

}
