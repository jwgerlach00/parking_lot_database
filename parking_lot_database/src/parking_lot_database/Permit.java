package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Permit {
    private static final String ENTER_PERMIT_INFO_SQL_FILE = "/sql/information_processing/enter_permit_info.sql";
    private static final String UPDATE_PERMIT_INFO_SQL_FILE = "/sql/information_processing/update_permit_info.sql";
    private static final String DELETE_PERMIT_INFO_SQL_FILE = "/sql/information_processing/delete_permit_info.sql";
    


    public boolean enterPermitInfo(int permitID, String startDate, String expirationDate, String expirationTime,
                                   String parkingLotName, String parkingLotAddress, String zoneID,
                                   String permitType, String spaceType) {
        return executeUpdateOperation(ENTER_PERMIT_INFO_SQL_FILE, permitID, startDate, expirationDate, expirationTime,
                parkingLotName, parkingLotAddress, zoneID, permitType, spaceType);
    }

    public boolean updatePermitInfo(int permitID, String newPermitType, String newStartDate, String newExpirationDate,
                                    String newExpirationTime, String parkingLotName, String parkingLotAddress,
                                    String zoneID, String newPermitType, String newSpaceType) {
        return executeUpdateOperation(UPDATE_PERMIT_INFO_SQL_FILE, newPermitType, newStartDate, newExpirationDate,
                newExpirationTime, parkingLotName, parkingLotAddress, zoneID, newPermitType, newSpaceType, permitID);
    }

    public boolean deletePermitInfo(int permitID) {
        return executeUpdateOperation(DELETE_PERMIT_INFO_SQL_FILE, permitID);
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
