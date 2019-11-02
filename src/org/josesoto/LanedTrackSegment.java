package org.josesoto;

public class LanedTrackSegment extends TrackSegment {
    protected int lanes;

    public LanedTrackSegment(double speed, double length, int place, int lanes){
        super(speed, length, place);
        this.lanes = lanes;
    }
}
