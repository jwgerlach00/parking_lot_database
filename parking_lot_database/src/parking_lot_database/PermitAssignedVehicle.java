package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PermitAssignedVehicle {

    public boolean assignVehicleToPermit(String permitID, String licenseNum) {
        Connection connection = null;
        try {
            connection = ParkingLotDB.initializeDatabase();

            // Disable auto-commit to start a transaction
            connection.setAutoCommit(false);

            String driverStatus = getDriverStatusForPermit(permitID);

            if (driverStatus != null) {
                String sqlQuery = getSqlQueryForStatus(driverStatus);

                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                    preparedStatement.setString(1, permitID);
                    preparedStatement.setString(2, licenseNum);

                    for (int i = 3; i <= 4; i++) {
                        preparedStatement.setString(i, permitID);
                    }

                    int rowsAffected = preparedStatement.executeUpdate();

                    // Commit the transaction if everything is successful
                    connection.commit();

                    return rowsAffected > 0;
                }
            } else {
                System.out.println("No driver found for permit with ID: " + permitID);
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

    private String getDriverStatusForPermit(String permitID) {
        try {
            Connection connection = ParkingLotDB.initializeDatabase();
            String sqlQuery = "SELECT status FROM Drivers WHERE id IN (SELECT driverID FROM DriversObtainPermits WHERE permitID = ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, permitID);

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

    private String getSqlQueryForStatus(String driverStatus) {
        switch (driverStatus) {
            case "E":
                return "INSERT INTO PermitsAssignedVehicles (permitID, licenseNum) " +
                        "SELECT ?, ? " +
                        "WHERE NOT EXISTS (SELECT 1 FROM PermitsAssignedVehicles WHERE permitID = ?) " +
                        "AND (SELECT COUNT(*) FROM PermitsAssignedVehicles WHERE permitID = ?) < 2";
            case "S":
            case "V":
                return "INSERT INTO PermitsAssignedVehicles (permitID, licenseNum) " +
                        "SELECT ?, ? " +
                        "WHERE NOT EXISTS (SELECT 1 FROM PermitsAssignedVehicles WHERE permitID = ?) " +
                        "AND (SELECT COUNT(*) FROM PermitsAssignedVehicles WHERE permitID = ?) < 1";
            default:
                throw new IllegalArgumentException("Invalid driver status: " + driverStatus);
        }
    }
}
