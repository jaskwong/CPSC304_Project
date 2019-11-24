package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.*;

import java.sql.Timestamp;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the
 * controller class (in this case Bank).
 *
 * TerminalTransactions calls the methods that we have listed below but
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {


    void insertReservation(Reservation reso);

    void terminalTransactionsFinished();

    void makeCustomer(Customer cust);

    boolean customerExists(int dlicense);

    boolean vehicleTypeAvailable(String vt_name);

    boolean confNumberExists(int confNumber);

    boolean rentalExists(int rid);

    boolean returnExists(int rid);

    void makeReturn(Ret ret);

    float getInitOdom(int rid);

    Timestamp getRentalFromDateFromRid(int rid);

    VehicleType getVtFromRid(int rid);

    void viewReservations();

    void viewAvailableVehicles();

    void viewCarnum(String vtname, int startYear, int endYear, String location);

    void viewCardetail(String vtname, int startYear, int endYear, String location);

    void viewRentalReport(String location, String city, Timestamp sqlDate);

    void viewReturnReport(String location, String city, Timestamp sqlDate);

    void setRented(String vlicense);

    Rental  makeRentalFromReservation(int confNo, String v_license, String cardName, int cardNo, Timestamp exp);

    void makeRental(Rental rental);

    String getAvailableVlicenseOfType(String vt);

    String getVtFromRes(int confNumber);

    Timestamp getFromDateFromRes(int confNumber);

    Timestamp getToDateFromRes(int confNumber);

    int getDlicenseFromRes(int confNumber);

    int getPhoneFromCustomer(int dlicense);

    int getOdomFromVehicle(String vlicense);
}
