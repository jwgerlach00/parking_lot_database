package parking_lot_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PermitAssignment {

    private static Scanner scanner = new Scanner(System.in);

    
    public static Connection conn;

    // Initialize the database connection
    static {
        try {
            conn = ParkingLotDB.initializeDatabase();
            //
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    
    public static boolean assignPermitToDrivers(String driverID,String permitID) {
        try {
          

            // Determine the number of permits based on driver's status
//            int numberOfPermitsCorrect = getNumberOfPermitsForDriver(driverID, permitID);
            
            System.out.println(isSpecialPermit(permitID));
            
            boolean run = false;
            
            if (isSpecialPermit(permitID)) {
            	if (howManySpecial(driverID) < getNumberOfSpecialPermitsForDriver(driverID, permitID)) {
            		run = true;
            	}
            } else {
            	if (howManyNormal(driverID) < getNumberOfPermitsForDriver(driverID, permitID)) {
            		run = true;
            	}
            }
            
            if (run) {
            	String query = "INSERT INTO DriversObtainPermits VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, driverID);
                pstmt.setString(2, permitID);
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("Permit ID " + permitID + " assigned to driver " + driverID);
                System.out.println("Permits Assigned Successfully");
                return true;
            } else {
            	System.out.println("Permits are fully assigned for the driver. Cannot assign more permits.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int howManyNormal(String driverPhoneNum) {
        return howMany(driverPhoneNum, false);
    }

    private static int howManySpecial(String driverPhoneNum) {
        return howMany(driverPhoneNum, true);
    }

    private static int howMany(String driverPhoneNum, boolean isSpecial) {
        try {
            String query = "SELECT COUNT(*) FROM DriversObtainPermits dp JOIN Permits p ON dp.permitID = p.permitID WHERE dp.driverID = ? AND p.permitType " + (isSpecial ? "IN ('Special event', 'Park & Ride')" : "NOT IN ('Special event', 'Park & Ride')");
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, driverPhoneNum);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            pstmt.close();
            // Do not close the connection here, as it will be closed in the calling method
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if status is not found (handle appropriately in your application)
    }

    private static boolean isSpecialPermit(String permitID) {
        try {
            String query = "SELECT COUNT(*) FROM Permits WHERE permitID = ? AND permitType IN ('Special event', 'Park & Ride')";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, permitID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            pstmt.close();
            // Do not close the connection here, as it will be closed in the calling method
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Return false if the permit is not found or not a special permit
    }
    private static int howmany(String driverPhoneNum) {
        try {
            String query = "SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, driverPhoneNum);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            pstmt.close();
            // Do not close the connection here, as it will be closed in the calling method
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // Return null if status is not found (handle appropriately in your application)
    }
    
    private static int howmanyN(String driverPhoneNum) {
    	try {
            String query = "SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, driverPhoneNum);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            pstmt.close();
            // Do not close the connection here, as it will be closed in the calling method
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // Return null if status is not found (handle appropriately in your application
    }
    
    private static int getNumberOfSpecialPermitsForDriver(String driverID, String permitID) {
    	String driverStatus = getDriverStatus(driverID);
        String 	permitType = getpermitType(permitID);
        
        if (driverStatus.equals("V")) {
        	return 0;
        } else {
        	return 1;
        }
    }
    
    private static int getNumberOfPermitsForDriver(String driverPhoneNum, String permit11) {

        String driverStatus = getDriverStatus(driverPhoneNum);
        String 	permitType1=getpermitType(permit11);

        if (driverStatus != null) {
            switch (driverStatus) {
                case "S":
                    return 1;
                case "E":
                    return 2;
                case "V":
                    return 1;
                // Add more cases for other driver statuses as needed
                default:
                    // For unknown status or other cases, you can handle accordingly
                    return 0;
            }
        }

        // Return 0 if driver status is not found (handle appropriately in your application)
        return 0;
    }
    // Get the driver's status from the database
    private static String getpermitType(String permit_id) {
        try {
            String query = "SELECT permitType FROM Permits WHERE permitID = ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, permit_id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("permitType");
            }

            pstmt.close();
            // Do not close the connection here, as it will be closed in the calling method
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if status is not found (handle appropriately in your application)
    }

    
    private static String getDriverStatus(String driverPhoneNum) {
        try {
            String query = "SELECT status FROM Drivers WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, driverPhoneNum);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("status");
            }

            pstmt.close();
            // Do not close the connection here, as it will be closed in the calling method
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if status is not found (handle appropriately in your application)
    }
  


     static void displayAllDriverPermitAssignments() {
        try {
         
            String query = "SELECT * FROM DriversObtainPermits";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("Driver ID: " + rs.getString("driverID"));
                System.out.println("Permit ID: " + rs.getString("permitID"));
                System.out.println("------------------------");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}