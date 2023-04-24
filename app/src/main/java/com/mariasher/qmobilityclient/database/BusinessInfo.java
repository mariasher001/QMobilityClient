package com.mariasher.qmobilityclient.database;

import androidx.annotation.NonNull;

public class BusinessInfo {

    @NonNull
    private String businessID;
    private String businessName;
    private String businessPhoneNumber;
    private String businessAddress;
    private String businessLatitude;
    private String businessLongitude;
    private String businessType;

    public BusinessInfo() {
    }

    public BusinessInfo(@NonNull String businessID, String businessName, String businessPhoneNumber, String businessAddress, String businessLatitude, String businessLongitude, String businessType) {
        this.businessID = businessID;
        this.businessName = businessName;
        this.businessPhoneNumber = businessPhoneNumber;
        this.businessAddress = businessAddress;
        this.businessLatitude = businessLatitude;
        this.businessLongitude = businessLongitude;
        this.businessType = businessType;
    }

    @NonNull
    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(@NonNull String businessID) {
        this.businessID = businessID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }

    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessLatitude() {
        return businessLatitude;
    }

    public void setBusinessLatitude(String businessLatitude) {
        this.businessLatitude = businessLatitude;
    }

    public String getBusinessLongitude() {
        return businessLongitude;
    }

    public void setBusinessLongitude(String businessLongitude) {
        this.businessLongitude = businessLongitude;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
