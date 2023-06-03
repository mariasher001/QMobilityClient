package com.mariasher.qmobilityclient.database;

import androidx.annotation.NonNull;

import com.mariasher.qmobilityclient.Utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.Map;

public class Queue {
    @NonNull
    private String queueId;
    private String creatorId;
    private String queueName;
    private String queueStartTime;
    private String queueEndTime;
    private String queueStatus;
    private int numberOfActiveCounters;
    private String averageCustomerTime;
    private Map<String, Object> queueCounters;
    private Map<String, Object> clientsInQueue;
    private double totalClientWaitingTime;
    private int totalClients;

    public Queue() {
    }

    public Queue(@NonNull String queueId, String creatorId, String queueName, LocalDateTime queueStartTime, LocalDateTime queueEndTime, String queueStatus, int numberOfActiveCounters, LocalDateTime averageCustomerTime, Map<String, Object> queueCounters, Map<String, Object> clientsInQueue, double totalClientWaitingTime, int totalClients) {
        this.queueId = queueId;
        this.creatorId = creatorId;
        this.queueName = queueName;
        this.queueStartTime = DateTimeUtils.convertDateAndTimeToString(queueStartTime);
        this.queueEndTime = DateTimeUtils.convertDateAndTimeToString(queueEndTime);
        this.queueStatus = queueStatus;
        this.numberOfActiveCounters = numberOfActiveCounters;
        this.averageCustomerTime = DateTimeUtils.convertDateAndTimeToString(averageCustomerTime);
        this.queueCounters = queueCounters;
        this.clientsInQueue = clientsInQueue;
        this.totalClientWaitingTime = totalClientWaitingTime;
        this.totalClients = totalClients;
    }

    public Queue(@NonNull String queueId, String creatorId, String queueName, String queueStartTime, String queueEndTime, String queueStatus, int numberOfActiveCounters, String averageCustomerTime, Map<String, Object> queueCounters, Map<String, Object> clientsInQueue, double totalClientWaitingTime, int totalClients) {
        this.queueId = queueId;
        this.creatorId = creatorId;
        this.queueName = queueName;
        this.queueStartTime = queueStartTime;
        this.queueEndTime = queueEndTime;
        this.queueStatus = queueStatus;
        this.numberOfActiveCounters = numberOfActiveCounters;
        this.averageCustomerTime = averageCustomerTime;
        this.queueCounters = queueCounters;
        this.clientsInQueue = clientsInQueue;
        this.totalClientWaitingTime = totalClientWaitingTime;
        this.totalClients = totalClients;
    }

    @NonNull
    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(@NonNull String queueId) {
        this.queueId = queueId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueStartTime() {
        return queueStartTime;
    }

    public void setQueueStartTime(String queueStartTime) {
        this.queueStartTime = queueStartTime;
    }

    public String getQueueEndTime() {
        return queueEndTime;
    }

    public void setQueueEndTime(String queueEndTime) {
        this.queueEndTime = queueEndTime;
    }

    public String getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
    }

    public int getNumberOfActiveCounters() {
        return numberOfActiveCounters;
    }

    public void setNumberOfActiveCounters(int numberOfActiveCounters) {
        this.numberOfActiveCounters = numberOfActiveCounters;
    }

    public String getAverageCustomerTime() {
        return averageCustomerTime;
    }

    public void setAverageCustomerTime(String averageCustomerTime) {
        this.averageCustomerTime = averageCustomerTime;
    }

    public Map<String, Object> getQueueCounters() {
        return queueCounters;
    }

    public void setQueueCounters(Map<String, Object> queueCounters) {
        this.queueCounters = queueCounters;
    }

    public Map<String, Object> getClientsInQueue() {
        return clientsInQueue;
    }

    public void setClientsInQueue(Map<String, Object> clientsInQueue) {
        this.clientsInQueue = clientsInQueue;
    }

    public double getTotalClientWaitingTime() {
        return totalClientWaitingTime;
    }

    public void setTotalClientWaitingTime(double totalClientWaitingTime) {
        this.totalClientWaitingTime = totalClientWaitingTime;
    }

    public int getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(int totalClients) {
        this.totalClients = totalClients;
    }
}
