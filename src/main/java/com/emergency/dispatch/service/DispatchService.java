package com.emergency.dispatch.service;

import com.emergency.dispatch.model.Ambulance;
import com.emergency.dispatch.model.AmbulanceState;
import com.emergency.dispatch.model.EmergencyRequest;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class DispatchService {
    private final List<Ambulance> ambulances = new ArrayList<>();
    private final Queue<EmergencyRequest> waitingQueue = new PriorityBlockingQueue<>();

    public void addAmbulance(Ambulance ambulance) {
        ambulances.add(ambulance);
    }

    public synchronized void requestEmergency(EmergencyRequest request) {
        Optional<Ambulance> ambulance = ambulances.stream()
                .filter(Ambulance::isAvailable)
                .findFirst();

        if (ambulance.isPresent()) {
            allocate(request, ambulance.get());
        } else {
            waitingQueue.add(request);
            System.out.println("No ambulance free. Request " + request.getRequestId() + " added to queue.");
        }
    }

    private void allocate(EmergencyRequest request, Ambulance ambulance) {
        ambulance.setState(AmbulanceState.DISPATCHED);
        System.out.println("Ambulance " + ambulance.getId() + " assigned to request " + request.getRequestId());
    }

    public synchronized void updateState(Ambulance ambulance, AmbulanceState newState) {
        ambulance.setState(newState);
        if (newState == AmbulanceState.AVAILABLE && !waitingQueue.isEmpty()) {
            EmergencyRequest next = waitingQueue.poll();
            allocate(next, ambulance);
        }
    }
}
