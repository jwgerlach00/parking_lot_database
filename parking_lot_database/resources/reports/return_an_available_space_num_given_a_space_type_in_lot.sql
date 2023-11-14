SELECT spaceNum
FROM Spaces
WHERE spaceType = 'SPACE_TYPE' AND availabilityStatus = true AND parkingLotName = 'PARKING_LOT_NAME' and parkingLotAddress = 'PARKING_LOT_ADDRESS'
LIMIT 1;
-- Leave availability status true, must be available