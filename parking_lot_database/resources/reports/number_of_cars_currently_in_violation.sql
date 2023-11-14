SELECT count(DISTINCT vehicleLicenseNum) AS NumCarsInViolation
FROM Citations
WHERE paymentStatus = false;
