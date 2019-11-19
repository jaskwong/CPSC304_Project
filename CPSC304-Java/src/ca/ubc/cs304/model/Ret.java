package ca.ubc.cs304.model;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * The intent for this class is to update/store information about a single return
 */
public class Ret {
	private final int rid;
	private final Timestamp time;
	private final int odomoter;
	private final boolean fulltank;
	private final float value;

	public Ret(int rid, Timestamp time, int odomoter, boolean fulltank, float value) {
		this.rid = rid;
		this.time = time;
		this.odomoter = odomoter;
		this.fulltank = fulltank;
		this.value = value;
	}

	public int getRid() {
		return rid;
	}

	public Timestamp getTime() {
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
