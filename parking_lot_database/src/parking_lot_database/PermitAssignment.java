package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermitAssignment {

    public boolean assignPermitToDrivers(String driverID, String permitID) {
        try {
            Connection connection = ParkingLotDB.initializeDatabase();
            String driverStatus = getDriverStatus(driverID);

            if (driverStatus != null) {
                String sqlQuery = getSqlQuery(driverStatus);

                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                    preparedStatement.setString(1, driverID);
                    preparedStatement.setString(2, permitID);

                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
                }
            } else {
                System.out.println("Driver not found with ID: " + driverID);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getDriverStatus(String driverID) {
        try {
            Connection connection = ParkingLotDB.initializeDatabase();
            String sqlQuery = "SELECT status FROM Drivers WHERE id = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, driverID);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSqlQuery(String driverStatus) {
        switch (driverStatus) {
            case "E":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? " +
                       "WHERE NOT EXISTS (SELECT 1 FROM DriversObtainPermits WHERE driverID = ?) " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?) < 2";
            case "S":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? " +
                       "WHERE NOT EXISTS (SELECT 1 FROM DriversObtainPermits WHERE driverID = ?) " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?) < 1";
            case "V":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? " +
                       "WHERE NOT EXISTS (SELECT 1 FROM DriversObtainPermits WHERE driverID = ?) " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?) < 1";
            default:
                throw new IllegalArgumentException("Invalid driver status: " + driverStatus);
        }
    }
}
