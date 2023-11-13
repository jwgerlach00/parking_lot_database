package parking_lot_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ParkingLotDB {
    private static Connection conn;

    public static void initializeDatabase() throws SQLException {
        String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";
        String user = "username";
        String password = "password";

        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Successfully connected to DB");
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
