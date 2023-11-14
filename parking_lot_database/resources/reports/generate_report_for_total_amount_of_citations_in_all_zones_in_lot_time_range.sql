SELECT parkingLotName, parkingLotAddress, COUNT(*) AS totalNumCitationsAllZones
FROM Citations
WHERE citationDate > 'START_DATE' AND citationDate < 'END_DATE'
GROUP BY parkingLotName, parkingLotAddress;
