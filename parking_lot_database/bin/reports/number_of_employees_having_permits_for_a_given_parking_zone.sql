SELECT zoneID, count(zoneID) AS numEmployees
FROM Permits NATURAL JOIN (
    SELECT *
	FROM DriversObtainPermits JOIN Drivers ON id = driverID
	WHERE status='E'
) AS Employees
GROUP BY zoneID;
