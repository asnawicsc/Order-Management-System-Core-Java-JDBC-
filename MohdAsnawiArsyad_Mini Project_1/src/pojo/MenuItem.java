package pojo;

public class MenuItem {
	// all user attribute
	private String menuName;
	private String itemname;
	private String category;
	private double sellingPrice;

	// getter setter
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

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

	// constructor
	public MenuItem(String menuName, String itemname, String category, double sellingPrice) {
		super();
		this.menuName = menuName;
		this.itemname = itemname;
		this.category = category;
		this.sellingPrice = sellingPrice;
	}

}
