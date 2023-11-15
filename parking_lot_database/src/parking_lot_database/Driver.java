package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Driver {
    public boolean enterDriverInfo(String id, String name, String status) {
        return executeUpdateOperation(
                "INSERT INTO Drivers VALUES (?, ?, ?)",
                id, name, status);
    }

    public boolean updateDriverInfo(String id, String name, String status) {
        return executeUpdateOperation(
                "UPDATE Drivers SET status=?, name=? WHERE id=?",
                status, name, id);
    }

    public boolean deleteDriverInfo(String id) {
        return executeUpdateOperation("DELETE FROM Drivers WHERE id=?", id);
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
