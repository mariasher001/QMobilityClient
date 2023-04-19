package com.mariasher.qmobilityclient.database;

import androidx.annotation.NonNull;

public class Client {

    @NonNull
    private String clientId;
    private String clientUserName;
    private String clientEmail;
    private String clientPhoneNumber;
    //TODO: Implement these in constructor and set getters and setters.

    private String clientStatus;
    private String queueId;

    public Client(@NonNull String clientId, String clientUserName, String clientEmail, String clientPhoneNumber) {
        this.clientId = clientId;
        this.clientUserName = clientUserName;
        this.clientEmail = clientEmail;
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }
}
