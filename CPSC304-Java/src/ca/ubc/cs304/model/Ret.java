package ca.ubc.cs304.model;
import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single vehicle
 */
public class Ret {
	private final int rid;
	private final Date date;
	private final Time time;
	private final int odomoter;
	private final boolean fulltank;
	private final float value;

	public Ret(int rid, Date date, Time time, int odomoter, boolean fulltank, float value) {
		this.rid = rid;
		this.date = date;
		this.time = time;
		this.odomoter = odomoter;
		this.fulltank = fulltank;
		this.value = value;
	}

	public int getRid() {
		return rid;
	}

	public Date getDate() {
		return date;
	}

	public Time getTime() {
		return time;
	}

	public int getOdomoter() {
		return odomoter;
	}

	public boolean isFulltank() {
		return fulltank;
	}

	public float getValue() {
		return value;
	}
}
