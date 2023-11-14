package parking_lot_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportGenerator {
    private static final String TOTAL_CITATIONS_SQL_FILE = "/resources/reports/generate_report_for_total_amount_of_citations_in_all_zones_in_lot_time_range.sql";
    private static final String ZONES_LIST_SQL_FILE = "/resources/reports/list_of_zones_for_each_lot.sql";
    private static final String VIOLATION_CARS_COUNT_SQL_FILE = "/resources/reports/number_of_cars_currently_in_violation.sql";
    private static final String EMPLOYEES_PERMITS_COUNT_SQL_FILE = "/resources/reports/number_of_employees_having_permits_for_a_given_parking_zone.sql";
    private static final String PERMIT_INFO_SQL_FILE = "/resources/reports/permit_info_given_driver_id_or_phone_num.sql";
    private static final String AVAILABLE_SPACE_SQL_FILE = "/resources/reports/return_an_available_space_num_given_a_space_type_in_lot.sql";

    public void generateTotalCitationsReport(String startDate, String endDate) {
        executeAndPrintQuery(TOTAL_CITATIONS_SQL_FILE, startDate, endDate);
    }

    public void listZonesForAllLots() {
        executeAndPrintQuery(ZONES_LIST_SQL_FILE);
    }

    public void countViolationCars() {
        executeAndPrintQuery(VIOLATION_CARS_COUNT_SQL_FILE);
    }

    public void countEmployeesPermitsByZone() {
        executeAndPrintQuery(EMPLOYEES_PERMITS_COUNT_SQL_FILE);
    }

    public void getPermitInfoByDriverIdOrPhoneNum(String identifier) {
        executeAndPrintQuery(PERMIT_INFO_SQL_FILE, identifier);
    }

    public void returnAvailableSpaceByTypeInLot(String spaceType, String parkingLotName, String parkingLotAddress) {
        executeAndPrintQuery(AVAILABLE_SPACE_SQL_FILE, spaceType, parkingLotName, parkingLotAddress);
    }

    private void executeAndPrintQuery(String sqlFilePath, Object... params) {
    	 try {
         	Connection connection = ParkingLotDB.initializeDatabase();
             PreparedStatement preparedStatement = getPreparedStatement(connection, sqlFilePath);
             
            // Set parameters and execute SQL
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            printResultSet(resultSet);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1)); // Assuming the query returns a single column
        }
    }

    private PreparedStatement getPreparedStatement(Connection connection, String sqlFilePath) throws SQLException, IOException {
        String sqlQuery = new String(Files.readAllBytes(Paths.get(getClass().getResource(sqlFilePath).getFile())));
        return connection.prepareStatement(sqlQuery);
    }
}
