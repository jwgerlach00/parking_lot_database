package parking_lot_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize database connection
            ParkingLotDB.initializeDatabase();

            // Perform sample operations using DriverDAO
            Driver driver = new Driver();

            // Accept user input for Driver Information
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Driver Information:");
            System.out.print("Phone Number: ");
            String phoneNum = scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Status (S/E/V): ");
            String status = scanner.nextLine();

            // Enter Driver Information
            boolean enterSuccess = driver.enterDriverInfo(phoneNum, name, status);
            if (enterSuccess) {
                System.out.println("Driver information entered successfully.");
            } else {
                System.out.println("Failed to enter driver information.");
            }

            // Update Driver Information
            System.out.println("\nEnter Updated Driver Information:");
            System.out.print("Phone Number: ");
            phoneNum = scanner.nextLine();
            System.out.print("Updated Name: ");
            name = scanner.nextLine();
            System.out.print("Updated Status (S/E/V): ");
            status = scanner.nextLine();

            boolean updateSuccess = driver.updateDriverInfo(phoneNum, name, status);
            if (updateSuccess) {
                System.out.println("Driver information updated successfully.");
            } else {
                System.out.println("Failed to update driver information.");
            }

            // Delete Driver Information
            System.out.println("\nEnter Phone Number to Delete Driver Information:");
            System.out.print("Phone Number: ");
            phoneNum = scanner.nextLine();

            boolean deleteSuccess = driver.deleteDriverInfo(phoneNum);
            if (deleteSuccess) {
                System.out.println("Driver information deleted successfully.");
            } else {
                System.out.println("Failed to delete driver information.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred while processing driver information.");
        } finally {
            // Close database connection
            ParkingLotDB.closeDatabase();
        }
    }
}
