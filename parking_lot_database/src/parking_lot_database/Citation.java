package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Citation {
    private static final String DELETE_CITATION_SQL_FILE = "/resources/generating_and_maintaining_citations/delete_citations.sql";
    private static final String DETECT_PARKING_VIOLATION_SQL_FILE = "/resources/generating_and_maintaining_citations/detect_parking_violations_by_checking_if_a_car_has_a_valid_permit_in_the_lot.sql";
    private static final String DRIVERS_APPEAL_CITATIONS_SQL_FILE = "/resources/generating_and_maintaining_citations/drivers_appeal_citations.sql";
    private static final String DRIVERS_PAY_CITATIONS_SQL_FILE = "/resources/generating_and_maintaining_citations/drivers_pay_citations.sql";
    private static final String GENERATE_NEW_CITATION_SQL_FILE = "/resources/generating_and_maintaining_citations/generate_a_new_citation_for_a_parking_violation.sql";
    private static final String UPDATE_CITATION_INFO_SQL_FILE = "/resources/generating_and_maintaining_citations/update_citation_information.sql";

    public boolean deleteCitation(String citationNum) {
        return executeUpdateOperation(DELETE_CITATION_SQL_FILE, citationNum);
    }

    public boolean detectParkingViolation(String licenseNum, String parkingLotName, String parkingLotAddress, String zoneID) {
        return executeDetectViolationOperation(DETECT_PARKING_VIOLATION_SQL_FILE, licenseNum, parkingLotName, parkingLotAddress, zoneID);
    }

    public boolean driversAppealCitations(String citationNum) {
        return executeUpdateOperation(DRIVERS_APPEAL_CITATIONS_SQL_FILE, citationNum);
    }

    public boolean driversPayCitations(String citationNum) {
        return executeUpdateOperation(DRIVERS_PAY_CITATIONS_SQL_FILE, citationNum);
    }

    public boolean generateNewCitation(String citationNum, String citationDate, String citationTime, String vehicleLicenseNum,
                                       String vehicleModel, String vehicleColor, String parkingLotName, String parkingLotAddress,
                                       String citationCategory) {
        return executeUpdateOperation(GENERATE_NEW_CITATION_SQL_FILE, citationNum, citationDate, citationTime, vehicleLicenseNum,
                vehicleModel, vehicleColor, parkingLotName, parkingLotAddress, citationCategory);
    }

    public boolean updateCitationInformation(String citationNum, String newCitationDate, String newCitationTime,
                                             String newVehicleLicenseNum, String newVehicleModel, String newVehicleColor,
                                             String newParkingLotName, String newParkingLotAddress, String newCategory) {
        return executeUpdateOperation(UPDATE_CITATION_INFO_SQL_FILE, newCitationDate, newCitationTime, newVehicleLicenseNum,
                newVehicleModel, newVehicleColor, newParkingLotName, newParkingLotAddress, newCategory, citationNum);
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

    private boolean executeDetectViolationOperation(String sqlFilePath, Object... params) {
        try {
        	Connection connection = ParkingLotDB.initializeDatabase();
            PreparedStatement preparedStatement = getPreparedStatement(connection, sqlFilePath);

            // Set parameters and execute SQL
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("validPermit");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private PreparedStatement getPreparedStatement(Connection connection, String sqlFilePath) throws SQLException, IOException {
        String sqlQuery = new String(Files.readAllBytes(Paths.get(getClass().getResource(sqlFilePath).getFile())));
        return connection.prepareStatement(sqlQuery);
    }
}
