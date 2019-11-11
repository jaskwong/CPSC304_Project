package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single vehicle
 */
public class Customers {
	private int cellphone;
	private final String name;
	private String address;
	private final int dlicense;

	public Customers(int cellphone, String name, String address, int dlicense) {
		this.cellphone = cellphone;
		this.name = name;
		this.address = address;
		this.dlicense = dlicense;
	}

	public int getCellphone() {
		return cellphone;
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
}
