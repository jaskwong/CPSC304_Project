package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * The class is only responsible for handling terminal text inputs.
 */
public class TerminalTransactions {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private BufferedReader bufferedReader = null;
    private TerminalTransactionsDelegate delegate = null;

    public TerminalTransactions() {
    }


    public void showMainMenu(TerminalTransactionsDelegate delegate) throws ParseException {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 8) {
            System.out.println();
            System.out.println("1. View Available Vehicles you specify");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Rent a Vehicle");
            System.out.println("4. Return a Vehicle");
            System.out.println("5. Print a Report");
            System.out.println("6. View Reservations");
            System.out.println("7. View all Available Vehicles");
            System.out.println("8. Quit");
            System.out.print("Please choose one of the above 8 options: ");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleViewAvailableCar();
                        break;
                    case 2:
                        handleMakeReservation();
                        break;
                    case 3:
                        handleMakeRental();
                        break;
                    case 4:
                        handleMakeRet();
                        break;
                    case 5:
                        handleMakeReport();
                        break;
                    case 6:
                        handleViewReservations();
                        break;
                    case 7:
                        handleViewAvailableVehicles();
                        break;
                    case 8:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }


    private void handleMakeReport() {


        String date = null;
        Timestamp sqlDate = null;
        while (sqlDate == null || date.length() <= 0) {
            System.out.print("Please enter when you'd like to start your rental (yyyy-mm-dd hh:mm:ss): ");
            date = readLine().trim();
            try {
                Date startDate = dateFormat.parse(date);
                sqlDate = new java.sql.Timestamp(startDate.getTime());
            } catch (ParseException e) {
                System.out.println("Please enter a valid date");
                date = null;
                continue;
            }
        }

        int c = INVALID_INPUT;
        date = date.substring(0, 10);
        while (c == INVALID_INPUT) {
            System.out.println("Enter 1 to see the all Rentals report on " + date);
            System.out.println("Enter 2 to see the all Return report on " + date);
            System.out.println("Enter 3 to see the Rentals report of the branch you specify on " + date);
            System.out.print("Enter 4 to see the Return report of the branch you specify on " + date + " : ");
            c = readInteger(false);

            if (c == 1) {
                String location = "all";
                String city = "all";
                delegate.viewRentalReport(location, city, sqlDate);
            } else if (c == 2) {
                String location = "all";
                String city = "all";
                delegate.viewReturnReport(location, city, sqlDate);
            } else if (c == 3) {
                String location = null;
                while (location == null || location.length() <= 0) {
                    System.out.print("Please enter the branch's location of the rentals report you want to view: ");
                    location = readLine().trim();
                }
                String city = null;
                while (city == null || city.length() <= 0) {
                    System.out.print("Please enter the branch's city of the rentals report you want to view: ");
                    city = readLine().trim();
                }
                location = location.substring(0, 1).toUpperCase() + location.substring(1);
                city = city.substring(0, 1).toUpperCase() + city.substring(1);

                delegate.viewRentalReport(location, city, sqlDate);
            } else if (c == 4) {
                String location = null;
                while (location == null || location.length() <= 0) {
                    System.out.print("Please enter the branch's location of the return report you want to view: ");
                    location = readLine().trim();
                }
                String city = null;
                while (city == null || city.length() <= 0) {
                    System.out.print("Please enter the branch's city of the return report you want to view: ");
                    city = readLine().trim();
                }
                location = location.substring(0, 1).toUpperCase() + location.substring(1);
                city = city.substring(0, 1).toUpperCase() + city.substring(1);

                delegate.viewReturnReport(location, city, sqlDate);
            } else {
                System.out.println("Not a valid input");
                c = INVALID_INPUT;
            }
        }
    }


    private void handleViewAvailableVehicles() {
        delegate.viewAvailableVehicles();
    }

    private void handleViewAvailableCar() {
        System.out.print("Please enter the car type you want to view: ");
        String vtname = readLine().trim();
        if (vtname.length() == 0) {
            vtname = null;
        } else if (vtname.equals("suv")) {
            vtname = vtname.toUpperCase();
        } else {
            vtname = vtname.substring(0, 1).toUpperCase() + vtname.substring(1);
        }

        int startYear = INVALID_INPUT;
        while (startYear == INVALID_INPUT) {
            System.out.print("Please enter the start year of the car you want to view: ");
            startYear = readInteger(true);
        }

        int endYear = INVALID_INPUT;
        while (endYear == INVALID_INPUT) {
            System.out.print("Please enter the end year of the car you want to view: ");
            endYear = readInteger(true);
            if (endYear < startYear) {
                System.out.println("End year is invalid before Start year");
                endYear = INVALID_INPUT;
            }
        }

        System.out.print("Please enter the location of the car you want to view: ");
        String location = readLine().trim();
        if (location.length() == 0) {
            location = null;
        } else {
            location = location.substring(0, 1).toUpperCase() + location.substring(1);
        }

        delegate.viewCarnum(vtname, startYear, endYear, location);
        int c = INVALID_INPUT;
        while (c == INVALID_INPUT) {
            System.out.print("Enter 1 to see detail of the cars, enter other integer to return to the main menu: ");
            c = readInteger(false);
            if (c == 1) {
                delegate.viewCardetail(vtname, startYear, endYear, location);
            } else if (c == 2) {
            } else {
                System.out.println("Not a valid input");
                c = INVALID_INPUT;
            }
        }
    }


