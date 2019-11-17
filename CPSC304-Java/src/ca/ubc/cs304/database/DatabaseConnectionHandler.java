package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Rentals;
import ca.ubc.cs304.model.Ret;
import ca.ubc.cs304.model.Vehicle;

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
			PreparedStatement ps = connection.prepareStatement("UPDATE vehicle SET status WHERE vlicense = ?");
			ps.setString(1, license);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Vehicle " + license + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void makeRental(Rentals rental) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO rental VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, rental.getRid());
			ps.setInt(2, rental.getVid());
			ps.setInt(3, rental.getCellphone());
			ps.setDate(4, rental.getFromDate());
			ps.setTime(5, rental.getFromTime());
			ps.setDate(6, rental.getToDate());
			ps.setTime(7, rental.getToTime());
			ps.setInt(8, rental.getOdomoter());
			ps.setString(9, rental.getCardName());
			ps.setInt(10, rental.getCardNo());
			ps.setDate(11, rental.getExpDate());
			ps.setInt(12, rental.getConfNo());

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
				System.out.println(WARNING_TAG + " Rental " + vlicense + " does not exist!");
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
			PreparedStatement ps = connection.prepareStatement("INSERT INTO ret VALUES (?,?,?,?,?,?)");

			ps.setInt(1, ret.getRid());
			ps.setDate(2, ret.getDate());
			ps.setTime(3, ret.getTime());
			ps.setInt(4, ret.getOdomoter());
			ps.setBoolean(5, ret.isFulltank());
			ps.setFloat(6, ret.getValue());

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
	
	public void insertBranch(Vehicle model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
			ps.setInt(1, model.getId());
			ps.setString(2, model.getName());
			ps.setString(3, model.getAddress());
			ps.setString(4, model.getCity());
			if (model.getPhoneNumber() == 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, model.getPhoneNumber());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	
	public Vehicle[] getBranchInfo() {
		ArrayList<Vehicle> result = new ArrayList<Vehicle>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");
		
//    		// get info on ResultSet
//    		ResultSetMetaData rsmd = rs.getMetaData();
//
//    		System.out.println(" ");
//
//    		// display column names;
//    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
//    			// get column name and print it
//    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//    		}
			
			while(rs.next()) {
				Vehicle model = new Vehicle(rs.getString("branch_addr"),
													rs.getString("branch_city"),
													rs.getInt("branch_id"),
													rs.getString("branch_name"),
													rs.getInt("branch_phone"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}	
		
		return result.toArray(new Vehicle[result.size()]);
	}
	
	public void updateBranch(int id, String name) {
		try {
		  PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
		  ps.setString(1, name);
		  ps.setInt(2, id);
		
		  int rowCount = ps.executeUpdate();
		  if (rowCount == 0) {
		      System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
		  }
	
		  connection.commit();
		  
		  ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}	
	}
	
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
}
