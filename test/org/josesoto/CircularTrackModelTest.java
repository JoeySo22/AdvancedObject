package org.josesoto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircularTrackModelTest {

    @Test
    void testInstanceSuccess(){
        CircularTrackModel model = CircularTrackModel.getInstance("circular_lane_test.xml");
        System.out.println(model.getTrack().getTrackLength());
        assertEquals(1.0 , model.track.getTrackLength());
    }
}