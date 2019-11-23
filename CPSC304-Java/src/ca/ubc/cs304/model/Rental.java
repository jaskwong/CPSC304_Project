package ca.ubc.cs304.model;
import java.sql.Timestamp;

/**
 * The intent for this class is to update/store information about a single rental
 */
public class Rental {
    private final int rid;
    private final String v_license;
    private final int cellphone;
    private final Timestamp fromDate;
    private final Timestamp toDate;
    private final int odomoter;
    private final String cardName;
    private final int cardNo;
    private final Timestamp expDate;
    private final int confNo;

    public Rental(int rid, String v_license, int cellphone, Timestamp fromDate, Timestamp toDate, int odomoter, String cardName, int cardNo, Timestamp expDate, int confNo) {
        this.rid = rid;
        this.v_license = v_license;
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

    public String getV_license() {
        return v_license;
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

    public Timestamp getExpDate() {
        return expDate;
    }

    public int getConfNo() {
        return confNo;
    }
}
