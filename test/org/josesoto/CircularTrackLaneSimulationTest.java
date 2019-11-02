package org.josesoto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircularTrackLaneSimulationTest {
    String testFileName = "circular_lane_test.xml";
    CircularTrackLaneSimulation sim;

    @Test
    void tryToInstantiateWithTestFile(){
        sim = new CircularTrackLaneSimulation(testFileName);
        // Just to test the file and instance exceptions.
        assertEquals(true,true);
    }
}