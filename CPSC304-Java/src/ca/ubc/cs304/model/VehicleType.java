package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single vehicle
 */
public class VehicleType {
	private final String vtname;
	private final String features;
	private int wrate;
	private int drate;
	private int hrate;
	private int wirate;
	private int dirate;
	private int hirate;
	private int krate;

	public VehicleType(String vtname, String features, int wrate, int drate, int hrate, int wirate, int dirate, int hirate, int krate) {
		this.vtname = vtname;
		this.features = features;
		this.wrate = wrate;
		this.drate = drate;
		this.hrate = hrate;
		this.wirate = wirate;
		this.dirate = dirate;
		this.hirate = hirate;
		this.krate = krate;
	}


	public String getVtname() {return vtname;}

	public String getFeatures() {
		return features;
	}

	public int getWrate() {
		return wrate;
	}

	public int getDrate() {
		return drate;
	}

	public int getHrate() {
		return hrate;
	}

	public int getWirate() {
		return wirate;
	}

	public int getDirate() {
		return dirate;
	}

	public int getHirate() {
		return hirate;
	}

	public int getKrate() {
		return krate;
	}
}
