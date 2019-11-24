package ca.ubc.cs304.model;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The intent for this class is to update/store information about a single vehiclye type
 */
public class VehicleType {
	private final String vtname;
	private final String features;
	private float wrate;
	private float drate;
	private float hrate;
	private float wirate;
	private float dirate;
	private float hirate;
	private float krate;

	public VehicleType(String vtname, String features, float wrate, float drate, float hrate, float wirate, float dirate, float hirate, float krate) {
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

	public float getWrate() {
		return wrate;
	}

	public float getDrate() {
		return drate;
	}

	public float getHrate() {
		return hrate;
	}

	public float getWirate() {
		return wirate;
	}

	public float getDirate() {
		return dirate;
	}

	public float getHirate() {
		return hirate;
	}

	public float getKrate() {
		return krate;
	}

	public void setDirate(int dirate) {
		this.dirate = dirate;
	}

	public void setDrate(int drate) {
		this.drate = drate;
	}

	public void setHirate(int hirate) {
		this.hirate = hirate;
	}

	public void setHrate(int hrate) {
		this.hrate = hrate;
	}

	public void setKrate(int krate) {
		this.krate = krate;
	}

	public void setWirate(int wirate) {
		this.wirate = wirate;
	}

	public void setWrate(int wrate) {
		this.wrate = wrate;
	}

	public float calculateValue(Timestamp rent, Timestamp ret, int dist) {
        Date rentDate = new Date(rent.getTime());
        Date retDate = new Date(ret.getTime());
        long duration = retDate.getTime()-rentDate.getTime();

        double weeks = Math.floor(TimeUnit.MILLISECONDS.toDays(duration)/7);
        double days = Math.floor((TimeUnit.MILLISECONDS.toDays(duration) % 7));
        double hours = Math.floor(TimeUnit.MILLISECONDS.toHours(duration) % 24);

        double val = (weeks *(wirate + wrate) + days * (drate + dirate) + hours * (hrate + hirate) + krate * dist);
        System.out.println("Price calculation: (" + wirate + " + " + wrate + ") * " + weeks + " weeks + ("
                + dirate + " + " + drate + ") * " + days + " days + ("
                + hirate + " + " + hrate + ") * " + hours + " hours + "
                + krate + " * " + dist + " km");
        return (float) val;
    }
}
