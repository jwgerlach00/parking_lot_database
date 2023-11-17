package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class Permit {
	public boolean enterPermitInfo(String permitID, String startDate, String expirationDate, String expirationTime,
			String parkingLotName, String parkingLotAddress, String zoneID, String permitType, String spaceType) {
		try (Connection connection = ParkingLotDB.initializeDatabase()) {
			connection.setAutoCommit(false); // Start transaction

			boolean result = executeUpdateOperation(connection,
					"INSERT INTO Permits VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", permitID, startDate, expirationDate,
					expirationTime, parkingLotName, parkingLotAddress, zoneID, permitType, spaceType);

			// If successful, commit the transaction
			if (result) {
				connection.commit();
			} else {
				connection.rollback(); // Rollback if there is an error
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public boolean updatePermitInfo(String permitID, Scanner scanner) {
		try (Connection connection = ParkingLotDB.initializeDatabase()) {
			connection.setAutoCommit(false); // Start transaction

			// Check if the permitID exists
			boolean permitExists = checkPermitExists(connection, permitID);

			if (!permitExists) {
				System.out.println("Permit with ID " + permitID + " does not exist. Update aborted.");
				connection.rollback(); // Rollback if permit doesn't exist
				return false;
			}
			// scanner.nextLine(); // Consume the newline character
	        System.out.print("New Permit Type: ");
	        String newPermitType = scanner.nextLine();
	        System.out.print("New Start Date: ");
	        String newStartDate = scanner.nextLine();
	        System.out.print("New Expiration Date: ");
	        String newExpirationDate = scanner.nextLine();
	        System.out.print("New Expiration Time: ");
	        String newExpirationTime = scanner.nextLine();
	        System.out.print("New Space Type: ");
	        String newSpaceType = scanner.nextLine();

	        // You need to obtain these values from user input or other sources
	        System.out.print("New Parking Lot Name: ");
	        String parkingLotName = scanner.nextLine();
	        System.out.print("New Parking Lot Address: ");
	        String parkingLotAddress = scanner.nextLine();
	        System.out.print("New Zone ID: ");
	        String zoneID = scanner.nextLine();

			boolean result = executeUpdateOperation(connection,
					"UPDATE Permits SET startDate=?, expirationDate=?, expirationTime=?, "
							+ "parkingLotName=?, parkingLotAddress=?, zoneID=?, permitType=?, spaceType=? "
							+ "WHERE permitID=?",
					newStartDate, newExpirationDate, newExpirationTime, parkingLotName, parkingLotAddress, zoneID,
					newPermitType, newSpaceType, permitID);

// If successful, commit the transaction
			if (result) {
				connection.commit();
			} else {
				connection.rollback(); // Rollback if there is an error
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// method to check if the permitID exists
	private boolean checkPermitExists(Connection connection, String permitID) {
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT 1 FROM Permits WHERE permitID = ?")) {
			preparedStatement.setString(1, permitID);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePermitInfo(String permitID) {
		try (Connection connection = ParkingLotDB.initializeDatabase()) {
			connection.setAutoCommit(false); // Start transaction

			boolean result = executeUpdateOperation(connection, "DELETE FROM Permits WHERE permitID=?", permitID);

			// If successful, commit the transaction
			if (result) {
				connection.commit();
			} else {
				connection.rollback(); // Rollback if there is an error
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean executeUpdateOperation(Connection connection, String sqlQuery, Object... params) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
			// Set parameters and execute SQL
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i + 1, params[i]);
			}

			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
