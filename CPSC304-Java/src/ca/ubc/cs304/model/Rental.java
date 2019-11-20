package ca.ubc.cs304.model;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * The intent for this class is to update/store information about a single rental
 */
public class Rental {
	private final int rid;
	private final int vid;
	private final int cellphone;
	private final Timestamp fromDate;
	private final Timestamp toDate;
	private final int odomoter;
	private final String cardName;
	private final int cardNo;
	private final Date expDate;
	private final int confNo;

	public Rental(int rid, int vid, int cellphone, Timestamp fromDate, Timestamp toDate, int odomoter, String cardName, int cardNo, Date expDate, int confNo) {
		this.rid = rid;
		this.vid = vid;
		this.cellphone = cellphone;
		this.fromDate = fromDate;
		this.toDate = toDate;
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

	public Timestamp getFromDate() {
		return fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
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
