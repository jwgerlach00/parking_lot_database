package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PermitAssignedVehicle {
	 public static Connection conn;

	    // Initialize the database connection
	    static {
	        try {
	            conn = ParkingLotDB.initializeDatabase();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
    public boolean assignVehicleToPermit(String permit_id, String license) {
    	
		// TODO Auto-generated method stub
		try {

		
		
		int howmnayLicTotal=howManyLic(permit_id);
		int howmanythere=getHowManythere(permit_id);
		if(howmanythere<howmnayLicTotal) {
		     String query = "INSERT INTO PermitsAssignedVehicles VALUES (?, ?)";
             PreparedStatement pstmt = conn.prepareStatement(query);
             pstmt.setString(1, permit_id);
             pstmt.setString(2, license);

             pstmt.executeUpdate();
             pstmt.close();

             // Display the permit information (you can customize this part)
             System.out.println("Permit ID " + permit_id + " assigned to License " + license);

             System.out.println("Permits Assigned Successfully");
             return true;
         } else {
             System.out.println("Permits are fully assigned for the License. Cannot assign more permits.");
             return false;
         }

   
		  } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
		}
	

	private static int getHowManythere(String permit_id) {
		// TODO Auto-generated method stub
		  try {
	            String query = "select Count(*) from PermitsAssignedVehicles where permitID = ?";
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setString(1, permit_id);

	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                 return rs.getInt(1);
	            }

	            pstmt.close();
	            // Do not close the connection here, as it will be closed in the calling method
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	      
		return 0;
	}

	private static int howManyLic(String permit_id) {
		// TODO Auto-generated method stub
		
		 String DriverID = getDriverID(permit_id);
		 String Status= getStatus(DriverID);
		 if(Status.equals("E"))
			 return 2;
		 else if(Status.equals("S"))
		     return 1;
		 else if(Status.equals("V"))
		     return 1;
		 
		return 0;
	}
   

	private static String getDriverID(String permit_id) {
		// TODO Auto-generated method stub 
		try {
        String query = "SELECT driverID FROM DriversObtainPermits WHERE permitID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, permit_id);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getString("driverID");
        }

        pstmt.close();
        // Do not close the connection here, as it will be closed in the calling method
    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
	
	}


	private static String getStatus(String driverID) {
		// TODO Auto-generated method stub
	
			// TODO Auto-generated method stub
	    	try {
	        String query = "SELECT status FROM Drivers WHERE id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, driverID);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("status");
	        }

	        pstmt.close();
	        // Do not close the connection here, as it will be closed in the calling method
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

			return null;
		}



}
