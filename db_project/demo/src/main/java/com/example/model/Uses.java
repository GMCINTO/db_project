package com.example.model;


/*
 * CREATE TABLE Uses (
    UserID INT NOT NULL,
    DeviceID INT NOT NULL,
    UsageDate DATE NOT NULL,
    UsageDuration INT NOT NULL, -- Assuming duration is in minutes
    PRIMARY KEY (UserID, DeviceID, UsageDate),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (DeviceID) REFERENCES Devices(DeviceID)
    );
 */
public class Uses {

    private int userId;
    private int deviceId;
    private String usageDate;
    private String usageDuration;

    public Uses(int userId, int deviceId, String usageDate, String usageDuration) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.usageDate = usageDate;
        this.usageDuration = usageDuration;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(String usageDate) {
        this.usageDate = usageDate;
    }

    public String getUsageDuration() {
        return usageDuration;
    }
    
    public void setUsageDuration(String usageDuration) {
        this.usageDuration = usageDuration;
    } 


}
