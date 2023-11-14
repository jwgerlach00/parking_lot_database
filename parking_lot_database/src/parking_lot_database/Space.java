package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Space {
    private static final String ENTER_SPACE_INFO_SQL_FILE = "/resources/information_processing/enter_space_info.sql";
    private static final String UPDATE_SPACE_INFO_SQL_FILE = "/resources/information_processing/update_space_info.sql";
    private static final String DELETE_SPACE_INFO_SQL_FILE = "/resources/information_processing/delete_space_info.sql";

    public boolean enterSpaceInfo(int spaceNum, String spaceType, boolean availabilityStatus,
                                  String parkingLotName, String parkingLotAddress, String zoneID) {
        return executeUpdateOperation(ENTER_SPACE_INFO_SQL_FILE, spaceNum, spaceType, availabilityStatus,
                parkingLotName, parkingLotAddress, zoneID);
    }

    public boolean updateSpaceInfo(int spaceNum, String newSpaceType, String newZoneID,
                                   String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(UPDATE_SPACE_INFO_SQL_FILE, newSpaceType, newZoneID, spaceNum,
                parkingLotName, parkingLotAddress);
    }

    public boolean deleteSpaceInfo(int spaceNum, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(DELETE_SPACE_INFO_SQL_FILE, spaceNum, parkingLotName, parkingLotAddress);
    }

    private boolean executeUpdateOperation(String sqlFilePath, Object... params) {
        try {
        	Connection connection = ParkingLotDB.initializeDatabase();
            PreparedStatement preparedStatement = getPreparedStatement(connection, sqlFilePath);

            // Set parameters and execute SQL
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private PreparedStatement getPreparedStatement(Connection connection, String sqlFilePath) throws SQLException, IOException {
        String sqlQuery = new String(Files.readAllBytes(Paths.get(getClass().getResource(sqlFilePath).getFile())));
        return connection.prepareStatement(sqlQuery);
    }
}
