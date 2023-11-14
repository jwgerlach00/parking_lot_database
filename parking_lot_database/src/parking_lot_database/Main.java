package parking_lot_database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
  
        try {
        	Scanner scanner = new Scanner(System.in);
            // Initialize database connection
            ParkingLotDB.initializeDatabase();

          
            Driver driver = new Driver();
            Space space = new Space();
            ParkingLot parkingLot = new ParkingLot();
            Permit permit = new Permit();
            Zone zone = new Zone();
            Vehicle vehicle = new Vehicle();
            PermitAssignment permitAssignment = new PermitAssignment();
            //Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose an operation:");
                System.out.println("1. Driver Operations");
                System.out.println("2. Parking Lot Operations");
                System.out.println("3. Space Operations");
                System.out.println("4. Permit Operations");
                System.out.println("5. Zone Operations");
                System.out.println("6. Vehicle Operations"); // Added Vehicle Operations
                System.out.println("7. Permit Assignment Operations");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        performDriverOperations(driver, scanner);
                        break;
                    case 2:
                        performParkingLotOperations(parkingLot, scanner);
                        break;
                    case 3:
                        performSpaceOperations(space, scanner);
                        break;
                    case 4:
                        performPermitOperations(permit, scanner);
                        break;
                    case 5:
                        performZoneOperations(zone, scanner);
                        break;
                    case 6:
                        performVehicleOperations(vehicle, scanner); // Added Vehicle Operations
                        break;
                    case 7:
                        performPermitAssignmentOperations(permitAssignment, scanner); // New addition
                        break;
                    case 0:
                        System.out.println("Exiting program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred while processing information.");
        } finally {
            // Close database connection
            ParkingLotDB.closeDatabase();
        }
    }

    private static void performDriverOperations(Driver driver, Scanner scanner) {
    	// Accept user input for Driver Information
        
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
    }

    private static void performParkingLotOperations(ParkingLot parkingLot, Scanner scanner) {
    	// Enter Parking Lot Information
        System.out.println("\nEnter Parking Lot Information:");
        System.out.print("Name: ");
        String parkingLotName = scanner.nextLine();
        System.out.print("Address: ");
        String parkingLotAddress = scanner.nextLine();

        boolean enterParkingLotSuccess = parkingLot.enterParkingLotInfo(parkingLotName, parkingLotAddress);
        if (enterParkingLotSuccess) {
            System.out.println("Parking Lot information entered successfully.");
        } else {
            System.out.println("Failed to enter Parking Lot information.");
        }

        // Update Parking Lot Information
        System.out.println("\nEnter Updated Parking Lot Information:");
        System.out.print("Old Name: ");
        String oldParkingLotName = scanner.nextLine();
        System.out.print("Old Address: ");
        String oldParkingLotAddress = scanner.nextLine();
        System.out.print("New Name: ");
        String newParkingLotName = scanner.nextLine();
        System.out.print("New Address: ");
        String newParkingLotAddress = scanner.nextLine();

        boolean updateParkingLotSuccess = parkingLot.updateParkingLotInfo(oldParkingLotName, oldParkingLotAddress, newParkingLotName, newParkingLotAddress);
        if (updateParkingLotSuccess) {
            System.out.println("Parking Lot information updated successfully.");
        } else {
            System.out.println("Failed to update Parking Lot information.");
        }

        // Delete Parking Lot Information
        System.out.println("\nEnter Parking Lot Information to Delete:");
        System.out.print("Name: ");
        String deleteParkingLotName = scanner.nextLine();
        System.out.print("Address: ");
        String deleteParkingLotAddress = scanner.nextLine();

        boolean deleteParkingLotSuccess = parkingLot.deleteParkingLotInfo(deleteParkingLotName, deleteParkingLotAddress);
        if (deleteParkingLotSuccess) {
            System.out.println("Parking Lot information deleted successfully.");
        } else {
            System.out.println("Failed to delete Parking Lot information.");
        }

    }

    private static void performSpaceOperations(Space space, Scanner scanner) {
    	// Enter Space Information
        System.out.println("\nEnter Space Information:");
        System.out.print("Space Number: ");
        int spaceNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Space Type: ");
        String spaceType = scanner.nextLine();
        System.out.print("Availability Status: ");
        boolean availabilityStatus = scanner.nextBoolean();
        System.out.print("Parking Lot Name: ");
        String spaceParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String spaceParkingLotAddress = scanner.nextLine();
        System.out.print("Zone ID: ");
        String spaceZoneID = scanner.nextLine();

        boolean enterSpaceSuccess = space.enterSpaceInfo(spaceNumber, spaceType, availabilityStatus, spaceParkingLotName, spaceParkingLotAddress, spaceZoneID);
        if (enterSpaceSuccess) {
            System.out.println("Space information entered successfully.");
        } else {
            System.out.println("Failed to enter Space information.");
        }

        // Update Space Information
        System.out.println("\nEnter Updated Space Information:");
        System.out.print("Space Number: ");
        int updateSpaceNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("New Space Type: ");
        String updateSpaceType = scanner.nextLine();
        System.out.print("New Zone ID: ");
        String updateSpaceZoneID = scanner.nextLine();
        System.out.print("Parking Lot Name: ");
        String updateSpaceParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String updateSpaceParkingLotAddress = scanner.nextLine();

        boolean updateSpaceSuccess = space.updateSpaceInfo(updateSpaceNumber, updateSpaceType, updateSpaceZoneID, updateSpaceParkingLotName, updateSpaceParkingLotAddress);
        if (updateSpaceSuccess) {
            System.out.println("Space information updated successfully.");
        } else {
            System.out.println("Failed to update Space information.");
        }

        // Delete Space Information
        System.out.println("\nEnter Space Information to Delete:");
        System.out.print("Space Number: ");
        int deleteSpaceNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Parking Lot Name: ");
        String deleteSpaceParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String deleteSpaceParkingLotAddress = scanner.nextLine();

        boolean deleteSpaceSuccess = space.deleteSpaceInfo(deleteSpaceNumber, deleteSpaceParkingLotName, deleteSpaceParkingLotAddress);
        if (deleteSpaceSuccess) {
            System.out.println("Space information deleted successfully.");
        } else {
            System.out.println("Failed to delete Space information.");
        }
    }

    private static void performPermitOperations(Permit permit, Scanner scanner) {
    	// Enter Permit Information
        System.out.println("\nEnter Permit Information:");
        System.out.print("Permit ID: ");
        int permitID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Start Date: ");
        String startDate = scanner.nextLine();
        System.out.print("Expiration Date: ");
        String expirationDate = scanner.nextLine();
        System.out.print("Expiration Time: ");
        String expirationTime = scanner.nextLine();
        System.out.print("Parking Lot Name: ");
        String permitParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String permitParkingLotAddress = scanner.nextLine();
        System.out.print("Zone ID: ");
        String permitZoneID = scanner.nextLine();
        System.out.print("Permit Type: ");
        String permitType = scanner.nextLine();
        System.out.print("Space Type: ");
        String permitSpaceType = scanner.nextLine();

        boolean enterPermitSuccess = permit.enterPermitInfo(permitID, startDate, expirationDate, expirationTime,
                permitParkingLotName, permitParkingLotAddress, permitZoneID, permitType, permitSpaceType);

        if (enterPermitSuccess) {
            System.out.println("Permit information entered successfully.");
        } else {
            System.out.println("Failed to enter Permit information.");
        }

        // Update Permit Information
        System.out.println("\nEnter Updated Permit Information:");
        System.out.print("Permit ID to Update: ");
        int updatePermitID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
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

        boolean updatePermitSuccess = permit.updatePermitInfo(updatePermitID, newPermitType, newStartDate,
                newExpirationDate, newExpirationTime, permitParkingLotName, permitParkingLotAddress, permitZoneID,
                newSpaceType);

        if (updatePermitSuccess) {
            System.out.println("Permit information updated successfully.");
        } else {
            System.out.println("Failed to update Permit information.");
        }

        // Delete Permit Information
        System.out.println("\nEnter Permit ID to Delete:");
        System.out.print("Permit ID: ");
        int deletePermitID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        boolean deletePermitSuccess = permit.deletePermitInfo(deletePermitID);

        if (deletePermitSuccess) {
            System.out.println("Permit information deleted successfully.");
        } else {
            System.out.println("Failed to delete Permit information.");
        }

    }

    private static void performZoneOperations(Zone zone, Scanner scanner) {
    	// Accept user input for Zone Information
        System.out.println("\nEnter Zone Information:");
        System.out.print("Zone ID: ");
        String zoneID = scanner.nextLine();
        System.out.print("Parking Lot Name: ");
        String zoneParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String zoneParkingLotAddress = scanner.nextLine();

        // Enter Zone Information
        boolean enterZoneSuccess = zone.enterZoneInfo(zoneID, zoneParkingLotName, zoneParkingLotAddress);
        if (enterZoneSuccess) {
            System.out.println("Zone information entered successfully.");
        } else {
            System.out.println("Failed to enter Zone information.");
        }

        // Update Zone Information
        System.out.println("\nEnter Updated Zone Information:");
        System.out.print("Old Zone ID: ");
        String oldZoneID = scanner.nextLine();
        System.out.print("New Zone ID: ");
        String newZoneID = scanner.nextLine();

        boolean updateZoneSuccess = zone.updateZoneInfo(oldZoneID, newZoneID, zoneParkingLotName, zoneParkingLotAddress);
        if (updateZoneSuccess) {
            System.out.println("Zone information updated successfully.");
        } else {
            System.out.println("Failed to update Zone information.");
        }

        // Delete Zone Information
        System.out.println("\nEnter Zone Information to Delete:");
        System.out.print("Zone ID: ");
        String deleteZoneID = scanner.nextLine();

        boolean deleteZoneSuccess = zone.deleteZoneInfo(deleteZoneID, zoneParkingLotName, zoneParkingLotAddress);
        if (deleteZoneSuccess) {
            System.out.println("Zone information deleted successfully.");
        } else {
            System.out.println("Failed to delete Zone information.");
        }
    }
    
 
    private static void performVehicleOperations(Vehicle vehicle, Scanner scanner) {
    	while (true) {
            System.out.println("Vehicle Operations:");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Update Vehicle Ownership Info");
            System.out.println("4. Enter Vehicle Ownership Info");
            System.out.println("0. Back to Main Menu");

            int vehicleChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (vehicleChoice) {
                case 1:
                    addVehicle(vehicle, scanner);
                    break;
                case 2:
                    removeVehicle(vehicle, scanner);
                    break;
                case 3:
                    updateVehicleOwnershipInfo(vehicle, scanner);
                    break;
                case 4:
                    enterVehicleOwnershipInfo(vehicle, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addVehicle(Vehicle vehicle, Scanner scanner) {
    	
        System.out.println("Enter Vehicle Information:");
        System.out.print("License Number: ");
        String licenseNum = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Year: ");
        String year = scanner.nextLine();

        boolean addVehicleSuccess = vehicle.addVehicle(licenseNum, model, color, manufacturer, year);
        if (addVehicleSuccess) {
            System.out.println("Vehicle added successfully.");
        } else {
            System.out.println("Failed to add vehicle.");
        }
    }

    private static void removeVehicle(Vehicle vehicle, Scanner scanner) {
        System.out.print("Enter License Number to Remove Vehicle: ");
        String licenseNum = scanner.nextLine();

        boolean removeVehicleSuccess = vehicle.removeVehicle(licenseNum);
        if (removeVehicleSuccess) {
            System.out.println("Vehicle removed successfully.");
        } else {
            System.out.println("Failed to remove vehicle.");
        }
    }

    private static void updateVehicleOwnershipInfo(Vehicle vehicle, Scanner scanner) {
        System.out.print("Enter Driver ID for Vehicle Ownership Update: ");
        String driverID = scanner.nextLine();
        System.out.print("Enter Vehicle License Number: ");
        String vehicleLicenseNum = scanner.nextLine();

        boolean updateVehicleOwnershipSuccess = vehicle.updateVehicleOwnershipInfo(driverID, vehicleLicenseNum);
        if (updateVehicleOwnershipSuccess) {
            System.out.println("Vehicle ownership info updated successfully.");
        } else {
            System.out.println("Failed to update vehicle ownership info.");
        }
    }

    private static void enterVehicleOwnershipInfo(Vehicle vehicle, Scanner scanner) {
        System.out.print("Enter Driver ID for Vehicle Ownership: ");
        String driverID = scanner.nextLine();
        System.out.print("Enter Vehicle License Number: ");
        String licenseNum = scanner.nextLine();

        boolean enterVehicleOwnershipSuccess = vehicle.enterVehicleOwnershipInfo(driverID, licenseNum);
        if (enterVehicleOwnershipSuccess) {
            System.out.println("Vehicle ownership info entered successfully.");
        } else {
            System.out.println("Failed to enter vehicle ownership info.");
        }
    }
    
    private static void performPermitAssignmentOperations(PermitAssignment permitAssignment, Scanner scanner) {
        while (true) {
            System.out.println("Permit Assignment Operations:");
            System.out.println("1. Assign Permit to Drivers");
            System.out.println("0. Back to Main Menu");

            int permitAssignmentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (permitAssignmentChoice) {
                case 1:
                    assignPermitToDrivers(permitAssignment, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void assignPermitToDrivers(PermitAssignment permitAssignment, Scanner scanner) {
        System.out.print("Enter Driver ID for Permit Assignment: ");
        String driverID = scanner.nextLine();
        System.out.print("Enter Permit ID: ");
        String permitID = scanner.nextLine();

        boolean assignPermitSuccess = permitAssignment.assignPermitToDrivers(driverID, permitID);
        if (assignPermitSuccess) {
            System.out.println("Permit assigned to drivers successfully.");
        } else {
            System.out.println("Failed to assign permit to drivers.");
        }
    }
}