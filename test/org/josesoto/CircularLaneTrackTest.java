package org.josesoto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircularLaneTrackTest {

    @Test
    void testAttributes(){
        CircularTrackLaneSimulation sim = new CircularTrackLaneSimulation("circular_lane_test.xml");
        sim.loadData();
        CircularLaneTrack track = sim.track;
        LanedTrackSegment segment = (LanedTrackSegment)track.segments.get(0);
        assertEquals(20.0, segment.getSpeedLimit());
        assertEquals(1.0, segment.getLength());
        assertEquals(1.0,track.getTrackLength());
        assertEquals(1.0,track.getTotalLength());
    }
}