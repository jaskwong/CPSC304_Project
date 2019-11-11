package ca.ubc.cs304.model;
import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single reservation
 */
public class Reservations {
	private final int confNo;
	private final String vtname;
	private final int cellphone;
	private final Date fromDate;
	private final Time fromTime;
	private final Date toDate;
	private final Time toTime;


	public Reservations(int confNo, String vtname, int cellphone, Date fromDate, Time fromTime, Date toDate, Time toTime) {
		this.confNo = confNo;
		this.vtname = vtname;
		this.cellphone = cellphone;
		this.fromDate = fromDate;
		this.fromTime = fromTime;
		this.toDate = toDate;
		this.toTime = toTime;
	}

    public int getConfNo() {return confNo;}

    public String getVtname() {return vtname;}

    public int getCellphone() {return cellphone;}

    public Date getFromDate() {return fromDate;}

    public Time getFromTime() {return fromTime;}

    public Date getToDate() {return  toDate;}

    public Time getToTime() {return toTime;}

}
