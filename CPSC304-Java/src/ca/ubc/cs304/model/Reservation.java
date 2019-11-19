package ca.ubc.cs304.model;
import java.sql.Timestamp;

/**
 * The intent for this class is to update/store information about a single reservation
 */
public class Reservation {
	private final int confNo;
	private final String vtname;
	private final int customer_dlicense;
	private final Timestamp fromDate;
	private final Timestamp toDate;


	public Reservation(int confNo, String vtname, int dlicense, Timestamp fromDate, Timestamp toDate) {
		this.confNo = confNo;
		this.vtname = vtname;
		this.customer_dlicense = dlicense;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

    public int getConfNo() {return confNo;}

    public String getVtname() {return vtname;}

    public int getCustomer_dlicense() {return customer_dlicense;}

    public Timestamp getFromDate() {return fromDate;}

    public Timestamp getToDate() {return  toDate;}

}
