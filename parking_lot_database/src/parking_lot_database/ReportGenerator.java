package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportGenerator {
    
    public void generateTotalCitationsReport(String startDate, String endDate) {
        executeAndPrintQuery(
                "SELECT parkingLotName, parkingLotAddress, COUNT(*) AS totalNumCitationsAllZones " +
                "FROM Citations " +
                "WHERE citationDate > ? AND citationDate < ? " +
                "GROUP BY parkingLotName, parkingLotAddress;",
                startDate, endDate);
    }

    public void listZonesForAllLots() {
        executeAndPrintQuery(
                "SELECT parkingLotName, parkingLotAddress, zoneID " +
                "FROM Zones;");
    }

    public void countViolationCars() {
        executeAndPrintQuery(
                "SELECT count(DISTINCT vehicleLicenseNum) AS NumCarsInViolation " +
                "FROM Citations " +
                "WHERE paymentStatus = false;");
    }

    public void countEmployeesPermitsByZone() {
        executeAndPrintQuery(
                "SELECT zoneID, count(zoneID) AS numEmployees " +
                "FROM Permits NATURAL JOIN (" +
                "    SELECT * " +
                "    FROM DriversObtainPermits JOIN Drivers ON id = driverID " +
                "    WHERE status='E' " +
                ") AS Employees " +
                "GROUP BY zoneID;");
    }

    public void getPermitInfoByDriverIdOrPhoneNum(String identifier) {
        executeAndPrintQuery(
                "SELECT * " +
                "FROM Permits NATURAL JOIN DriversObtainPermits " +
                "WHERE driverID=?;", identifier);
    }

    public void returnAvailableSpaceByTypeInLot(String spaceType, String parkingLotName, String parkingLotAddress) {
        executeAndPrintQuery(
                "SELECT spaceNum " +
                "FROM Spaces " +
                "WHERE spaceType = ? AND availabilityStatus = true AND parkingLotName = ? AND parkingLotAddress = ? " +
                "LIMIT 1;",
                spaceType, parkingLotName, parkingLotAddress);
    }

    private void executeAndPrintQuery(String query, Object... params) {
        try {
            Connection connection = ParkingLotDB.initializeDatabase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters and execute SQL
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            printResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1)); // Assuming the query returns a single column
        }
    }
}
