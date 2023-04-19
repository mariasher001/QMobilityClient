package com.mariasher.qmobilityclient.database;

import androidx.annotation.NonNull;

import com.mariasher.qmobilityclient.Utils.Enums.ClientStatus;

public class Client {

    @NonNull
    private String clientId;
    private String clientUserName;
    private String clientEmail;
    private String clientPhoneNumber;
    private String clientStatus;
    private String queueId;

    public Client() {
    }

    public Client(@NonNull String clientId, String clientUserName, String clientEmail, String clientPhoneNumber) {
        this.clientId = clientId;
        this.clientUserName = clientUserName;
        this.clientEmail = clientEmail;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientStatus = ClientStatus.DEQUEUED.toString();
        this.queueId = "";
    }

    public Client(@NonNull String clientId, String clientUserName, String clientEmail, String clientPhoneNumber, String clientStatus, String queueId) {
        this.clientId = clientId;
        this.clientUserName = clientUserName;
        this.clientEmail = clientEmail;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientStatus = clientStatus;
        this.queueId = queueId;
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

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }
}
