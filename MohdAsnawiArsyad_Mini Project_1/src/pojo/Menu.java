package pojo;

public class Menu {
	// all user attribute
	private String menuName;
	private String menuDesc;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	
	// constructor
	public Menu(String menuName, String menuDesc) {
		super();
		this.menuName = menuName;
		this.menuDesc = menuDesc;
	}

}
