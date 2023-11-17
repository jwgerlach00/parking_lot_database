package parking_lot_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ParkingLotDB {
    private static Connection conn;

    public static Connection initializeDatabase() throws SQLException {
    	 if (conn == null || conn.isClosed()) {
             String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/skhanap2";
             String user = "skhanap2";
             String password = "200536582";

             conn = DriverManager.getConnection(url, user, password);

             System.out.println("Successfully connected to DB");
         } else {
        	 
             System.out.println("Already connected to DB");
         }

         return conn;
     }

    public static void closeDatabase() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
