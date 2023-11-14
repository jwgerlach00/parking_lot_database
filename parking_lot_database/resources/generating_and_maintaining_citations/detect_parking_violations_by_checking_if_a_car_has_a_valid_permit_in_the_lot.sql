SELECT count(*) >= 1 AS validPermit
FROM PermitsAssignedVehicles NATURAL JOIN Permits
WHERE licenseNum='LICENSE_NUM' AND parkingLotName='PARKING_LOT_NAME' AND parkingLotAddress='PARKING_LOT_ADDRESS' AND zoneID='ZONE_ID' AND (expirationDate > 'CURRENT_DATE' OR (expirationDate = 'CURRENT_DATE' AND expirationTime > 'CURRENT_TIME'));