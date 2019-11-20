package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}

	/**
	 * Displays simple text interface

	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Insert branch");
			System.out.println("2. Delete branch");
			System.out.println("3. Update branch name");
			System.out.println("4. Show branch");
			System.out.println("5. Quit");
			System.out.print("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					handleInsertOption(); 
					break;
				case 2:  
					handleDeleteOption(); 
					break;
				case 3: 
					handleUpdateOption();
					break;
				case 4:  
//					delegate.showBranch();
					break;
				case 5:
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}
	 */

	public void showMainMenu(TerminalTransactionsDelegate delegate) throws ParseException {
		this.delegate = delegate;

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice != 5) {
			System.out.println();
			System.out.println("1. View Available Vehicles");
			System.out.println("2. Make a Reservation");
			System.out.println("3. Rent a Vehicle");
			System.out.println("4. Return a Vehicle");
			System.out.println("5. Print a Report");
			System.out.print("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
//						handleViewAvailableVehicles();
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
	}



	private void handleDeleteOption() {
		int branchId = INVALID_INPUT;
		while (branchId == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to delete: ");
			branchId = readInteger(false);
			if (branchId != INVALID_INPUT) {
//				delegate.deleteBranch(branchId);
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
							break;
						}
					case 2:
						if (delegate.vehicleTypeAvailable("SUV")) {
							vtname = "SUV";
							break;
						} else {
							System.out.println(WARNING_TAG + " The vehicle type you are trying to rent is unavailable, please pick a different option.");
							break;
						}
					case 3:
						if (delegate.vehicleTypeAvailable("Convertible")) {
							vtname = "Convertible";
							break;
						} else {
							System.out.println(WARNING_TAG + " The vehicle type you are trying to rent is unavailable, please pick a different option.");
							break;
						}
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}


		String start = null;
        Timestamp sqlStartDate = null;
		while (sqlStartDate == null || start.length() <= 0) {
			System.out.print("Please enter when you'd like to start your rental (yyyy-mm-dd hh:mm:ss.SSS): ");
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
			System.out.print("Please enter when you'd like to start your rental (yyyy-mm-dd hh:mm:ss): ");
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
		System.out.println("Vehicle Type: " + vtname);
		System.out.println("Start Date: " + sqlStartDate);
		System.out.println("Return Date: " + sqlEndDate);
		System.out.println("Your Driver License Number: " + dlicense);
	}

	private void handleNewCustomer(int dlicense) {
		int phone = INVALID_INPUT;
		while (phone == INVALID_INPUT) {
			System.out.print("Please enter your phone number: ");
			phone = readInteger(false);
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

		delegate.insertCustomer(cust);
	}

	private void handleMakeRet() {
        int rid = INVALID_INPUT;
        while (rid == INVALID_INPUT) {
            System.out.print("Please enter your rental id: ");
            rid = readInteger(false);

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
            System.out.print("Was the gas tank full? 1. yes 2. no");
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
        Rental r = delegate.getRentalFromRid(rid);
        int val = vt.calculateValue(sqlDate, sqlDate, initOdom - odom);

        Ret ret = new Ret(rid, sqlDate, odom, fullTank, val);
	    delegate.makeReturn(ret);
    }

	private void handleMakeRental() {
		int confNumber = INVALID_INPUT;
		while (confNumber == INVALID_INPUT) {
			System.out.print("Please your reservation confirmation number: ");
			confNumber = readInteger(false);
		}

		if (!delegate.confNumberExists(confNumber)) {
			handleMakeReservation();
		}

	}
	
	private void handleInsertOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to insert: ");
			id = readInteger(false);
		}
		
		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the branch name you wish to insert: ");
			name = readLine().trim();
		}
		
		// branch address is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the branch address you wish to insert: ");
		String address = readLine().trim();
		if (address.length() == 0) {
			address = null;
		}
		
		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the branch city you wish to insert: ");
			city = readLine().trim();
		}
		
		int phoneNumber = INVALID_INPUT;
		while (phoneNumber == INVALID_INPUT) {
			System.out.print("Please enter the branch phone number you wish to insert: ");
			phoneNumber = readInteger(true);
		}
		
//		Vehicle model = new Vehicle(address,
//											city,
//											id,
//											name,
//											phoneNumber);
//		delegate.insertBranch(model);
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
	
	private void handleUpdateOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to update: ");
			id = readInteger(false);
		}

		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the branch name you wish to update: ");
			name = readLine().trim();
		}

//		delegate.updateBranch(id, name);
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
