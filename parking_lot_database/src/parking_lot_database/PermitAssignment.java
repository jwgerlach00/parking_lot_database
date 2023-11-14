package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PermitAssignment {
    private static final String ASSIGN_PERMIT_TO_DRIVERS_SQL_FILE = "/sql/maintaining_permits_and_vehicle_info/assign_permit_to_drivers.sql";

    public boolean assignPermitToDrivers(String driverID, String permitID) {
        return executeUpdateOperation(ASSIGN_PERMIT_TO_DRIVERS_SQL_FILE, driverID, permitID);
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
