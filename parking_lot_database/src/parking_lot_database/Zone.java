package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Zone {
    private static final String ENTER_ZONE_INFO_SQL_FILE = "/sql/information_processing/enter_zone_info.sql";
    private static final String UPDATE_ZONE_INFO_SQL_FILE = "/sql/information_processing/update_zone_info.sql";
    private static final String DELETE_ZONE_INFO_SQL_FILE = "/sql/information_processing/delete_zone_info.sql";

    public boolean enterZoneInfo(String zoneID, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(ENTER_ZONE_INFO_SQL_FILE, zoneID, parkingLotName, parkingLotAddress);
    }

    public boolean updateZoneInfo(String oldZoneID, String newZoneID, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(UPDATE_ZONE_INFO_SQL_FILE, newZoneID, oldZoneID, parkingLotName, parkingLotAddress);
    }

    public boolean deleteZoneInfo(String zoneID, String parkingLotName, String parkingLotAddress) {
        return executeUpdateOperation(DELETE_ZONE_INFO_SQL_FILE, zoneID, parkingLotName, parkingLotAddress);
    }

    private boolean executeUpdateOperation(String sqlFilePath, Object... params) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(connection, sqlFilePath)) {

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
