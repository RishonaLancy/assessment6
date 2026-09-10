package com.emergency.dispatch.service;

import com.emergency.dispatch.model.Ambulance;
import com.emergency.dispatch.model.AmbulanceState;
import com.emergency.dispatch.model.EmergencyRequest;
import com.emergency.dispatch.model.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DispatchServiceTest {

    private DispatchService dispatchService;
    private Ambulance ambulance;

    @BeforeEach
    void setUp() {
        dispatchService = new DispatchService();
        ambulance = new Ambulance("AMB-001");
        dispatchService.addAmbulance(ambulance);
    }

    @Test
    void testDispatchWhenAmbulanceAvailable() {
        EmergencyRequest request = new EmergencyRequest("REQ-01", Priority.HIGH, 3.5);
        dispatchService.requestEmergency(request);
        
        assertFalse(ambulance.isAvailable(), "Ambulance should be busy after dispatch");
    }

    @Test
    void testQueueWhenNoAmbulanceAvailable() {
        EmergencyRequest req1 = new EmergencyRequest("REQ-01", Priority.CRITICAL, 2.0);
        dispatchService.requestEmergency(req1);
        assertFalse(ambulance.isAvailable());

        EmergencyRequest req2 = new EmergencyRequest("REQ-02", Priority.MODERATE, 5.0);
        dispatchService.requestEmergency(req2);
        
        assertFalse(ambulance.isAvailable());
    }

    @Test
    void testAutomaticAllocationFromQueue() {
        EmergencyRequest req1 = new EmergencyRequest("REQ-01", Priority.HIGH, 2.0);
        dispatchService.requestEmergency(req1);

        EmergencyRequest req2 = new EmergencyRequest("REQ-02", Priority.CRITICAL, 4.0);
        dispatchService.requestEmergency(req2);

        // State changes to AVAILABLE, automatically pulling req2 from the queue
        dispatchService.updateState(ambulance, AmbulanceState.AVAILABLE);
        
        assertFalse(ambulance.isAvailable(), "Ambulance should automatically pick up the queued request");
    }
}
