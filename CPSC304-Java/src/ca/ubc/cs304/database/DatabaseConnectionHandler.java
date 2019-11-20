package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;
	
	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void setRented(String license) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE vehicle SET status WHERE vlicense = ? THEN ?");
			ps.setString(1, license);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Vehicle " + license + " does not exist!");
			} else {
			    ps.setString(2, "RENTED");
            }

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void makeRental(Rental rental) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO rental VALUES (?,?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, rental.getRid());
			ps.setInt(2, rental.getVid());
			ps.setInt(3, rental.getCellphone());
			ps.setTimestamp(4, rental.getFromDate());
			ps.setTimestamp(5, rental.getToDate());
			ps.setInt(6, rental.getOdomoter());
			ps.setString(7, rental.getCardName());
			ps.setInt(8, rental.getCardNo());
			ps.setDate(9, rental.getExpDate());
			ps.setInt(10, rental.getConfNo());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void makeVehicle(Vehicle vehicle) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO vehicle VALUES (?,?,?,?,?,?,?,?,?,?)");

			ps.setString(1, vehicle.getVlicense());
			ps.setString(2, vehicle.getMake());
			ps.setString(3, vehicle.getModel());
			ps.setInt(4, vehicle.getYear());
			ps.setString(5, vehicle.getColor());
			ps.setInt(6, vehicle.getOdomoter());
			ps.setString(7, vehicle.getStatus());
			ps.setString(8, vehicle.getVtname());
			ps.setString(9, vehicle.getLocation());
			ps.setString(10, vehicle.getCity());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteVehicle(String vlicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM vehicle WHERE vlicense = ?");
			ps.setString(1, vlicense);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Vehicle " + vlicense + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void makeRet(Ret ret) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO ret VALUES (?,?,?,?,?)");

			ps.setInt(1, ret.getRid());
			ps.setTimestamp(2, ret.getTime());
			ps.setInt(3, ret.getOdomoter());
			ps.setBoolean(4, ret.isFulltank());
			ps.setFloat(5, ret.getValue());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteRet(int rid) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM ret WHERE rid = ?");
			ps.setInt(1, rid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Return " + rid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteRental(int rid) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM rental WHERE rid = ?");
			ps.setInt(1, rid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Rental " + rid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteBranch(int branchId) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
			ps.setInt(1, branchId);
			
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
			}

			connection.commit();
	
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

    public void makeCustomer(Customer c){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customers VALUES(?,?,?,?)");
            ps.setInt(1, c.getCellphone());
            ps.setString(2, c.getName());
            ps.setString(3, c.getAddress());
            ps.setInt(4, c.getDlicense());

			ps.executeUpdate();
			connection.commit();
			ps.close();
        } catch (SQLException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteCustomer(int dlic){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM customers WHERE customer_dlicense = ?");
            ps.setInt(1, dlic);

			ps.executeUpdate();
			connection.commit();
			ps.close();
        } catch (SQLException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void makeReservation(Reservation r){

	    try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservations VALUES(?,?,?,?,?)");
            ps.setInt(1,  r.getConfNo());
            ps.setString(2, r.getVtname());
            ps.setInt(3, r.getCustomer_dlicense());
            ps.setTimestamp(4, r.getFromDate());
            ps.setTimestamp(5, r.getToDate());

			ps.executeUpdate();
			connection.commit();
			ps.close();
        } catch (SQLException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteReservation(int confno){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reservations WHERE reservations_confNo = ?");
            ps.setInt(1, confno);

			ps.executeUpdate();
			connection.commit();
			ps.close();
        } catch (SQLException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void makeVehicleType(VehicleType vt){
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO vehicletypes VALUES(?,?,?,?,?,?,?)");
            ps.setString(1,  vt.getVtname());
            ps.setString(2, vt.getFeatures());
            ps.setFloat(3, vt.getWrate());
            ps.setFloat(4, vt.getDrate());
            ps.setFloat(5, vt.getHrate());
            ps.setFloat(6, vt.getWirate());
            ps.setFloat(7, vt.getDirate());
            ps.setFloat(8, vt.getHirate());
            ps.setFloat(9, vt.getKrate());

			ps.executeUpdate();
			connection.commit();
			ps.close();
        } catch (SQLException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteVehicleType(String vtName) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM vehicletypes WHERE vt_name = ?");
            ps.setString(1, vtName);
        } catch (SQLException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public boolean customerExists(int dlicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) from CUSTOMER where dlicense = ?");
			ps.setInt(1, dlicense);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e){
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return false;
	}

	public boolean vehicleTypeAvailable(String vtname) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) from VEHICLE where (vt_name = ? AND v_status = ?)");
			ps.setString(1, vtname);
			ps.setString(2, "AVAILABLE");
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e){
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return false;
	}

	public float getInitOdom(int rid) {
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT rentals_odometer FROM rentals" +
                        "WHERE rentals.rid = ?");
                ps.setInt(1, rid);
                ResultSet rs = ps.executeQuery();

                // get info on ResultSet
                ResultSetMetaData rsmd = rs.getMetaData();

                Float initOdom = rs.getFloat(1);
                rs.close();
                return initOdom;
            } catch (SQLException e) {
                System.out.println("Not a valid rid");
                return -1;
            }

    }

    public Rental getRentalFromRid(int rid) {
        Rental r;
        try {
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM rentals" +
                    "WHERE rentals.rid = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }
            r = new Rental(rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getTimestamp(4),
                    rs.getTimestamp(5),
                    rs.getInt(6),
                    rs.getString(7),
                    rs.getInt(8),
                    rs.getDate(9),
                    rs.getInt(10));

            rs.close();
            stmt.close();

            return r;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }


    public VehicleType getVtFromRid(int rid) {
        VehicleType vt;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles, rentals, vehicletypes " +
                    "WHERE rentals.rid = ? and rentals.vid = vehicles.vid and vehicles.vt_name = vehicletypes.vt_name");

    		// get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }
            vt = new VehicleType(rs.getString("vt_name"),
                    rs.getString("vt_features"),
                    rs.getFloat("vt_wrate"),
                    rs.getFloat("vt_drate"),
                    rs.getFloat("vt_hrate"),
                    rs.getFloat("vt_wirate"),
                    rs.getFloat("vt_dirate"),
                    rs.getFloat("vt_hirate"),
                    rs.getFloat("vt_krate"));

            rs.close();
            stmt.close();

            return vt;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

	
//	public void insertBranch(Vehicle model) {
//		try {
//			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
//			ps.setInt(1, model.getId());
//			ps.setString(2, model.getName());
//			ps.setString(3, model.getAddress());
//			ps.setString(4, model.getCity());
//			if (model.getPhoneNumber() == 0) {
//				ps.setNull(5, java.sql.Types.INTEGER);
//			} else {
//				ps.setInt(5, model.getPhoneNumber());
//			}
//
//			ps.executeUpdate();
//			connection.commit();
//
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			rollbackConnection();
//		}
//	}
	
//	public Vehicle[] getBranchInfo() {
//		ArrayList<Vehicle> result = new ArrayList<Vehicle>();
//
//		try {
//			Statement stmt = connection.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");
//
////    		// get info on ResultSet
////    		ResultSetMetaData rsmd = rs.getMetaData();
////
////    		System.out.println(" ");
////
////    		// display column names;
////    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
////    			// get column name and print it
////    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
////    		}
//
//			while(rs.next()) {
//				Vehicle model = new Vehicle(rs.getString("branch_addr"),
//													rs.getString("branch_city"),
//													rs.getInt("branch_id"),
//													rs.getString("branch_name"),
//													rs.getInt("branch_phone"));
//				result.add(model);
//			}
//
//			rs.close();
//			stmt.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//		}
//
//		return result.toArray(new Vehicle[result.size()]);
//	}
//
//	public void updateBranch(int id, String name) {
//		try {
//		  PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
//		  ps.setString(1, name);
//		  ps.setInt(2, id);
//
//		  int rowCount = ps.executeUpdate();
//		  if (rowCount == 0) {
//		      System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
//		  }
//
//		  connection.commit();
//
//		  ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			rollbackConnection();
//		}
//	}
	
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}
	
			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);
	
			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();	
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public boolean confNumberExists(int confNumber) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) from RESERVATION where confNumber = ?");
			ps.setInt(1, confNumber);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e){
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return false;
	}
}
