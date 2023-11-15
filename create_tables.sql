DROP TABLE IF EXISTS Spaces;
DROP TABLE IF EXISTS Citations;
DROP TABLE IF EXISTS CitationCategoryFees;
DROP TABLE IF EXISTS DriversObtainPermits;
DROP TABLE IF EXISTS PermitsAssignedVehicles;
DROP TABLE IF EXISTS DriversOwnVehicles;
DROP TABLE IF EXISTS Drivers;
DROP TABLE IF EXISTS Permits;
DROP TABLE IF EXISTS Zones;
DROP TABLE IF EXISTS Vehicles;
DROP TABLE IF EXISTS ParkingLots;


CREATE TABLE Drivers (
	id VARCHAR(10) PRIMARY KEY,
	name VARCHAR(128) NOT NULL,
	status CHAR(1) NOT NULL,
	CHECK (status IN ('S', 'E', 'V'))
);

CREATE TABLE ParkingLots (
	name VARCHAR(128),
	address VARCHAR(128),
	PRIMARY KEY(name, address)
);

CREATE TABLE Zones (
	zoneID CHAR(2),
	parkingLotName VARCHAR(128),
	parkingLotAddress VARCHAR(128),
	PRIMARY KEY(zoneID, parkingLotName, parkingLotAddress),
	FOREIGN KEY(parkingLotName, parkingLotAddress) REFERENCES ParkingLots(name, address) ON UPDATE CASCADE,
	CHECK (zoneID IN ('A', 'B', 'C', 'D', 'AS', 'BS', 'CS', 'DS', 'V'))
);

CREATE TABLE Spaces (
	spaceNum INT,
	parkingLotName VARCHAR(128),
	parkingLotAddress VARCHAR(128),
    zoneID CHAR(2),
	spaceType VARCHAR(11) NOT NULL DEFAULT 'Regular',
	availabilityStatus BOOLEAN NOT NULL,
	PRIMARY KEY(spaceNum, parkingLotName, parkingLotAddress),
	FOREIGN KEY(parkingLotName, parkingLotAddress) REFERENCES ParkingLots(name, address) ON UPDATE CASCADE,
    FOREIGN KEY(zoneID, parkingLotName, parkingLotAddress) REFERENCES Zones(zoneID, parkingLotName, parkingLotAddress) ON UPDATE CASCADE,
	CHECK (spaceType IN ('Electric', 'Handicap', 'Compact Car', 'Regular'))
);

CREATE TABLE Permits (
	permitID VARCHAR(8) PRIMARY KEY,
	startDate DATE NOT NULL,
	expirationDate DATE NOT NULL,
	expirationTime TIME NOT NULL,
	parkingLotName VARCHAR(128),
	parkingLotAddress VARCHAR(128),
    zoneID CHAR(2),
	permitType CHAR(13) NOT NULL,
    spaceType VARCHAR(11) NOT NULL,
	FOREIGN KEY(parkingLotName, parkingLotaddress) REFERENCES ParkingLots(name, address) ON UPDATE CASCADE,
    FOREIGN KEY(zoneID) REFERENCES Zones(zoneID) ON UPDATE CASCADE,
	CHECK (permitType IN ('Residential', 'Commuter', 'Peak hours', 'Special event', 'Park & Ride')),
    CHECK (spaceType IN ('Electric', 'Handicap', 'Compact Car', 'Regular')),
    CHECK (expirationDate >= startDate)
);

CREATE TABLE Vehicles (
	licenseNum CHAR(8) PRIMARY KEY,
	model VARCHAR(128) NOT NULL,
	color VARCHAR(128) NOT NULL,
	manufacturer VARCHAR(128) NOT NULL,
	year YEAR NOT NULL
);

CREATE TABLE CitationCategoryFees (
	category CHAR(14) PRIMARY KEY, 
	fee INT NOT NULL,
	CHECK (category IN ('Invalid Permit', 'Expired Permit', 'No Permit'))
);

CREATE TABLE Citations (
	citationNum VARCHAR(6) PRIMARY KEY,
	citationDate Date NOT NULL,
	citationTime Time NOT NULL,
	vehicleLicenseNum CHAR(8) NOT NULL,
    vehicleModel VARCHAR(128) NOT NULL,
    vehicleColor VARCHAR(128) NOT NULL,
	parkingLotName VARCHAR(128),
	parkingLotAddress VARCHAR(128),
	category CHAR(14),
	paid BOOLEAN NOT NULL DEFAULT false,
	appealRequested BOOLEAN NOT NULL DEFAULT false,
	FOREIGN KEY(parkingLotName, parkingLotAddress) REFERENCES ParkingLots(name, address) ON UPDATE CASCADE,
	FOREIGN KEY(category) REFERENCES CitationCategoryFees(category) ON UPDATE CASCADE
);

CREATE TABLE DriversObtainPermits (
	driverID VARCHAR(10),
	permitID VARCHAR(8) PRIMARY KEY,
	FOREIGN KEY(driverID) REFERENCES Drivers(id) ON UPDATE CASCADE,
	FOREIGN KEY(permitID) REFERENCES Permits(permitID) ON UPDATE CASCADE
);

CREATE TABLE PermitsAssignedVehicles (
	permitID VARCHAR(8),
	licenseNum CHAR(8),
    PRIMARY KEY(permitID, licenseNum),
	FOREIGN KEY(permitID) REFERENCES Permits(permitID) ON UPDATE CASCADE,
	FOREIGN KEY (licenseNum) REFERENCES Vehicles(licenseNum)
ON DELETE CASCADE
ON UPDATE CASCADE;
);

CREATE TABLE DriversOwnVehicles (
	driverID VARCHAR(10),
    vehicleLicenseNum CHAR(8),
    PRIMARY KEY(driverID, vehicleLicenseNum),
    FOREIGN KEY(driverID) REFERENCES Drivers(id) ON UPDATE CASCADE,
    FOREIGN KEY(vehicleLicenseNum) REFERENCES Vehicles(licenseNum) ON UPDATE CASCADE ON DELETE CASCADE
	
);
