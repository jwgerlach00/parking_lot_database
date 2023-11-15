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
            Citation citation = new Citation();
            ReportGenerator reportGenerator = new ReportGenerator();
            //Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose an operation:");
                System.out.println("1. Driver Operations");
                System.out.println("2. Parking Lot Operations");
                System.out.println("3. Space Operations");
                System.out.println("4. Permit Operations");
                System.out.println("5. Zone Operations");
                System.out.println("6. Vehicle Operations");
                System.out.println("7. Permit Assignment Operations");
                System.out.println("8. Citation Operations"); 
                System.out.println("9. Report Operations");
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
                        performVehicleOperations(vehicle, scanner);
                        break;
                    case 7:
                        performPermitAssignmentOperations(permitAssignment, scanner);
                        break;
                    case 8:
                        performCitationOperations(citation, scanner);
                        break;
                    case 9:
                        performReportOperations(reportGenerator, scanner); 
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
        while (true) {
            System.out.println("Driver Operations:");
            System.out.println("1. Enter Driver Information");
            System.out.println("2. Update Driver Information");
            System.out.println("3. Delete Driver Information");

            int driverChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (driverChoice) {
                case 1:
                    enterDriverInfo(driver, scanner);
                    break;
                case 2:
                    updateDriverInfo(driver, scanner);
                    break;
                case 3:
                    deleteDriverInfo(driver, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void enterDriverInfo(Driver driver, Scanner scanner) {
        // Accept user input for Driver Information
        System.out.println("Enter Driver Information:");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Status (S/E/V): ");
        String status = scanner.nextLine();

        // Enter Driver Information
        boolean enterSuccess = driver.enterDriverInfo(id, name, status);
        if (enterSuccess) {
            System.out.println("Driver information entered successfully.");
        } else {
            System.out.println("Failed to enter driver information.");
        }
    }

    private static void updateDriverInfo(Driver driver, Scanner scanner) {
        // Accept user input for Driver Information
        System.out.println("Enter Updated Driver Information:");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Updated Name: ");
        String name = scanner.nextLine();
        System.out.print("Updated Status (S/E/V): ");
        String status = scanner.nextLine();

        // Update Driver Information
        boolean updateSuccess = driver.updateDriverInfo(id, name, status);
        if (updateSuccess) {
            System.out.println("Driver information updated successfully.");
        } else {
            System.out.println("Failed to update driver information.");
        }
    }

    
    private static void deleteDriverInfo(Driver driver, Scanner scanner) {
        // Accept user input for Driver Information
        System.out.println("Enter ID to Delete Driver Information:");
        String id = scanner.nextLine();

        // Delete Driver Information
        boolean deleteSuccess = driver.deleteDriverInfo(id);
        if (deleteSuccess) {
            System.out.println("Driver information deleted successfully.");
        } else {
            System.out.println("Failed to delete driver information.");
        }
    }

    private static void performParkingLotOperations(ParkingLot parkingLot, Scanner scanner) {
        while (true) {
            System.out.println("Parking Lot Operations:");
            System.out.println("1. Enter Parking Lot Information");
            System.out.println("2. Update Parking Lot Information");
            System.out.println("3. Delete Parking Lot Information");
            System.out.println("0. Back to Main Menu");

            int parkingLotChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (parkingLotChoice) {
                case 1:
                    enterParkingLotInfo(parkingLot, scanner);
                    break;
                case 2:
                    updateParkingLotInfo(parkingLot, scanner);
                    break;
                case 3:
                    deleteParkingLotInfo(parkingLot, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void enterParkingLotInfo(ParkingLot parkingLot, Scanner scanner) {
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
    }

    private static void updateParkingLotInfo(ParkingLot parkingLot, Scanner scanner) {
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
    }

    private static void deleteParkingLotInfo(ParkingLot parkingLot, Scanner scanner) {
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
        while (true) {
            System.out.println("Space Operations:");
            System.out.println("1. Enter Space Information");
            System.out.println("2. Update Space Information");
            System.out.println("3. Delete Space Information");
            System.out.println("0. Back to Main Menu");

            int spaceChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (spaceChoice) {
                case 1:
                    enterSpaceInfo(space, scanner);
                    break;
                case 2:
                    updateSpaceInfo(space, scanner);
                    break;
                case 3:
                    deleteSpaceInfo(space, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void enterSpaceInfo(Space space, Scanner scanner) {
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
    }

    private static void updateSpaceInfo(Space space, Scanner scanner) {
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
    }

    private static void deleteSpaceInfo(Space space, Scanner scanner) {
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
        while (true) {
            System.out.println("Permit Operations:");
            System.out.println("1. Enter Permit Information");
            System.out.println("2. Update Permit Information");
            System.out.println("3. Delete Permit Information");
            System.out.println("0. Back to Main Menu");

            int permitChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (permitChoice) {
                case 1:
                    enterPermitInfo(permit, scanner);
                    break;
                case 2:
                    updatePermitInfo(permit, scanner);
                    break;
                case 3:
                    deletePermitInfo(permit, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void enterPermitInfo(Permit permit, Scanner scanner) {
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
    }

    private static void updatePermitInfo(Permit permit, Scanner scanner) {
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

        // You need to obtain these values from user input or other sources
        System.out.print("New Parking Lot Name: ");
        String permitParkingLotName = scanner.nextLine();
        System.out.print("New Parking Lot Address: ");
        String permitParkingLotAddress = scanner.nextLine();
        System.out.print("New Zone ID: ");
        String permitZoneID = scanner.nextLine();

        boolean updatePermitSuccess = permit.updatePermitInfo(updatePermitID, newPermitType, newStartDate,
                newExpirationDate, newExpirationTime, permitParkingLotName, permitParkingLotAddress, permitZoneID,
                newSpaceType);

        if (updatePermitSuccess) {
            System.out.println("Permit information updated successfully.");
        } else {
            System.out.println("Failed to update Permit information.");
        }
    }

    private static void deletePermitInfo(Permit permit, Scanner scanner) {
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
        while (true) {
            System.out.println("Zone Operations:");
            System.out.println("1. Enter Zone Information");
            System.out.println("2. Update Zone Information");
            System.out.println("3. Delete Zone Information");
            System.out.println("0. Back to Main Menu");

            int zoneChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (zoneChoice) {
                case 1:
                    enterZoneInfo(zone, scanner);
                    break;
                case 2:
                    updateZoneInfo(zone, scanner);
                    break;
                case 3:
                    deleteZoneInfo(zone, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void enterZoneInfo(Zone zone, Scanner scanner) {
        System.out.println("\nEnter Zone Information:");
        System.out.print("Zone ID: ");
        String zoneID = scanner.nextLine();
        System.out.print("Parking Lot Name: ");
        String zoneParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String zoneParkingLotAddress = scanner.nextLine();

        boolean enterZoneSuccess = zone.enterZoneInfo(zoneID, zoneParkingLotName, zoneParkingLotAddress);
        if (enterZoneSuccess) {
            System.out.println("Zone information entered successfully.");
        } else {
            System.out.println("Failed to enter Zone information.");
        }
    }

    private static void updateZoneInfo(Zone zone, Scanner scanner) {
        System.out.println("\nEnter Updated Zone Information:");
        System.out.print("Old Zone ID: ");
        String oldZoneID = scanner.nextLine();
        System.out.print("New Zone ID: ");
        String newZoneID = scanner.nextLine();

        System.out.print("Parking Lot Name: ");
        String zoneParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String zoneParkingLotAddress = scanner.nextLine();

        boolean updateZoneSuccess = zone.updateZoneInfo(oldZoneID, newZoneID, zoneParkingLotName, zoneParkingLotAddress);
        if (updateZoneSuccess) {
            System.out.println("Zone information updated successfully.");
        } else {
            System.out.println("Failed to update Zone information.");
        }
    }

    private static void deleteZoneInfo(Zone zone, Scanner scanner) {
        System.out.println("\nEnter Zone Information to Delete:");
        System.out.print("Zone ID: ");
        String deleteZoneID = scanner.nextLine();

        System.out.print("Parking Lot Name: ");
        String zoneParkingLotName = scanner.nextLine();
        System.out.print("Parking Lot Address: ");
        String zoneParkingLotAddress = scanner.nextLine();

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
        System.out.print("Enter Driver Status (Employee/Driver/Visitor): ");
        String driverStatus = scanner.nextLine();

        boolean assignPermitSuccess = permitAssignment.assignPermitToDrivers(driverID, permitID, driverStatus);
        if (assignPermitSuccess) {
            System.out.println("Permit assigned to drivers successfully.");
        } else {
            System.out.println("Failed to assign permit to drivers.");
        }
    }

    
    private static void performCitationOperations(Citation citation, Scanner scanner) {
        while (true) {
            System.out.println("Citation Operations:");
            System.out.println("1. Delete Citation");
            System.out.println("2. Detect Parking Violation");
            System.out.println("3. Drivers Appeal Citations");
            System.out.println("4. Drivers Pay Citations");
            System.out.println("5. Generate New Citation");
            System.out.println("6. Update Citation Information");
            System.out.println("0. Back to Main Menu");

            int citationChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (citationChoice) {
                case 1:
                    deleteCitation(citation, scanner);
                    break;
                case 2:
                    detectParkingViolation(citation, scanner);
                    break;
                case 3:
                    driversAppealCitations(citation, scanner);
                    break;
                case 4:
                    driversPayCitations(citation, scanner);
                    break;
                case 5:
                    generateNewCitation(citation, scanner);
                    break;
                case 6:
                    updateCitationInformation(citation, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void deleteCitation(Citation citation, Scanner scanner) {
        System.out.print("Enter Citation Number to Delete: ");
        String citationNum = scanner.nextLine();

        boolean deleteCitationSuccess = citation.deleteCitation(citationNum);
        if (deleteCitationSuccess) {
            System.out.println("Citation deleted successfully.");
        } else {
            System.out.println("Failed to delete citation.");
        }
    }

    private static void detectParkingViolation(Citation citation, Scanner scanner) {
        System.out.print("Enter License Number: ");
        String licenseNum = scanner.nextLine();
        System.out.print("Enter Parking Lot Name: ");
        String parkingLotName = scanner.nextLine();
        System.out.print("Enter Parking Lot Address: ");
        String parkingLotAddress = scanner.nextLine();
        System.out.print("Enter Zone ID: ");
        String zoneID = scanner.nextLine();

        boolean violationDetected = citation.detectParkingViolation(licenseNum, parkingLotName, parkingLotAddress, zoneID);
        if (violationDetected) {
            System.out.println("Parking violation detected.");
        } else {
            System.out.println("No parking violation detected.");
        }
    }

    private static void driversAppealCitations(Citation citation, Scanner scanner) {
        System.out.print("Enter Citation Number for Appeal: ");
        String citationNum = scanner.nextLine();

        boolean appealSuccess = citation.appealCitation(citationNum);
        if (appealSuccess) {
            System.out.println("Citation appeal requested successfully.");
        } else {
            System.out.println("Failed to request citation appeal.");
        }
    }

    private static void driversPayCitations(Citation citation, Scanner scanner) {
        System.out.print("Enter Citation Number for Payment: ");
        String citationNum = scanner.nextLine();

        boolean paySuccess = citation.payCitation(citationNum);
        if (paySuccess) {
            System.out.println("Citation payment processed successfully.");
        } else {
            System.out.println("Failed to process citation payment.");
        }
    }

    private static void generateNewCitation(Citation citation, Scanner scanner) {
        System.out.println("Enter New Citation Information:");

        // Get user input for new citation details
        System.out.print("Citation Number: ");
        String citationNum = scanner.nextLine();

        System.out.print("Citation Date (YYYY-MM-DD): ");
        String citationDate = scanner.nextLine();

        System.out.print("Citation Time (HH:mm:ss): ");
        String citationTime = scanner.nextLine();

        System.out.print("Vehicle License Number: ");
        String vehicleLicenseNum = scanner.nextLine();

        System.out.print("Vehicle Model: ");
        String vehicleModel = scanner.nextLine();

        System.out.print("Vehicle Color: ");
        String vehicleColor = scanner.nextLine();

        System.out.print("Parking Lot Name: ");
        String parkingLotName = scanner.nextLine();

        System.out.print("Parking Lot Address: ");
        String parkingLotAddress = scanner.nextLine();

        System.out.print("Citation Category: ");
        String citationCategory = scanner.nextLine();

        // Call the method in Citation class to generate a new citation
        boolean success = citation.generateNewCitation(citationNum, citationDate, citationTime, vehicleLicenseNum,
                vehicleModel, vehicleColor, parkingLotName, parkingLotAddress, citationCategory);

        if (success) {
            System.out.println("New citation generated successfully.");
        } else {
            System.out.println("Failed to generate a new citation. Please try again.");
        }
    }

    private static void updateCitationInformation(Citation citation, Scanner scanner) {
        System.out.print("Enter Citation Number to Update: ");
        String citationNum = scanner.nextLine();

        System.out.println("Enter Updated Citation Information:");
        System.out.print("New Citation Date: ");
        String newCitationDate = scanner.nextLine();
        System.out.print("New Citation Time: ");
        String newCitationTime = scanner.nextLine();
        System.out.print("New Vehicle License Number: ");
        String newVehicleLicenseNum = scanner.nextLine();
        System.out.print("New Vehicle Model: ");
        String newVehicleModel = scanner.nextLine();
        System.out.print("New Vehicle Color: ");
        String newVehicleColor = scanner.nextLine();
        System.out.print("New Parking Lot Name: ");
        String newParkingLotName = scanner.nextLine();
        System.out.print("New Parking Lot Address: ");
        String newParkingLotAddress = scanner.nextLine();
        System.out.print("New Category: ");
        String newCategory = scanner.nextLine();

        boolean updateSuccess = citation.updateCitation(
                citationNum, newCitationDate, newCitationTime, newVehicleLicenseNum,
                newVehicleModel, newVehicleColor, newParkingLotName, newParkingLotAddress, newCategory
        );

        if (updateSuccess) {
            System.out.println("Citation information updated successfully.");
        } else {
            System.out.println("Failed to update citation information.");
        }
    }


    private static void performReportOperations(ReportGenerator reportGenerator, Scanner scanner) {
        while (true) {
            System.out.println("Report Operations:");
            System.out.println("1. Generate Total Citations Report");
            System.out.println("2. List Zones for All Lots");
            System.out.println("3. Count Violation Cars");
            System.out.println("4. Count Employees Permits by Zone");
            System.out.println("5. Get Permit Info by Driver ID or Phone Num");
            System.out.println("6. Return Available Space by Type in Lot");
            System.out.println("0. Back to Main Menu");

            int reportChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (reportChoice) {
                case 1:
                    generateTotalCitationsReport(reportGenerator, scanner);
                    break;
                case 2:
                    listZonesForAllLots(reportGenerator);
                    break;
                case 3:
                    countViolationCars(reportGenerator);
                    break;
                case 4:
                    countEmployeesPermitsByZone(reportGenerator);
                    break;
                case 5:
                    getPermitInfoByDriverIdOrPhoneNum(reportGenerator, scanner);
                    break;
                case 6:
                    returnAvailableSpaceByTypeInLot(reportGenerator, scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void generateTotalCitationsReport(ReportGenerator reportGenerator, Scanner scanner) {
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        reportGenerator.generateTotalCitationsReport(startDate, endDate);
    }

    private static void listZonesForAllLots(ReportGenerator reportGenerator) {
        reportGenerator.listZonesForAllLots();
    }

    private static void countViolationCars(ReportGenerator reportGenerator) {
        reportGenerator.countViolationCars();
    }

    private static void countEmployeesPermitsByZone(ReportGenerator reportGenerator) {
        reportGenerator.countEmployeesPermitsByZone();
    }

    private static void getPermitInfoByDriverIdOrPhoneNum(ReportGenerator reportGenerator, Scanner scanner) {
        System.out.print("Enter Driver ID or Phone Number: ");
        String identifier = scanner.nextLine();

        reportGenerator.getPermitInfoByDriverIdOrPhoneNum(identifier);
    }

    private static void returnAvailableSpaceByTypeInLot(ReportGenerator reportGenerator, Scanner scanner) {
        System.out.print("Enter Space Type: ");
        String spaceType = scanner.nextLine();
        System.out.print("Enter Parking Lot Name: ");
        String parkingLotName = scanner.nextLine();
        System.out.print("Enter Parking Lot Address: ");
        String parkingLotAddress = scanner.nextLine();

        reportGenerator.returnAvailableSpaceByTypeInLot(spaceType, parkingLotName, parkingLotAddress);
    }

}
