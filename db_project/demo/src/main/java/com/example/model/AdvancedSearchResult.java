package com.example.model;

import java.sql.Date;

public class AdvancedSearchResult {
    private String userName;
    private int deviceId;
    private String deviceName;
    private Date usageDate;
    private int usageDuration;

    public AdvancedSearchResult(String userName, int deviceId, String deviceName, Date advUsageDate, int usageDuration) {
        this.userName = userName;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.usageDate = advUsageDate;
        this.usageDuration = usageDuration;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }

    public int getUsageDuration() {
        return usageDuration;
    }

    public void setUsageDuration(int usageDuration) {
        this.usageDuration = usageDuration;
    }
}
