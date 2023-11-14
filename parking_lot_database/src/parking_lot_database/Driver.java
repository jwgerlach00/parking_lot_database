package parking_lot_database;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Driver{
    private static final String ENTER_DRIVER_INFO_SQL_FILE = "/resources/information_processing/enter_driver_info.sql";
    private static final String UPDATE_DRIVER_INFO_SQL_FILE = "/resources/information_processing/update_driver_info.sql";
    private static final String DELETE_DRIVER_INFO_SQL_FILE = "/resources/information_processing/delete_driver_info.sql";

    public boolean enterDriverInfo(String phoneNum, String name, String status) {
        return executeUpdateOperation(ENTER_DRIVER_INFO_SQL_FILE, phoneNum, name, status);
    }

    public boolean updateDriverInfo(String phoneNum, String name, String status) {
        return executeUpdateOperation(UPDATE_DRIVER_INFO_SQL_FILE, status, name, phoneNum);
    }

    public boolean deleteDriverInfo(String phoneNum) {
        return executeUpdateOperation(DELETE_DRIVER_INFO_SQL_FILE, phoneNum);
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

