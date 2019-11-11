package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single vehicle
 */
public class Returns {
	private final String vlicense;
	private final String make;
	private final String model;
	private final int year;
	private final String color;
	private int odomoter;
	private String status;
	private final String vtname;
	private String location;
	private String city;

	public Returns(String vlicense, String make, String model, int year, String color, int odomoter, String status, String vtname, String location, String city) {
		this.vlicense = vlicense;
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
		this.odomoter = odomoter;
		this.status = status;
		this.vtname = vtname;
		this.location = location;
		this.city = city;
	}

	public String getVlicense() {return vlicense;}

	public String getMake() {return make;}

	public String getModel() {return model;}

	public int getYear() {return year;}

	public String getColor() {return color;}

	public int getOdomoter() {return odomoter;}

	public String getStatus() {return status;}

	public String getVtname() {return vtname;}

	public String getLocation() {return location;}

	public String getCity() {return city;}

}