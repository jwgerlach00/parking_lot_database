package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Vehicle {
    public boolean addVehicle(String licenseNum, String model, String color, String manufacturer, String year) {
        return executeUpdateOperation(
                "INSERT INTO Vehicles VALUES (?, ?, ?, ?, ?)",
                licenseNum, model, color, manufacturer, year);
    }

    public boolean removeVehicle(String licenseNum) {
        return executeUpdateOperation(
                "DELETE FROM Vehicles WHERE licenseNum = ?",
                licenseNum);
    }

    public boolean updateVehicleOwnershipInfo(String driverID, String vehicleLicenseNum) {
        return executeUpdateOperation(
                "UPDATE DriversOwnVehicles SET driverID = ? WHERE vehicleLicenseNum = ?",
                driverID, vehicleLicenseNum);
    }

    public boolean enterVehicleOwnershipInfo(String driverID, String licenseNum) {
//        // Insert into Vehicles
//        boolean vehicleInsertSuccess = executeUpdateOperation(
//                "INSERT INTO Vehicles VALUES (?, ?, ?, ?, ?)",
//                licenseNum, "MODEL", "COLOR", "MANF", "YYYY");

        // Insert into DriversOwnVehicles
        boolean ownershipInsertSuccess = executeUpdateOperation(
                "INSERT INTO DriversOwnVehicles VALUES (?, ?)",
                driverID, licenseNum);

//        return vehicleInsertSuccess && ownershipInsertSuccess;
        return ownershipInsertSuccess;
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
