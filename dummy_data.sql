INSERT INTO Drivers VALUES
	(7729119111, 'Sam BankmanFried', 'V'),
    (266399121, 'John Clay', 'E'),
    (366399121, 'Julia Hicks', 'E'),
    (466399121, 'Ivan Garcia', 'E'),
    (122765234, 'Sachin Tendulkar', 'S'),
    (9194789124, 'Charles Xavier', 'V');

INSERT INTO ParkingLots VALUES
	('Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606'),
    ('Partners Way Deck', '851 Partners Way Raleigh, NC, 27606'),
    ('Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607');
    
INSERT INTO Zones VALUES
	('V', 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606'),
    ('A', 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606'),
    ('AS', 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606'),
    ('V', 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607');
    
INSERT INTO Spaces VALUES
	(1, 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606', 'V', 'Regular', true),
    (2, 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606', 'V', 'Regular', true),
    (3, 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606', 'V', 'Regular', true),
    (4, 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606', 'V', 'Regular', true),
    (5, 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606', 'V', 'Regular', true),
    (1, 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'A', 'Electric', true),
    (2, 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'A', 'Regular', true),
    (3, 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'A', 'Regular', true),
    (4, 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'AS', 'Regular', true),
    (5, 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'AS', 'Compact Car', true),
    (6, 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'AS', 'Compact Car', true),
    (1, 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'V', 'Handicap', true),
    (2, 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'V', 'Handicap', true),
    (3, 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'V', 'Regular', true),
    (4, 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'V', 'Regular', true),
    (5, 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'V', 'Regular', true),
    (6, 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'V', 'Regular', true);

INSERT INTO Vehicles VALUES
    ('SBF', 'GT-R-Nismo', 'Pearl White TriCoat', 'Nissan', '2024'),
    ('Clay1', 'Model S', 'Ultra Red', 'Tesla', '2023'),
    ('Hicks1', 'M2 Coupe', 'Zandvoort Blue', 'BMW', '2024'),
    ('Garcia1', 'Continental GT Speed', 'Blue Fusion', 'Bentley', '2024'),
    ('CRICKET', 'Civic SI', 'Sonic Gray Pearl', 'Honda', '2024'),
    ('PROFX', 'Taycan Sport Turismo', 'Frozenblue Metallic', 'Porsche', '2024');
    
INSERT INTO Permits VALUES
    ('VSBF1C', '2023-01-01', '2024-01-01', '06:00:00', 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606', 'V', 'Commuter', 'Regular'),
    ('EJC1R', '2010-01-01', '2030-01-01', '06:00:00', 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'A', 'Residential', 'Electric'),
    ('EJH2C', '2023-01-01', '2024-01-01', '06:00:00', 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'A', 'Commuter', 'Regular'),
    ('EIG3C', '2023-01-01', '2024-01-01', '06:00:00', 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'A', 'Commuter', 'Regular'),
    ('SST1R', '2022-01-01', '2023-09-30', '06:00:00', 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'AS', 'Residential', 'Compact Car'),
    ('VCX1SE', '2023-01-01', '2023-11-15', '06:00:00', 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'V', 'Special event', 'Handicap');
    
INSERT INTO DriversOwnVehicles VALUES
	('7729119111', 'SBF'),
    ('266399121', 'Clay1'),
    ('366399121', 'Hicks1'),
    ('466399121', 'Garcia1'),
    ('122765234', 'CRICKET'),
    ('9194789124', 'PROFX');
    
INSERT INTO DriversObtainPermits VALUES
	('7729119111', 'VSBF1C'),
    ('266399121', 'EJC1R'),
    ('366399121', 'EJH2C'),
    ('466399121', 'EIG3C'),
    ('122765234', 'SST1R'),
    ('9194789124', 'VCX1SE');

INSERT INTO PermitsAssignedVehicles VALUES
	('VSBF1C', 'SBF'),
    ('EJC1R', 'Clay1'),
    ('EJH2C', 'Hicks1'),
    ('EIG3C', 'Garcia1'),
    ('SST1R', 'CRICKET'),
    ('VCX1SE', 'PROFX');
    
INSERT INTO CitationCategoryFees VALUES
	('Invalid Permit', 25),
    ('Expired Permit', 30),
    ('No Permit', 40);
    
INSERT INTO Citations VALUES
	('NP1', '2021-10-11', '08:00:00', 'VAN-9910', 'Macan GTS', 'Papaya Metallic', 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607', 'No Permit', true, false),
    ('EP1', '2023-10-01', '08:00:00', 'CRICKET', 'Civic SI', 'Sonic Gray Pearl', 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606', 'Expired Permit', false, false);
