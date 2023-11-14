package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Vehicle {
    private static final String ADD_VEHICLE_SQL_FILE = "/sql/maintaining_permits_and_vehicle_info/add_vehicle.sql";
    private static final String REMOVE_VEHICLE_SQL_FILE = "/sql/maintaining_permits_and_vehicle_info/remove_vehicle.sql";
    private static final String UPDATE_VEHICLE_OWNERSHIP_INFO_SQL_FILE = "/sql/maintaining_permits_and_vehicle_info/update_vehicle_ownership_info.sql";
    private static final String ENTER_VEHICLE_OWNERSHIP_INFO_SQL_FILE = "/sql/maintaining_permits_and_vehicle_info/enter_vehicle_ownership_info.sql";



    public boolean addVehicle(String licenseNum, String model, String color, String manufacturer, String year) {
        return executeUpdateOperation(ADD_VEHICLE_SQL_FILE, licenseNum, model, color, manufacturer, year);
    }

    public boolean removeVehicle(String licenseNum) {
        return executeUpdateOperation(REMOVE_VEHICLE_SQL_FILE, licenseNum);
    }

    public boolean updateVehicleOwnershipInfo(String driverID, String vehicleLicenseNum) {
        return executeUpdateOperation(UPDATE_VEHICLE_OWNERSHIP_INFO_SQL_FILE, driverID, vehicleLicenseNum);
    }
    
    public boolean enterVehicleOwnershipInfo(String driverID, String licenseNum) {
        return executeUpdateOperation(ENTER_VEHICLE_OWNERSHIP_INFO_SQL_FILE, driverID, licenseNum);
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
