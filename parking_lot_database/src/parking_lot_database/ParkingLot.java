package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParkingLot {
    public boolean enterParkingLotInfo(String name, String address) {
        return executeUpdateOperation(
                "INSERT INTO ParkingLots VALUES (?, ?)",
                name, address);
    }

    public boolean updateParkingLotInfo(String oldName, String oldAddress, String newName, String newAddress) {
        return executeUpdateOperation(
                "UPDATE ParkingLots SET name=?, address=? WHERE name=? AND address=?",
                newName, newAddress, oldName, oldAddress);
    }

    public boolean deleteParkingLotInfo(String name, String address) {
        return executeUpdateOperation(
                "DELETE FROM ParkingLots WHERE name=? AND address=?",
                name, address);
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