package com.emergency.dispatch.model;

public class EmergencyRequest implements Comparable<EmergencyRequest> {
    private String requestId;
    private Priority priority;
    private double distance;

    public EmergencyRequest(String requestId, Priority priority, double distance) {
        this.requestId = requestId;
        this.priority = priority;
        this.distance = distance;
    }

    @Override
    public int compareTo(EmergencyRequest other) {
        return this.priority.compareTo(other.priority);
    }

    public Priority getPriority() {
        return priority;
    }

    public String getRequestId() {
        return requestId;
    }

    public double getDistance() {
        return distance;
    }
}
