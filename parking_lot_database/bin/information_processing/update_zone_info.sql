UPDATE Zones
SET zoneID='NEW_ZONE_ID' -- can only update attribute zoneID, it doesn't make sense to assign a zone to a different parking lot
-- There are spaces associated with the zone in a particular parking lot
-- To update to a new parking lot you must delete the zone and enter a new one
WHERE zoneID='OLD_ZONE_ID' AND parkingLotName='PARKING_LOT_NAME' AND parkingLotAddress='PARKING_LOT_ADDRESS';