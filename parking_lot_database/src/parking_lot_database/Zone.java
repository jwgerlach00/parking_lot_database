package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Zone {

    public boolean enterZoneInfo(String zoneID, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(
                "INSERT INTO Zones VALUES (?, ?, ?)",
                zoneID, parkingLotName, parkingLotAddress);
    }

    public boolean updateZoneInfo(String oldZoneID, String newZoneID, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(
                "UPDATE Zones SET zoneID=? WHERE zoneID=? AND parkingLotName=? AND parkingLotAddress=?",
                newZoneID, oldZoneID, parkingLotName, parkingLotAddress);
    }

    public boolean deleteZoneInfo(String zoneID, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(
                "DELETE FROM Zones WHERE zoneID=? AND parkingLotName=? AND parkingLotAddress=?",
                zoneID, parkingLotName, parkingLotAddress);
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
