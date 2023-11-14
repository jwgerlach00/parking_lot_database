package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParkingLot {
    private static final String ENTER_PARKING_LOT_INFO_SQL_FILE = "/resources/information_processing/enter_parking_lot_info.sql";
    private static final String UPDATE_PARKING_LOT_INFO_SQL_FILE = "/resources/information_processing/update_parking_lot_info.sql";
    private static final String DELETE_PARKING_LOT_INFO_SQL_FILE = "/resources/information_processing/delete_parking_lot_info.sql";

    public boolean enterParkingLotInfo(String name, String address) {
        return executeUpdateOperation(ENTER_PARKING_LOT_INFO_SQL_FILE, name, address);
    }

    public boolean updateParkingLotInfo(String oldName, String oldAddress, String newName, String newAddress) {
        return executeUpdateOperation(UPDATE_PARKING_LOT_INFO_SQL_FILE, newName, newAddress, oldName, oldAddress);
    }

    public boolean deleteParkingLotInfo(String name, String address) {
        return executeUpdateOperation(DELETE_PARKING_LOT_INFO_SQL_FILE, name, address);
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
