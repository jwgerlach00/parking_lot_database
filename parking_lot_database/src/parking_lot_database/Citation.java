package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Citation {
    public boolean generateNewCitation(String citationNum, String citationDate, String citationTime, String vehicleLicenseNum,
                                       String vehicleModel, String vehicleColor, String parkingLotName, String parkingLotAddress,
                                       String citationCategory) {
        return executeUpdateOperation(
                "INSERT INTO Citations VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, false, false)",
                citationNum, citationDate, citationTime, vehicleLicenseNum,
                vehicleModel, vehicleColor, parkingLotName, parkingLotAddress, citationCategory);
    }

    public boolean deleteCitation(String citationNum) {
        return executeUpdateOperation("DELETE FROM Citations WHERE citationNum = ?", citationNum);
    }

    public boolean appealCitation(String citationNum) {
        return executeUpdateOperation("UPDATE Citations SET appealRequested = true WHERE citationNum = ?", citationNum);
    }
    
    public boolean payCitationIfAppealed(String citationNum) {
    	return executeUpdateOperation("UPDATE Citations SET paid=true WHERE citationNum=? AND appealRequested=true", citationNum);
    }

    public boolean payCitation(String citationNum) {
        return executeUpdateOperation("UPDATE Citations SET paid = true WHERE citationNum = ?", citationNum);
    }

    public boolean updateCitation(String citationNum, String newCitationDate, String newCitationTime,
                                  String newVehicleLicenseNum, String newVehicleModel, String newVehicleColor,
                                  String newParkingLotName, String newParkingLotAddress, String newCategory,
                                  Boolean newPaid, Boolean newAppealRequested) {
        try (Connection connection = ParkingLotDB.initializeDatabase()) {
            connection.setAutoCommit(false); // Start transaction

            boolean result = executeUpdateOperation(
                    connection,
                    "UPDATE Citations SET citationDate = ?, citationTime = ?, vehicleLicenseNum = ?, " +
                            "vehicleModel = ?, vehicleColor = ?, parkingLotName = ?, parkingLotAddress = ?, " +
                            "category = ?, paid = ?, appealRequested = ? WHERE citationNum = ?",
                    newCitationDate, newCitationTime, newVehicleLicenseNum,
                    newVehicleModel, newVehicleColor, newParkingLotName, newParkingLotAddress,
                    newCategory, newPaid, newAppealRequested, citationNum);

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

    public boolean detectParkingViolation(String licenseNum, String parkingLotName, String parkingLotAddress, String zoneID) {
        try (Connection connection = ParkingLotDB.initializeDatabase()) {
            connection.setAutoCommit(false); // Start transaction

            boolean hasValidPermit = executeDetectViolationOperation(
                    connection,
                    "SELECT count(*) >= 1 AS validPermit " +
                            "FROM PermitsAssignedVehicles NATURAL JOIN Permits " +
                            "WHERE licenseNum=? AND parkingLotName=? AND parkingLotAddress=? AND zoneID=? " +
                            "AND (expirationDate > CURRENT_DATE OR (expirationDate = CURRENT_DATE AND expirationTime > CURRENT_TIME))",
                    licenseNum, parkingLotName, parkingLotAddress, zoneID);

            // If successful, commit the transaction
            if (hasValidPermit) {
                connection.commit();
            } else {
                connection.rollback(); // Rollback if there is an error
            }

            return hasValidPermit;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeUpdateOperation(String sqlQuery, Object... params) {
        try (Connection connection = ParkingLotDB.initializeDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
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

    private boolean executeDetectViolationOperation(Connection connection, String sql, Object... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters and execute SQL
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getBoolean("validPermit");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
