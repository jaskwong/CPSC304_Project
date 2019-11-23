package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;
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

    public void viewCarnum(String vtname, int startYear, int endYear, String location) {dbHandler.viewVehiclenum(vtname, startYear, endYear, location);};

    public boolean rentalExists(int rid){ return dbHandler.rentalExists(rid); }

    public String getAvailableVlicenseOfType(String vt) { return dbHandler.getAvailableOfType(vt);}

    public String getVtFromRes(int confNumber) {return dbHandler.getVtFromRes(confNumber);}

    public Timestamp getFromDateFromRes(int confNumber) {return dbHandler.getFromDateFromRes(confNumber);}

    public Timestamp getToDateFromRes(int confNumber) {return dbHandler.getToDateFromRes(confNumber);}

    public int getDlicenseFromRes(int confNumber) {return dbHandler.getDLicenseFromRes(confNumber);}

    public int getPhoneFromCustomer(int dlicense) {return dbHandler.getPhoneFromCustomer(dlicense);}


    public int getOdomFromVehicle(String vlicense) {return dbHandler.getOdomFromVehicle(vlicense);}




    public void viewCardetail(String vtname, int startYear, int endYear, String location) {
        Vehicle[] models = dbHandler.getCarInfom(vtname, startYear, endYear, location);

        for (int i = 0; i < models.length; i++) {
            Vehicle model = models[i];
            System.out.printf("%-20.20s", "vehicle license");
            System.out.printf("%-20.20s", "vehicle make");
            System.out.printf("%-20.20s", "vehicle model");
            System.out.printf("%-20.20s", "vehicle year");
            System.out.printf("%-20.20s", "vehicle color");
            System.out.printf("%-20.20s", "vehicle odometer");
            System.out.printf("%-20.20s", "vehicle status");
            System.out.printf("%-20.20s", "vehicle type");
            System.out.printf("%-20.20s", "vehicle location");
            System.out.printf("%-20.20s", "vehicle city");
            System.out.println();
            System.out.printf("%-20.20s", model.getVlicense());
            if (model.getMake() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getMake());
            }
            if (model.getModel() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getModel());
            }
            if (model.getYear() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getYear());
            }
            if (model.getColor() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getColor());
            }
            if (model.getOdomoter() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getOdomoter());
            }
            if (model.getStatus() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getStatus());
            }
            if (model.getVtname() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getVtname());
            }
            if (model.getLocation() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getLocation());
            }
            if (model.getCity() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getCity());
            }
            System.out.println();
        }
    }


    public void viewRentalReport(String location, String city, Timestamp sqlDate) {
        dbHandler.viewRtalRptNum(location, city, sqlDate);
        Rental[] models = dbHandler.getRtalRptInfo(location, city, sqlDate);
        String s;
        System.out.printf("%-30.30s", "Date");
        System.out.printf("%-20.20s", "rental id");
        System.out.printf("%-20.20s", "vehicle license");
        System.out.printf("%-20.20s", "driver license");
        System.out.printf("%-30.30s", "from date time");
        System.out.printf("%-30.30s", "to date time");
        System.out.printf("%-20.20s", "odometer");
        System.out.printf("%-20.20s", "card name");
        System.out.printf("%-20.20s", "card num");
        System.out.printf("%-30.30s", "card exp date");
        System.out.printf("%-20.20s", "reservation conNum");
        System.out.printf("%-20.20s", "branch");
        System.out.println();
        for (int i = 0; i < models.length; i++) {
            Rental model = models[i];
            s = sqlDate.toString().substring(0, 10);
            System.out.printf("%-30.30s", s);
            System.out.printf("%-20.20s", model.getRid());
            System.out.printf("%-20.20s", model.getV_license());
            System.out.printf("%-20.20s", model.getCellphone());
            if (model.getFromDate() == null) {
                System.out.printf("%-30.30s", " ");
            } else {
                System.out.printf("%-30.30s", model.getFromDate());
            }
            if (model.getToDate() == null) {
                System.out.printf("%-30.30s", " ");
            } else {
                System.out.printf("%-30.30s", model.getToDate());
            }
            if (model.getOdomoter() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getOdomoter());
            }
            if (model.getCardName() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getCardName());
            }
            if (model.getCardNo() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getCardNo());
            }
            if (model.getExpDate() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-30.30s", model.getExpDate());
            }
            if (model.getConfNo() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getConfNo());
            }
            System.out.printf("%-5.5s", location);
            if (city.equals("all")) {
            } else {
                System.out.printf("%-20.20s", city);
            }
            System.out.println();
        }
    }

    public void viewReturnReport(String location, String city, Timestamp sqlDate) {
        dbHandler.viewRtnRptNum(location, city, sqlDate);
        Ret[] models = dbHandler.getRtnRptInfo(location, city, sqlDate);
        String s;
        System.out.printf("%-30.30s", "Date");
        System.out.printf("%-20.20s", "rental id");
        System.out.printf("%-30.30s", "return time");
        System.out.printf("%-20.20s", "return odometer");
        System.out.printf("%-20.20s", "is full tank");
        System.out.printf("%-20.20s", "value");
        System.out.printf("%-20.20s", "branch");
        System.out.println();
        for (int i = 0; i < models.length; i++) {
            Ret model = models[i];
            s = sqlDate.toString().substring(0, 10);
            System.out.printf("%-30.30s", s);
            System.out.printf("%-20.20s", model.getRid());
            if (model.getTime() == null) {
                System.out.printf("%-30.30s", " ");
            } else {
                System.out.printf("%-30.30s", model.getTime());
            }
            if (model.getOdomoter() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getOdomoter());
            }
            System.out.printf("%-20.20s", model.isFulltank());
            if (model.getValue() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getValue());
            }
            System.out.printf("%-5.5s", location);
            if (city.equals("all")) {
            } else {
                System.out.printf("%-20.20s", city);
            }
            System.out.println();
        }
    }


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
