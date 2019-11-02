package org.josesoto;

import java.util.List;

public class LanedDriver extends Driver {
    protected int setupLane;
    protected int currentLane;

    public LanedDriver(String name, String driverType, CircularLaneTrack track,
                       int laneNumber){
        super(name, driverType, track);
        this.setupLane = laneNumber;
    }

    public int getSetupLane() {return this.setupLane;}
    public int getCurrentLane() {return this.currentLane;}
}
