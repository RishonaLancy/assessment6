package com.emergency.dispatch.model;

public class Ambulance {
    private String id;
    private AmbulanceState state;

    public Ambulance(String id) {
        this.id = id;
        this.state = AmbulanceState.AVAILABLE;
    }

    public boolean isAvailable() {
        return this.state == AmbulanceState.AVAILABLE;
    }

    public void setState(AmbulanceState state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public AmbulanceState getState() {
        return state;
    }
}
