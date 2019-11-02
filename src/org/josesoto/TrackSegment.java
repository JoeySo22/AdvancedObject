package org.josesoto;

import java.io.Serializable;

public class TrackSegment implements Serializable{
    protected double speedLimit;
    protected double length;
    protected int place;

    public TrackSegment(double speedLimit, double length, int place){
        this.speedLimit = speedLimit;
        this.length = length;
        this.place = place;
    }

    public double getSpeedLimit(){ return this.speedLimit; }
    public double getLength(){ return this.length; }
    public int getPlace(){ return this.place; }
}