    private void handleMakeReservation() {
        int dlicense = INVALID_INPUT;
        while (dlicense == INVALID_INPUT) {
            System.out.print("Please enter your driver license number: ");
            dlicense = readInteger(false);
        }

        if (!delegate.customerExists(dlicense)) {
            System.out.println("Please register as a new customer first");
            handleNewCustomer(dlicense);
        }

        int vehicletype = INVALID_INPUT;
        String vtname = null;
        while (vehicletype == INVALID_INPUT) {
            System.out.println("1. Sedan ");
            System.out.println("2. SUV ");
            System.out.println("3. Convertible ");
            System.out.print("Please choose your vehicle type from the above options: ");
            vehicletype = readInteger(false);

            if (vehicletype != INVALID_INPUT) {
                switch (vehicletype) {
                    case 1:
                        if (delegate.vehicleTypeAvailable("Sedan")) {
                            vtname = "Sedan";
                            break;
                        } else {
                            System.out.println(WARNING_TAG + " The vehicle type you are trying to rent is unavailable, please pick a different option.");
                            vehicletype = INVALID_INPUT;
                            break;
                        }
                    case 2:
                        if (delegate.vehicleTypeAvailable("SUV")) {
                            vtname = "SUV";
                            break;
                        } else {
                            System.out.println(WARNING_TAG + " The vehicle type you are trying to rent is unavailable, please pick a different option.");
                            vehicletype = INVALID_INPUT;
                            break;
                        }
                    case 3:
                        if (delegate.vehicleTypeAvailable("Convertible")) {
                            vtname = "Convertible";
                            break;
                        } else {
                            System.out.println(WARNING_TAG + " The vehicle type you are trying to rent is unavailable, please pick a different option.");
                            vehicletype = INVALID_INPUT;
                            break;
                        }
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        vehicletype = INVALID_INPUT;
                        break;
                }
            }
        }


        String start = null;
        Timestamp sqlStartDate = null;
        while (sqlStartDate == null || start.length() <= 0) {
            System.out.print("Please enter when you'd like to start your rental (yyyy-mm-dd hh:mm:ss): ");
            start = readLine().trim();
            try {
                Date startDate = dateFormat.parse(start);
                sqlStartDate = new java.sql.Timestamp(startDate.getTime());
            } catch (ParseException e) {
                System.out.println("Please enter a valid date");
                start = null;
                continue;
            }
        }

        String end = null;
        Timestamp sqlEndDate = null;
        while (sqlEndDate == null || end.length() <= 0) {
            System.out.print("Please enter when you'd like to finish your rental (yyyy-mm-dd hh:mm:ss): ");
            end = readLine().trim();
            try {
                Date endDate = dateFormat.parse(end);
                sqlEndDate = new java.sql.Timestamp(endDate.getTime());
            } catch (ParseException e) {
                System.out.println("Not a valid date");
                end = null;
                continue;
            }
        }

        Random rand = new Random();
        int confno = rand.nextInt(90000000) + 10000000;
        Reservation reso = new Reservation(confno, vtname, dlicense, sqlStartDate, sqlEndDate);

        delegate.insertReservation(reso);

        System.out.println("Thank you for the completing the reservation with confirmation number: " + confno);
        System.out.println("These are the details of your reservation: ");
        System.out.println("Confirmation No: " + confno);
        System.out.println("Vehicle Type: " + vtname);
        System.out.println("Start Date: " + sqlStartDate);
        System.out.println("Return Date: " + sqlEndDate);
        System.out.println("Your Driver License Number: " + dlicense);
    }

    private void handleNewCustomer(int dlicense) {
        String phone = null;
        while (phone == null || phone.length() <= 0) {
            System.out.print("Please enter your phone number: ");
            phone = readLine().trim();
        }

        String name = null;
        while (name == null || name.length() <= 0) {
            System.out.print("Please enter your name ");
            name = readLine().trim();
        }

        String addr = null;
        while (addr == null || addr.length() <= 0) {
            System.out.print("Please enter your address ");
            addr = readLine().trim();
        }

        Customer cust = new Customer(phone, name, addr, dlicense);
        delegate.makeCustomer(cust);
    }

