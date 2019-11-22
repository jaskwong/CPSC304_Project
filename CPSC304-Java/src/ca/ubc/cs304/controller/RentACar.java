package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.model.Rental;
import ca.ubc.cs304.model.Ret;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.model.VehicleType;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;

import java.sql.Timestamp;
import java.text.ParseException;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class RentACar implements LoginWindowDelegate, TerminalTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public RentACar() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}
	
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */ 
	public void login(String username, String password) throws ParseException {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			TerminalTransactions transaction = new TerminalTransactions();
			transaction.showMainMenu(this);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}
	
	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Insert a branch with the given info
	 */
//    public void insertBranch(Vehicle model) {
//    	dbHandler.insertBranch(model);
//    }

	/**
	 *
	 * Mark a vehicle as rented for a given time period.
	 *
	 */
	public void setRented(String vlicense) {
		dbHandler.setRented(vlicense);
	}

	public void makeRental(Rental rental) {
		dbHandler.makeRental(rental);
	}

	public void deleteRental(int rid) {
		dbHandler.deleteRental(rid);
	}

	public void makeVehicle(Vehicle vehicle) {
		dbHandler.makeVehicle(vehicle);
	}

	public void deleteVehicle(String vlicense) {
		dbHandler.deleteVehicle(vlicense);
	}

	public void makeReturn (Ret ret) {
		dbHandler.makeRet(ret);
	}

	public void deleteReturn (int rid) {
		dbHandler.deleteRet(rid);
	}

	public void insertReservation(Reservation reso) {
		dbHandler.makeReservation(reso);
	}

	public float getInitOdom(int rid) {
	    return dbHandler.getInitOdom(rid);
    }

    public VehicleType getVtFromRid(int rid) {
	    return dbHandler.getVtFromRid(rid);
    }

    public Timestamp getRentalFromDateFromRid(int rid) {
	    return dbHandler.getRentalFromDateFromRid(rid);
    }

    public boolean returnExists(int rid) { return dbHandler.returnExists(rid); }

	public void makeCustomer(Customer cust) { dbHandler.makeCustomer(cust);}

	public boolean customerExists(int dlicense) { return dbHandler.customerExists(dlicense);};

	public boolean vehicleTypeAvailable(String vt_name) { return dbHandler.vehicleTypeAvailable(vt_name);}

	public boolean confNumberExists(int confNumber) {return dbHandler.confNumberExists(confNumber);}

	public void viewReservations() {dbHandler.viewReservations();}

	public void viewAvailableVehicles() {dbHandler.viewAvailableVehicles();}

	public boolean rentalExists(int rid){ return dbHandler.rentalExists(rid); }


	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Delete branch with given branch ID.
	 */ 
    public void deleteBranch(int branchId) {
    	dbHandler.deleteBranch(branchId);
    }
    
    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Update the branch name for a specific ID
	 */

//    public void updateBranch(int branchId, String name) {
//    	dbHandler.updateBranch(branchId, name);
//    }
//
//    /**
//	 * TermainalTransactionsDelegate Implementation
//	 *
//	 * Displays information about varies bank branches.
//	 */
//    public void showBranch() {
//    	Vehicle[] models = dbHandler.getBranchInfo();
//
//    	for (int i = 0; i < models.length; i++) {
//    		Vehicle model = models[i];
//
//    		// simplified output formatting; truncation may occur
//    		System.out.printf("%-10.10s", model.getId());
//    		System.out.printf("%-20.20s", model.getName());
//    		if (model.getAddress() == null) {
//    			System.out.printf("%-20.20s", " ");
//    		} else {
//    			System.out.printf("%-20.20s", model.getAddress());
//    		}
//    		System.out.printf("%-15.15s", model.getCity());
//    		if (model.getPhoneNumber() == 0) {
//    			System.out.printf("%-15.15s", " ");
//    		} else {
//    			System.out.printf("%-15.15s", model.getPhoneNumber());
//    		}
//
//    		System.out.println();
//    	}
//    }
	
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing so we are cleaning up the connection since it's no longer needed.
     */ 
    public void terminalTransactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	
    	System.exit(0);
    }
    
	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		RentACar store = new RentACar();
		store.start();
	}
}
