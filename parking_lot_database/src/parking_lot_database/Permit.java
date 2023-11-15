package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Permit {
    public boolean enterPermitInfo(int permitID, String startDate, String expirationDate, String expirationTime,
                                   String parkingLotName, String parkingLotAddress, String zoneID,
                                   String permitType, String spaceType) {
        return executeUpdateOperation(
                "INSERT INTO Permits VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                permitID, startDate, expirationDate, expirationTime,
                parkingLotName, parkingLotAddress, zoneID, permitType, spaceType);
    }

    public boolean updatePermitInfo(int permitID, String newPermitType, String newStartDate, String newExpirationDate,
                                    String newExpirationTime, String parkingLotName, String parkingLotAddress,
                                    String zoneID, String newSpaceType) {
        return executeUpdateOperation(
                "UPDATE Permits SET startDate=?, expirationDate=?, expirationTime=?, " +
                        "parkingLotName=?, parkingLotAddress=?, zoneID=?, permitType=?, spaceType=? " +
                        "WHERE permitID=?",
                newStartDate, newExpirationDate, newExpirationTime,
                parkingLotName, parkingLotAddress, zoneID, newPermitType, newSpaceType, permitID);
    }

    public boolean deletePermitInfo(int permitID) {
        return executeUpdateOperation("DELETE FROM Permits WHERE permitID=?", permitID);
    }

    private boolean executeUpdateOperation(String sqlQuery, Object... params) {
        try {
            Connection connection = ParkingLotDB.initializeDatabase();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                // Set parameters and execute SQL
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
