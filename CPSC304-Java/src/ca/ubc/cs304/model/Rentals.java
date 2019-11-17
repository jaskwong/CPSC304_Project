package ca.ubc.cs304.model;
import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single rental
 */
public class Rentals {
	private final int rid;
	private final int vid;
	private final int cellphone;
	private final Date fromDate;
	private final Time fromTime;
	private final Date toDate;
	private final Time toTime;
	private final int odomoter;
	private final String cardName;
	private final int cardNo;
	private final Date expDate;
	private final int confNo;

	public Rentals(int rid, int vid, int cellphone, Date fromDate, Time fromTime, Date toDate, Time toTime, int odomoter, String cardName, int cardNo, Date expDate, int confNo) {
		this.rid = rid;
		this.vid = vid;
		this.cellphone = cellphone;
		this.fromDate = fromDate;
		this.fromTime = fromTime;
		this.toDate = toDate;
		this.toTime = toTime;
		this.odomoter = odomoter;
		this.cardName = cardName;
		this.cardNo = cardNo;
		this.expDate = expDate;
		this.confNo = confNo;
	}

	public int getRid() {
		return rid;
	}

	public int getVid() {
		return vid;
	}

	public int getCellphone() {
		return cellphone;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Time getFromTime() {
		return fromTime;
	}

	public Date getToDate() {
		return toDate;
	}

	public Time getToTime() {
		return toTime;
	}

	public int getOdomoter() {
		return odomoter;
	}

	public String getCardName() {
		return cardName;
	}

	public int getCardNo() {
		return cardNo;
	}

	public Date getExpDate() {
		return expDate;
	}

	public int getConfNo() {
		return confNo;
	}
}
