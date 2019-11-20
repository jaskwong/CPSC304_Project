package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.model.Ret;

import java.sql.SQLException;

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
//	public void deleteBranch(int branchId);
//	public void insertBranch(Vehicle model);
//	public void showBranch();
//	public void updateBranch(int branchId, String name);

	public void insertReservation(Reservation reso);
	
	public void terminalTransactionsFinished();

	public void insertCustomer(Customer cust);

	public boolean customerExists(int dlicense);

    public void insertReturn(Ret ret);

    public boolean vehicleTypeAvailable(String vt_name);

	public boolean confNumberExists(int confNumber);
}
