
CREATE DEFINER=`glemicnt`@`%` PROCEDURE `GetUserUsageRecords`(IN p_UserName VARCHAR(50))
BEGIN
    SELECT u.UserID, u.UserName, d.DeviceID, d.DeviceName, d.DeviceType, us.UsageDate, us.UsageDuration
    FROM Users u
    INNER JOIN Uses us ON u.UserID = us.UserID
    INNER JOIN Devices d ON us.DeviceID = d.DeviceID
    WHERE u.UserName = p_UserName;
END




CREATE DEFINER=`glemicnt`@`%` PROCEDURE `GetDevicesUsedBetweenDates6`(
    IN userId INT,
    IN startDate DATE,
    IN endDate DATE
)
BEGIN
    SELECT 
        u.UserName,
        d.DeviceID,
        d.DeviceName,
        us.UsageDate,
        us.UsageDuration
    FROM 
        Uses us
    LEFT OUTER JOIN 
        Users u ON us.UserID = u.UserID
    LEFT JOIN 
        Devices d ON us.DeviceID = d.DeviceID
    WHERE 
        us.UserID = userId
    AND us.UsageDate BETWEEN startDate AND endDate;
END