    private void handleMakeRet() {
        int rid = INVALID_INPUT;
        while (rid == INVALID_INPUT) {
            System.out.print("Please enter your rental id: ");
            rid = readInteger(false);
            if (rid != INVALID_INPUT) {
                if (!delegate.rentalExists(rid)) {
                    System.out.println("There is no such rental");
                    rid = INVALID_INPUT;
                } else if (delegate.returnExists(rid)) {
                    System.out.println("This rental has already been returned");
                    return;
                }
            }
        }


        String date = null;
        Timestamp sqlDate = null;
        while (sqlDate == null || date.length() <= 0) {
            System.out.print("Please enter the date of the return (yyyy-mm-dd hh:mm:ss): ");
            date = readLine().trim();
            try {
                Date endDate = dateFormat.parse(date);
                sqlDate = new java.sql.Timestamp(endDate.getTime());
            } catch (ParseException e) {
                System.out.println("Not a valid date");
                date = null;
                continue;
            }
        }

        int odom = INVALID_INPUT;
        while (odom == INVALID_INPUT) {
            System.out.print("Please enter the odometer reading: ");
            odom = readInteger(false);
        }

        int temp = INVALID_INPUT;
        boolean fullTank = false;
        while (temp == INVALID_INPUT) {
            System.out.print("Was the gas tank full? 1. yes 2. no ");
            temp = readInteger(false);
            if (temp == 1) {
                fullTank = true;
            } else if (temp == 2) {
                fullTank = false;
            } else {
                System.out.println("Not a valid input");
                temp = INVALID_INPUT;
            }
        }

        int initOdom = Math.round(delegate.getInitOdom(rid));
        VehicleType vt = delegate.getVtFromRid(rid);
        Timestamp t = delegate.getRentalFromDateFromRid(rid);
        float val = vt.calculateValue(t, sqlDate, odom - initOdom);
        Ret ret = new Ret(rid, sqlDate, odom, fullTank, val);
        delegate.makeReturn(ret);
    }

    private void handleMakeRental() {
        int confNumber = INVALID_INPUT;
        while (confNumber == INVALID_INPUT) {
            System.out.print("Please enter your reservation confirmation number: ");
            confNumber = readInteger(false);
        }

        if (!delegate.confNumberExists(confNumber)) {
            System.out.println("Please complete a reservation first");
            handleMakeReservation();
            int newResConfNo = INVALID_INPUT;
            while (newResConfNo == INVALID_INPUT) {
                System.out.print("Please enter your new reservation confirmation number: ");
                newResConfNo = readInteger(false);
            }
        }

        String cardName = null;
        while (cardName == null || cardName.length() <= 0) {
            System.out.print("Please enter credit card name: ");
            cardName = readLine().trim();
        }

        int cardNo = INVALID_INPUT;
        while (cardNo == INVALID_INPUT) {
            System.out.print("Please enter your credit card number: ");
            cardNo = readInteger(false);
        }

        String date = null;
        Timestamp sqlDate = null;
        while (sqlDate == null || date.length() <= 0) {
            System.out.print("Please enter your credit card expiry (yyyy-mm-dd 00:00:00): ");
            date = readLine().trim();
            try {
                Date endDate = dateFormat.parse(date);
                sqlDate = new java.sql.Timestamp(endDate.getTime());
            } catch (ParseException e) {
                System.out.println("Not a valid date");
                date = null;
                continue;
            }
        }

        Random rand = new Random();
        int rid = rand.nextInt(90000000) + 10000000;

        String vt = delegate.getVtFromRes(confNumber);
        String vlicense = delegate.getAvailableVlicenseOfType(vt);
        delegate.setRented(vlicense);


        Timestamp from = delegate.getFromDateFromRes(confNumber);
        Timestamp to = delegate.getToDateFromRes(confNumber);
        int dlicense = delegate.getDlicenseFromRes(confNumber);
        int cellphone = delegate.getPhoneFromCustomer(dlicense);
        int odomoter = delegate.getOdomFromVehicle(vlicense);

        Rental rental = new Rental(rid, vlicense, cellphone, from, to, odomoter, cardName, cardNo, sqlDate, confNumber);

        delegate.makeRental(rental);

        System.out.println("Thank you for the completing your rental from Reservation: " + confNumber);
        System.out.println("These are the details of your Rental: ");
        System.out.println("Rental ID: " + rid);
        System.out.println("Vehicle License: " + vlicense);
        System.out.println("Customer Phone Number: " + cellphone);
        System.out.println("Start of Rental: " + from);
        System.out.println("End of Rental: " + to);
        System.out.println("Vehicle Initial Odometer: " + odomoter);
        System.out.println("Customer Card Name: " + cardName);
        System.out.println("Customer Card Number: " + cardNo);
        System.out.println("Customer Card Exp Date: " + sqlDate);
    }


    private void handleViewReservations() {
        delegate.viewReservations();
    }

    private void handleQuitOption() {
        System.out.println("Good Bye!");

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("IOException!");
            }
        }

        delegate.terminalTransactionsFinished();
    }


    private int readInteger(boolean allowEmpty) {
        String line = null;
        int input = INVALID_INPUT;
        try {
            line = bufferedReader.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        } catch (NumberFormatException e) {
            if (allowEmpty && line.length() == 0) {
                input = EMPTY_INPUT;
            } else {
                System.out.println(WARNING_TAG + " Your input was not an integer");
            }
        }
        return input;
    }

    private String readLine() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;

    }
}

