package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermitAssignment {

    public boolean assignPermitToDrivers(String driverID, String permitID) {
        Connection connection = null;
        try {
            connection = ParkingLotDB.initializeDatabase();

            // Disable auto-commit to start a transaction
            connection.setAutoCommit(false);

            String driverStatus = getDriverStatus(driverID);

            if (driverStatus != null) {
                String sqlQuery = getSqlQuery(driverStatus);

                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                    preparedStatement.setString(1, driverID);
                    preparedStatement.setString(2, permitID);

                    for (int i = 3; i <= 4; i++) {
                        preparedStatement.setString(i, permitID);
                    }

                    int rowsAffected = preparedStatement.executeUpdate();

                    // Commit the transaction if everything is successful
                    connection.commit();

                    return rowsAffected > 0;
                }
            } else {
                System.out.println("Driver not found with ID: " + driverID);
                return false;
            }

        } catch (SQLException e) {
            // Rollback the transaction in case of an exception
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            // Enable auto-commit and close the connection
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
            }
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
                       "WHERE NOT EXISTS (SELECT 1 FROM DriversObtainPermits WHERE driverID = ? " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?) >= 3) " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ? AND permitType IN ('Special event', 'Park & Ride')) > 0";
            case "S":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? " +
                       "WHERE NOT EXISTS (SELECT 1 FROM DriversObtainPermits WHERE driverID = ?) " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?) >= 2 " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ? AND permitType IN ('Special event', 'Park & Ride')) > 0";
            case "V":
                return "INSERT INTO DriversObtainPermits (driverID, permitID) " +
                       "SELECT ?, ? " +
                       "WHERE NOT EXISTS (SELECT 1 FROM DriversObtainPermits WHERE driverID = ?) " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ?) >= 1 " +
                       "AND (SELECT COUNT(*) FROM DriversObtainPermits WHERE driverID = ? AND permitType IN ('Special event', 'Park & Ride')) > 0";
            default:
                throw new IllegalArgumentException("Invalid driver status: " + driverStatus);
        }
    }
}
