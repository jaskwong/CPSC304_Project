package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single customer
 */
public class Customer {
	private int cellphone;
	private String name;
	private String address;
	private final int dlicense;

	public Customer(int cellphone, String name, String address, int dlicense) {
		this.cellphone = cellphone;
		this.name = name;
		this.address = address;
		this.dlicense = dlicense;
	}

	public int getCellphone() {
		return  cellphone;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getDlicense() {
		return dlicense;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCellphone(int cellphone) {
		this.cellphone = cellphone;
	}

	public void setName(String name) {
		this.name = name;
	}
}
