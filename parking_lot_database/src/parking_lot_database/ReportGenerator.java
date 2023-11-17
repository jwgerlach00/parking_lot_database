package parking_lot_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
                        "WHERE paid = false;");
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
    
    public void queryForCitationFees() {
        executeAndPrintQuery(
                "SELECT spaceType, vehicleLicenseNum, citationNum, category, " +
                        "CASE WHEN spaceType = 'Handicap' THEN fee / 2 ELSE fee END AS adjustedFee " +
                        "FROM ((Vehicles NATURAL JOIN PermitsAssignedVehicles NATURAL JOIN Permits) " +
                        "RIGHT JOIN Citations ON licenseNum = vehicleLicenseNum) " +
                        "NATURAL JOIN CitationCategoryFees");
    }

    private void executeAndPrintQuery(String query, Object... params) {
        try (Connection connection = ParkingLotDB.initializeDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters and execute SQL
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                printResultSet(resultSet);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void printResultSet(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                do {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(metaData.getColumnName(i) + ": " + resultSet.getString(i) + " ");
                    }
                    System.out.println();
                } while (resultSet.next());
            } else {
                System.out.println("No results found.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }


    private void handleSQLException(SQLException e) {
        e.printStackTrace(); 
    }
}
