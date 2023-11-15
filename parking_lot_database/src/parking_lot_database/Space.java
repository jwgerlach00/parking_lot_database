package parking_lot_database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Space {

    public boolean enterSpaceInfo(int spaceNum, String spaceType, boolean availabilityStatus,
                                  String parkingLotName, String parkingLotAddress, String zoneID) {
        return executeUpdateOperation(
                "INSERT INTO Spaces VALUES (?, ?, ?, ?, ?, ?, ?)",
                spaceNum, parkingLotName, parkingLotAddress, zoneID, spaceType, availabilityStatus, parkingLotName);
    }

    public boolean updateSpaceInfo(int spaceNum, String newSpaceType, String newZoneID,
                                   String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(
                "UPDATE Spaces SET spaceType=?, zoneID=? WHERE spaceNum=? AND parkingLotName=? AND parkingLotAddress=?",
                newSpaceType, newZoneID, spaceNum, parkingLotName, parkingLotAddress);
    }

    public boolean deleteSpaceInfo(int spaceNum, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(
                "DELETE FROM Spaces WHERE spaceNum=? AND parkingLotName=? AND parkingLotAddress=?",
                spaceNum, parkingLotName, parkingLotAddress);
    }

    private boolean executeUpdateOperation(String sql, Object... params) {
        try {
            Connection connection = ParkingLotDB.initializeDatabase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

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
