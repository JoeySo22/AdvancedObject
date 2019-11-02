package org.josesoto;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.sound.midi.Track;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class AbstractTrack implements TrackLoadable, Serializable {
    protected String distanceUnit = "miles";
    protected String timeUnit = "hours";
    protected int laps;
    protected List<TrackSegment> segments;
    protected double totalLength;
    protected double trackLength;

    public void setLaps(int laps) throws IllegalArgumentException {
        if (laps < 1)
            throw new IllegalArgumentException("setLaps() Invalid amount of laps. Only greater than 1.");
        this.laps = laps;
        this.totalLength = trackLength * this.laps;
    }

    public TrackSegment getTrackSegment(int index){
        return segments.get(index);
    }

    public double getSpeedLimitFromNextSegment(double position){
        for (int i = 0; i < segments.size(); i++){
            TrackSegment segment = segments.get(i);
            if (segment.getLength() > position){
                return segments.get(i + 1).getSpeedLimit();
            }
        }
        return Double.MAX_VALUE;
    }

    public double getSpeedLimitFrom(double position){
        double lengthTracker = 0.0;
        for (int i = 0; i < segments.size(); i++){
            TrackSegment segment = segments.get(i);
            lengthTracker += segment.getLength();
            if (position <= lengthTracker)
                return segment.getSpeedLimit();
        }
        return Double.MAX_VALUE;
    }

    public double getTrackLength(){
        return trackLength;
    }

    public double getTotalLength() {
        return totalLength;
    }

    public String getDistanceUnit(){ return this.distanceUnit; }
    public String getTimeUnit() { return this.timeUnit; }


}
