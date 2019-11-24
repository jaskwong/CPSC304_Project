package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
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
            PreparedStatement ps = connection.prepareStatement("UPDATE vehicles SET v_status = 'R' WHERE v_license = ?");
            ps.setString(1, license);
            ps.executeUpdate();

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public static int generateRid() {
        return (int) Math.round((Math.random() * 9000000) + 1000000);
    }


    public void makeRental(Rental rental) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO rentals VALUES (?,?,?,?,?,?,?,?,?,?)");

            ps.setInt(1, rental.getRid());
            ps.setString(2, rental.getV_license());
            ps.setInt(3, rental.getCellphone());
            ps.setTimestamp(4, rental.getFromDate());
            ps.setTimestamp(5, rental.getToDate());
            ps.setInt(6, rental.getOdomoter());
            ps.setString(7, rental.getCardName());
            ps.setInt(8, rental.getCardNo());
            ps.setTimestamp(9, rental.getExpDate());
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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO rets VALUES (?,?,?,?,?)");

            ps.setInt(1, ret.getRid());
            ps.setTimestamp(2, ret.getTime());
            ps.setInt(3, ret.getOdomoter());
            ps.setBoolean(4, ret.isFulltank());

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


    public void makeCustomer(Customer c) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customers VALUES(?,?,?,?)");
            ps.setString(1, c.getCellphone());
            ps.setString(2, c.getName());
            ps.setString(3, c.getAddress());
            ps.setInt(4, c.getDlicense());

            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteCustomer(int dlic) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM customers WHERE customer_dlicense = ?");
            ps.setInt(1, dlic);

            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void makeReservation(Reservation r) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservations VALUES(?,?,?,?,?)");
            ps.setInt(1, r.getConfNo());
            ps.setString(2, r.getVtname());
            ps.setInt(3, r.getCustomer_dlicense());
            ps.setTimestamp(4, r.getFromDate());
            ps.setTimestamp(5, r.getToDate());

            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Rental  makeRentalFromReservation(int confNo, String v_license, String cardName, int cardNo, Timestamp exp) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM reservations WHERE reservations_confNo = ?");
            ps.setInt(1, confNo);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Random rand = new Random();
            int rid = rand.nextInt(90000000) + 10000000;

            Rental r = new Rental(rid, v_license,
                    rs.getInt("customer_dlicense"),
                    rs.getTimestamp("reservations_from"),
                    rs.getTimestamp("reservations_to"),
                    getOdomFromVehicle(v_license),
                    cardName,
                    cardNo,
                    exp,
                    confNo);
            rs.close();
            return r;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public void deleteReservation(int confno) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reservations WHERE reservations_confNo = ?");
            ps.setInt(1, confno);


            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void makeVehicleType(VehicleType vt) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO vehicletypes VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, vt.getVtname());
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
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteVehicleType(String vtName) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM vehicletypes WHERE vt_name = ?");
            ps.setString(1, vtName);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public boolean customerExists(int dlicense) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT customer_dlicense FROM customers where customer_dlicense = ?");
            ps.setInt(1, dlicense);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }

    public boolean rentalExists(int rid) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT rentals_rid FROM rentals where rentals_rid = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }

    public boolean returnExists(int rid) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT rentals_rid FROM rets where rentals_rid = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }

    public boolean vehicleTypeAvailable(String vtname) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT vt_name FROM vehicles WHERE (vt_name = ? AND v_status = ?)");
            ps.setString(1, vtname);
            ps.setString(2, "A");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }

    public String getAvailableOfType(String vtname) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT v_license FROM vehicles WHERE (vt_name = ? AND v_status = ?)");
            ps.setString(1, vtname);
            ps.setString(2, "A");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("v_license");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return null;
    }

    public String getVtFromRes(int confNumber) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT vt_name FROM reservations WHERE reservations_confno = ?");
            ps.setInt(1, confNumber);
            ResultSet rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            String vt = rs.getString("vt_name");
            rs.close();
            return vt;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }

    }

    public Timestamp getFromDateFromRes(int confNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT reservations_from FROM reservations WHERE reservations_confNo = ?");
            ps.setInt(1, confNumber);
            ResultSet rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            Timestamp from = rs.getTimestamp("reservations_from");
            rs.close();
            return from;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public Timestamp getToDateFromRes(int confNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT reservations_to FROM reservations WHERE reservations_confNo = ?");
            ps.setInt(1, confNumber);
            ResultSet rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            Timestamp to = rs.getTimestamp("reservations_to");
            rs.close();
            return to;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public int getDLicenseFromRes(int confNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT customer_dlicense FROM reservations WHERE reservations_confNo = ?");
            ps.setInt(1, confNumber);
            ResultSet rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            int dlicense = rs.getInt("reservations_to");
            rs.close();
            return dlicense;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return -1;
        }
    }


    public int getInitOdom(int rid) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT rentals_odometer FROM rentals WHERE rentals_rid = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            int initOdom = rs.getInt("rentals_odometer");
            rs.close();
            return initOdom;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return -1;
        }
    }


    public Timestamp getRentalFromDateFromRid(int rid) {
        try {
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT rentals_from FROM rentals WHERE rentals_rid = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            rs.next();

            // get info on ResultSet

            Timestamp t = rs.getTimestamp("rentals_from");
            rs.close();
            stmt.close();

            return t;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public void viewAvailableVehicles() {

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles WHERE v_status = 'A'");

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                if (i == 5) {
                    continue;
                }
                // get column name and print it
                System.out.printf("%-25s", rsmd.getColumnName(i + 1));
            }

            while (rs.next()) {
                Vehicle v = new Vehicle(rs.getString("v_license"),
                        rs.getString("v_make"),
                        rs.getString("v_model"),
                        rs.getInt("v_year"),
                        rs.getString("v_color"),
                        rs.getInt("v_odometer"),
                        rs.getString("v_status"),
                        rs.getString("vt_name"),
                        rs.getString("v_location"),
                        rs.getString("v_city"));
                System.out.println("\n");
                System.out.printf("%-25s", v.getVlicense());
                System.out.printf("%-25s", v.getMake());
                System.out.printf("%-25s", v.getMake());
                System.out.printf("%-25s", v.getYear());
                System.out.printf("%-25s", v.getColor());
                System.out.printf("%-25s", v.getOdomoter());
                System.out.printf("%-25s", v.getVtname());
                System.out.printf("%-25s", v.getLocation());
                System.out.printf("%-25s", v.getCity());
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

    }

    public void viewReservations() {

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservations");

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                System.out.printf("%-25s", rsmd.getColumnName(i + 1));
            }

            while (rs.next()) {
                Reservation res = new Reservation(rs.getInt("reservations_confNo"),
                        rs.getString("vt_name"),
                        rs.getInt("customer_dlicense"),
                        rs.getTimestamp("reservations_from"),
                        rs.getTimestamp("reservations_to"));
                System.out.println("\n");
                System.out.printf("%-25s", res.getConfNo());
                System.out.printf("%-25s", res.getVtname());
                System.out.printf("%-25s", res.getCustomer_dlicense());
                System.out.printf("%-25s", res.getFromDate());
                System.out.printf("%-25s", res.getToDate());
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

    }

    public VehicleType getVtFromRid(int rid) {
        VehicleType vt;
        try {
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT vehicletypes.vt_name, vehicletypes.vt_features, vehicletypes.vt_wrate, vehicletypes.vt_drate, vehicletypes.vt_hrate, vehicletypes.vt_wirate, vehicletypes.vt_dirate, vehicletypes.vt_hirate, vehicletypes.vt_krate FROM vehicles, rentals, vehicletypes " +
                    "WHERE rentals_rid = ? and rentals.v_license = vehicles.v_license and vehicles.vt_name = vehicletypes.vt_name");

            // get info on ResultSet
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            rs.next();

            System.out.println(" ");

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


    public void viewVehiclenum(String vtname, int startYear, int endYear, String location) {
        PreparedStatement ps;
        try {
            if (vtname == null) {
                if (startYear == 0) {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A'");
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND v_location = ?");
                            ps.setString(1, location);
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND v_year <= ?");
                            ps.setInt(1, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND v_year_ <= ? AND v_location = ?");
                            ps.setInt(1, endYear);
                            ps.setString(2, location);
                        }
                    }
                } else {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND v_year >= ?");
                            ps.setInt(1, startYear);
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND v_year >= ? AND v_location = ?");
                            ps.setInt(1, startYear);
                            ps.setString(2, location);
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND v_year >= ? AND v_year <= ?");
                            ps.setInt(1, startYear);
                            ps.setInt(2, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND v_year >= ? And v_year <= ? v_location = ?");
                            ps.setInt(1, startYear);
                            ps.setInt(2, endYear);
                            ps.setString(3, location);
                        }
                    }
                }
            } else {
                if (startYear == 0) {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ?");
                            ps.setString(1, vtname);
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_location = ?");
                            ps.setString(1, vtname);
                            ps.setString(2, location);
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year <= ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year <= ? AND v_lovation = ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, endYear);
                            ps.setString(3, location);
                        }
                    }
                } else {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ? AND v_location = ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                            ps.setString(3, location);
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ? AND v_year <= ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                            ps.setInt(3, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ? AND v_year <= ? AND v_location = ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                            ps.setInt(3, endYear);
                            ps.setString(4, location);
                        }
                    }
                }
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer n = rs.getInt(1);
                System.out.println("There are " + n + " available vehicles with these options");
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Vehicle[] getCarInfom(String vtname, int startYear, int endYear, String location) {
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Vehicle> result = new ArrayList<Vehicle>();
        try {
            if (vtname == null) {
                if (startYear == 0) {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A'");
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND v_location = ?");
                            ps.setString(1, location);
                            ;
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND v_year <= ?");
                            ps.setInt(1, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND v_year_ <= ? AND v_location = ?");
                            ps.setInt(1, endYear);
                            ps.setString(2, location);
                        }
                    }
                } else {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND v_year >= ?");
                            ps.setInt(1, startYear);
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND v_year >= ? AND v_location = ?");
                            ps.setInt(1, startYear);
                            ps.setString(2, location);
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND v_year >= ? AND v_year <= ?");
                            ps.setInt(1, startYear);
                            ps.setInt(2, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND v_year >= ? And v_year <= ? v_location = ?");
                            ps.setInt(1, startYear);
                            ps.setInt(2, endYear);
                            ps.setString(3, location);
                        }
                    }
                }
            } else {
                if (startYear == 0) {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ?");
                            ps.setString(1, vtname);
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_location = ?");
                            ps.setString(1, vtname);
                            ps.setString(2, location);
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year <= ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year <= ? AND v_lovation = ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, endYear);
                            ps.setString(3, location);
                        }
                    }
                } else {
                    if (endYear == 0) {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ? AND v_location = ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                            ps.setString(3, location);
                        }
                    } else {
                        if (location == null) {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ? AND v_year <= ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                            ps.setInt(3, endYear);
                        } else {
                            ps = connection.prepareStatement("SELECT * FROM vehicles WHERE v_status = 'A' AND vt_name = ? AND v_year >= ? AND v_year <= ? AND v_location = ?");
                            ps.setString(1, vtname);
                            ps.setInt(2, startYear);
                            ps.setInt(3, endYear);
                            ps.setString(4, location);
                        }
                    }
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle model = new Vehicle(rs.getString("v_license"),
                        rs.getString("v_make"),
                        rs.getString("v_model"),
                        rs.getInt("v_year"),
                        rs.getString("v_color"),
                        rs.getInt("v_odometer"),
                        rs.getString("v_status"),
                        rs.getString("vt_name"),
                        rs.getString("v_location"),
                        rs.getString("v_city"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Vehicle[result.size()]);
    }


    public void viewRtalRptNum(String location, String city, Timestamp sqlDate) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            if (location.equals("all") && city.equals("all")) {
                ps = connection.prepareStatement("SELECT COUNT(*) FROM rentals " +
                        "WHERE to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') >= to_date((SUBSTR((to_char(rentals_from, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd') AND to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') <= to_date((SUBSTR((to_char(rentals_to, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd')");
                ps.setTimestamp(1, sqlDate);
                ps.setTimestamp(2, sqlDate);
            } else {
                ps = connection.prepareStatement("SELECT COUNT(*) FROM rentals, vehicles " +
                        "WHERE rentals.v_license = vehicles.v_license AND vehicles.v_location = ? AND vehicles.v_city = ? AND to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') >= to_date((SUBSTR((to_char(rentals_from, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd') AND to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') <= to_date((SUBSTR((to_char(rentals_to, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd')");
                ps.setString(1, location);
                ps.setString(2, city);
                ps.setTimestamp(3, sqlDate);
                ps.setTimestamp(4, sqlDate);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                Integer n = rs.getInt(1);
                System.out.println("There are " + n + " rental reports here");
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    public Rental[] getRtalRptInfo(String location, String city, Timestamp sqlDate) {
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Rental> result = new ArrayList<Rental>();
        try {
            if (location.equals("all") && city.equals("all")) {
                ps = connection.prepareStatement("SELECT * FROM rentals " +
                        "WHERE to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') >= to_date((SUBSTR((to_char(rentals_from, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd') AND to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') <= to_date((SUBSTR((to_char(rentals_to, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd')");
                ps.setTimestamp(1, sqlDate);
                ps.setTimestamp(2, sqlDate);
            } else {
                ps = connection.prepareStatement("SELECT rentals.rentals_rid, rentals.v_license, rentals.customer_dlicense, rentals.rentals_from, rentals.rentals_to, rentals.rentals_odometer, rentals.rentals_cardName, rentals.rentals_cardNo, rentals.rentals_ExpDate, rentals.reservations_confNo FROM rentals, vehicles " +
                        "WHERE rentals.v_license = vehicles.v_license AND vehicles.v_location = ? AND vehicles.v_city = ? AND to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') >= to_date((SUBSTR((to_char(rentals_from, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd') AND to_date((to_char(?, 'yyyy-mm-dd')), 'yyyy-mm-dd') <= to_date((SUBSTR((to_char(rentals_to, 'yyyy-mm-dd')), 1, 10)), 'yyyy-mm-dd')");
                ps.setString(1, location);
                ps.setString(2, city);
                ps.setTimestamp(3, sqlDate);
                ps.setTimestamp(4, sqlDate);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Rental model = new Rental(rs.getInt("rentals_rid"),
                        rs.getString("v_license"),
                        rs.getInt("customer_dlicense"),
                        rs.getTimestamp("rentals_from"),
                        rs.getTimestamp("rentals_to"),
                        rs.getInt("rentals_odometer"),
                        rs.getString("rentals_cardName"),
                        rs.getInt("rentals_cardNo"),
                        rs.getTimestamp("rentals_ExpDate"),
                        rs.getInt("reservations_confNo"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Rental[result.size()]);
    }

    public void viewRtnRptNum(String location, String city, Timestamp sqlDate) {
        PreparedStatement ps;
        try {
            if (location.equals("all") && city.equals("all")) {
                ps = connection.prepareStatement("SELECT COUNT(*) FROM rets WHERE (to_char(?, 'yyyy-mm-dd')) = (SUBSTR((to_char(rets_datetime, 'yyyy-mm-dd')), 1, 10))");
                ps.setTimestamp(1, sqlDate);
            } else {
                ps = connection.prepareStatement("SELECT COUNT(*) FROM rets, rentals, vehicles WHERE rets.rentals_rid = rentals.rentals_rid AND rentals.v_license = vehicles.v_license  AND vehicles.v_location = ? AND vehicles.v_city = ? AND (to_char(?, 'yyyy-mm-dd')) = (SUBSTR((to_char(rets_datetime, 'yyyy-mm-dd')), 1, 10))");
                ps.setString(1, location);
                ps.setString(2, city);
                ps.setTimestamp(3, sqlDate);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer n = rs.getInt(1);
                System.out.println("There are " + n + " return reports here");
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Ret[] getRtnRptInfo(String location, String city, Timestamp sqlDate) {
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Ret> result = new ArrayList<Ret>();
        try {
            if (location.equals("all") && city.equals("all")) {
                ps = connection.prepareStatement("SELECT * FROM rets WHERE (to_char(?, 'yyyy-mm-dd')) = (SUBSTR((to_char(rets_datetime, 'yyyy-mm-dd')), 1, 10))");
                ps.setTimestamp(1, sqlDate);
            } else {
                ps = connection.prepareStatement("SELECT rets.rentals_rid, rets.rets_datetime, rets.rets_odometer, rets.rets_fulltank, rets.rets_value FROM rets, rentals, vehicles " +
                        "WHERE rets.rentals_rid = rentals.rentals_rid AND rentals.v_license = vehicles.v_license  AND vehicles.v_location = ? AND vehicles.v_city = ? AND (to_char(?, 'yyyy-mm-dd')) = (SUBSTR((to_char(rets_datetime, 'yyyy-mm-dd')), 1, 10))");
                ps.setString(1, location);
                ps.setString(2, city);
                ps.setTimestamp(3, sqlDate);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Ret model = new Ret(rs.getInt("rentals_rid"),
                        rs.getTimestamp("rets_datetime"),
                        rs.getInt("rets_odometer"),
                        rs.getBoolean("rets_fulltank"),
                        rs.getFloat("rets_value"));
                result.add(model);

            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Ret[result.size()]);
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
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean confNumberExists(int confNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT reservations_confNo FROM reservations WHERE reservations_confNo = ?");
            ps.setInt(1, confNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }


    public int getPhoneFromCustomer(int dlicense) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT customer_cellphone FROM customers WHERE customer_dlicense = ?");
            ps.setInt(1, dlicense);
            ResultSet rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            int phone = rs.getInt("reservations_to");
            rs.close();
            return phone;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return -1;
        }
    }

    public int getOdomFromVehicle(String vlicense) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT v_odometer FROM vehicles WHERE v_license = ?");
            ps.setString(1, vlicense);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int odom = rs.getInt("v_odometer");
            rs.close();
            return odom;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return -1;
        }
    }
}
