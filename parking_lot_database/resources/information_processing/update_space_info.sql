UPDATE Spaces
SET spaceType='NEW_SPACE_TYPE',
	zoneID='NEW_ZONE_ID'
WHERE spaceNum=0 AND parkingLotName='PARKING_LOT_NAME' AND ParkingLotAddress='PARKING_LOT_ADDRESS';
-- Don't allow update of parking lot name and address or spaceNum because it doesn't make sense.
-- To do this just delete the space and enter a new one.