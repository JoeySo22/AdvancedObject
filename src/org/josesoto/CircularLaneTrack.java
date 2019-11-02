package org.josesoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularLaneTrack extends CircularTrack {
    public CircularLaneTrack(){
        super();
        this.segments = new ArrayList<TrackSegment>();
    }

    @Override
    public void addSegment(TrackSegment segment){
        segments.add(segment);
        this.trackLength += segment.getLength();
    }

    protected List<LanedDriver> getDriversFromLane(int laneNumber, double timeInquiry, List<LanedDriver> driverList){
        List<LanedDriver> drivers = new ArrayList<LanedDriver>();
        for (LanedDriver driver: driverList){
            if (driver.currentLane == laneNumber)
                drivers.add(driver);
        }
        return drivers;
    }
}
