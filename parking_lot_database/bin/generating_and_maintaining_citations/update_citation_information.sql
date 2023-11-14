UPDATE Citations
SET
  citationDate = 'YYYY-MM-DD',
  citationTime = 'CITATION_TIME',
  vehicleLicenseNum = 'LICENSE_NUM',
  vehicleModel = 'VEHICLE_MODEL',
  vehicleColor = 'VEHICLE_COLOR',
  parkingLotName = 'PARKING_LOT_NAME',
  parkingLotAddress = 'PARKING_LOT_ADDR',
  category = 'CATEGORY',
  paymentStatus = false,
  appealRequested = false
WHERE citationNum = 'CITATION_NUM';
-- paymentStatus and appealRequested are placeholders, should be editable by user through Java
