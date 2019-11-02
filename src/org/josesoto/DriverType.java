package org.josesoto;

import java.io.Serializable;

public class DriverType implements Serializable {
    private final String TYPE_NAME;
    private final double FOLLOW_TIME;
    private final double SPEED_LIMIT;
    private final double MAX_ACCELERATION;

    public DriverType(String typeName, double followTime, double speedLimit, double maxAcceleration){
        this.TYPE_NAME = typeName;
        this.FOLLOW_TIME = followTime;
        this.SPEED_LIMIT = speedLimit;
        this.MAX_ACCELERATION = maxAcceleration;
    }

    public String getTypeName(){ return this.TYPE_NAME; }
    public double getFollowTime(){ return this.FOLLOW_TIME; }
    public double getSpeedLimit(){ return this.SPEED_LIMIT; }
    public double getMaxAcceleration(){ return this.MAX_ACCELERATION; }
}
