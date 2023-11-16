package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Permit {
    public boolean enterPermitInfo(String permitID, String startDate, String expirationDate, String expirationTime,
                                   String parkingLotName, String parkingLotAddress, String zoneID,
                                   String permitType, String spaceType) {
        try (Connection connection = ParkingLotDB.initializeDatabase()) {
            connection.setAutoCommit(false); // Start transaction

            boolean result = executeUpdateOperation(
                connection,
                "INSERT INTO Permits VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                permitID, startDate, expirationDate, expirationTime,
                parkingLotName, parkingLotAddress, zoneID, permitType, spaceType);

            // If successful, commit the transaction
            if (result) {
                connection.commit();
            } else {
                connection.rollback(); // Rollback if there is an error
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePermitInfo(String permitID, String newPermitType, String newStartDate, String newExpirationDate,
                                    String newExpirationTime, String parkingLotName, String parkingLotAddress,
                                    String zoneID, String newSpaceType) {
        try (Connection connection = ParkingLotDB.initializeDatabase()) {
            connection.setAutoCommit(false); // Start transaction

            boolean result = executeUpdateOperation(
                connection,
                "UPDATE Permits SET startDate=?, expirationDate=?, expirationTime=?, " +
                        "parkingLotName=?, parkingLotAddress=?, zoneID=?, permitType=?, spaceType=? " +
                        "WHERE permitID=?",
                newStartDate, newExpirationDate, newExpirationTime,
                parkingLotName, parkingLotAddress, zoneID, newPermitType, newSpaceType, permitID);

            // If successful, commit the transaction
            if (result) {
                connection.commit();
            } else {
                connection.rollback(); // Rollback if there is an error
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePermitInfo(String permitID) {
        try (Connection connection = ParkingLotDB.initializeDatabase()) {
            connection.setAutoCommit(false); // Start transaction

            boolean result = executeUpdateOperation(
                connection,
                "DELETE FROM Permits WHERE permitID=?",
                permitID);

            // If successful, commit the transaction
            if (result) {
                connection.commit();
            } else {
                connection.rollback(); // Rollback if there is an error
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeUpdateOperation(Connection connection, String sqlQuery, Object... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            // Set parameters and execute SQL
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
