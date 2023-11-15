package parking_lot_database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PermitAssignment {

    public boolean assignPermitToDrivers(String driverID, String permitID, String driverStatus) {
        try {
            Connection connection = ParkingLotDB.initializeDatabase();
            String sqlQuery = getSqlQuery(driverStatus);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, driverID);
                preparedStatement.setString(2, permitID);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getSqlQuery(String driverStatus) {
        switch (driverStatus) {
            case "Employee":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? FROM dual " +
                       "WHERE NOT EXISTS (SELECT * FROM DriversObtainPermits WHERE driverID = ?) " +
                       "HAVING COUNT(*) < 2";
            case "Student":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? FROM dual " +
                       "WHERE NOT EXISTS (SELECT * FROM DriversObtainPermits WHERE driverID = ?) " +
                       "HAVING COUNT(*) < 1";
            case "Visitor":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? FROM dual " +
                       "WHERE NOT EXISTS (SELECT * FROM DriversObtainPermits WHERE driverID = ?) " +
                       "HAVING COUNT(*) < 1";
            default:
                throw new IllegalArgumentException("Invalid driver status: " + driverStatus);
        }
    }
}
