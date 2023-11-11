package parking_lot_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingLotDB {

    public static void main(String args[]) {
        Connection conn = null;
        String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";
        String user = "username";
        String pswd = "password";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, pswd);

            // Perform your database operations here using PreparedStatement and ResultSet

            System.out.println("Successfully connected to DB");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close ResultSet first
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }

                // Close PreparedStatement
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }

                // Close Connection
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    System.out.println("Connection closed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}