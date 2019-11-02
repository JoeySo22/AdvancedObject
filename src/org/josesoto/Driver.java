/*
    Author: Jose Eduardo Soto 88565673

    Description:    The class is meant to be for handling the abstraction of a car. Our model looks like this...

            The dot represents our cars currentTime. The previousTime and nextTime are plotted. Each Driver object
            knows its previous attributes and its current attributes. When updating, it calculates its next
            attributes. We use Kinematic equations and they're algebraic variants to get these points. They
            also check for obstacles and adjusts so in each part.
    ^ velocity
    |
    |
    |
    |
    |       |\._                  ._                ./|
    |       | | |               / | |             / | |
    |_______|_|_|_______________|_|_|_____________|_|_|______________________________________________________> time
 */

package org.josesoto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Driver implements Traceable, Serializable {

    protected final String NAME;
    protected final String DRIVER_TYPE;
    protected final AbstractTrack TRACK;

    protected DriverType driverTypeData;
    protected double timeHoursDelay;
    protected List<Driver> otherDrivers;
    protected Driver nextDriver;
    protected List<Double> positionsToSlowDown;
    protected boolean needToSlowDown;

    protected DrivingState currentStatus;
    protected double currentVelocity;
    protected double currentPositionOnTrack;
    protected double currentDistanceTraveled;
    protected double currentTimeInquiry;

    protected double targetVelocity;

    protected DrivingState previousStatus;
    protected double previousVelocity;
    protected double previousPositionOnTrack;
    protected double previousDistanceTraveled;
    protected double previousTimeInquiry;

    protected DrivingState newStatus;
    protected double newVelocity;
    protected double newPositionOnTrack;
    protected double newDistanceTraveled;
    protected double newTimeInquiry;


    public Driver(String name, String driverType, AbstractTrack track){
        this.NAME = name;
        this.TRACK = track;
        this.DRIVER_TYPE = driverType;
        this.currentStatus = DrivingState.START;
        this.currentVelocity = 0.0;
        this.currentPositionOnTrack = 0.0;
        this.currentDistanceTraveled = 0.0;
        this.currentTimeInquiry = 0.0;
        this.newVelocity = 0.0;
        this.newPositionOnTrack = 0.0;
        this.newDistanceTraveled = 0.0;
        this.newTimeInquiry = 0.0;
        this.needToSlowDown = false;
        this.otherDrivers = new ArrayList<Driver>();
        this.positionsToSlowDown = new ArrayList<Double>();
        // I need to setup a method to plot the positions where the cars switches to acceleration or deceleration
        // to begin at.
        //setUpPositionsToSlowDownForTrackSegments();

        //I need to track the distance to deceleration
    }

    public void addDriverTypeData(List<DriverType> driverTypesList) {
        for (int i = 0; i < driverTypesList.size(); i++){
            if (driverTypesList.get(i).getTypeName().equalsIgnoreCase(this.DRIVER_TYPE)){
                this.driverTypeData = driverTypesList.get(i);
                return;
            }
        }
    }
    public void addOtherCarsOnTrack(Driver driver){
        otherDrivers.add(driver);
    }

    public void update(double timeInHours){
        if (currentStatus == DrivingState.FINISH){
            // do nothing
        }else {
            previousStatus = currentStatus;
            previousVelocity = currentVelocity;
            previousDistanceTraveled = currentDistanceTraveled;
            previousPositionOnTrack = currentPositionOnTrack;
            previousTimeInquiry = currentTimeInquiry;

            currentVelocity = newVelocity;
            currentDistanceTraveled = newDistanceTraveled;
            currentPositionOnTrack = newPositionOnTrack;
            currentTimeInquiry = timeInHours;

            //Needs to track the distance from other cars and the track segments.

            updateAll();// Lets set these into one method.


            double timeInterval = currentTimeInquiry - previousTimeInquiry;

            // A: Transition
            if (timeInHours < timeHoursDelay){
                // Do Nothing
            }
            // B: Transition
            else if (timeInHours == timeHoursDelay){
                newStatus = DrivingState.ACCELERATING;
                refreshAttributes(timeInHours, 1.0);
            }
            else {
                // C: Transition
                if (timeInHours > timeHoursDelay & targetVelocity > currentVelocity){
                    currentStatus = DrivingState.ACCELERATING;
                    refreshAttributes(timeInHours ,  1.0);
                }

                // D: Transition
                else if (needToSlowDown){
                    currentStatus = DrivingState.DECELERATING;
                    refreshAttributes(timeInHours, -1.0);
                }

                // F: Transition
                else if (currentVelocity == targetVelocity){
                    currentStatus = DrivingState.COASTING;
                    newVelocity = currentVelocity;
                    newDistanceTraveled = Kinematics.distanceVelocityTimeFormula1(currentDistanceTraveled,
                            newVelocity, timeInterval);
                    newPositionOnTrack = newDistanceTraveled % TRACK.getTrackLength();
                }

                // I: Transition
                if (currentDistanceTraveled >= TRACK.getTrackLength()){
                    currentVelocity = 0.0;
                    newVelocity = 0.0;
                    currentStatus = DrivingState.FINISH;
                }
            }
        }
    }

    protected void updateAll(){
        setNextDriver();
        updatePositionsToSlowDown();
        setIfNeedToSlowDown();
    }

    protected void setIfNeedToSlowDown(){
        needToSlowDown = positionsToSlowDown.contains(this.currentPositionOnTrack);

        //we need to determine if we have to slow down or not. We already have one for the tracksegments so
        //we need to find the one for other cars. The other car is always getting updated so we don't have to
        // worry about that. Lets keep going.
        double followingPositionFromNextCar = nextDriver.currentPositionOnTrack -
                Kinematics.distanceVelocityTimeFormula1(0.0, nextDriver.currentVelocity,
                        driverTypeData.getFollowTime());
        if (Kinematics.distanceVelocityFinalVelocityInitialDistanceFormula4(nextDriver.currentVelocity,
                this.currentVelocity, -1.0 * driverTypeData.getMaxAcceleration()) +
                this.currentPositionOnTrack > followingPositionFromNextCar)
            needToSlowDown = true;
    }

    protected void setNextDriver(){
        Driver closestDriver = this;
        double minimumDistance = Double.MAX_VALUE;
        for (Driver driver : otherDrivers){
            if ((driver.currentPositionOnTrack) > this.currentPositionOnTrack) {
                double distanceDifference = driver.currentPositionOnTrack - this.currentPositionOnTrack;
                if (distanceDifference < minimumDistance){
                    minimumDistance = distanceDifference;
                    closestDriver = driver;
                }
            }
        }
        nextDriver = closestDriver;
    }
    protected void updatePositionsToSlowDown(){
        for (int i = 0; i < TRACK.segments.size(); i++){
            positionsToSlowDown.add(TRACK.segments.get(i).getLength() * (i+1) -
                    Kinematics.distanceVelocityFinalVelocityInitialDistanceFormula4(
                            TRACK.segments.get(i).getSpeedLimit(), this.currentVelocity,
                            this.driverTypeData.getMaxAcceleration()
                    ));
        }
    }

    protected void refreshAttributes(double timeInquiry, double signConstant){
        double timeSegmentDifference = currentTimeInquiry - previousTimeInquiry;
        newTimeInquiry = currentTimeInquiry + timeSegmentDifference;
        if (newStatus == DrivingState.ACCELERATING){
            newVelocity = Kinematics.velocityAccelerationTimeFormula2(currentVelocity,
                    driverTypeData.getMaxAcceleration(), timeSegmentDifference);
            newDistanceTraveled = Kinematics.distanceVelocityAccelerationTimeFormula3(currentDistanceTraveled,
                    currentVelocity, driverTypeData.getMaxAcceleration(), timeSegmentDifference);
        }
        else if (newStatus == DrivingState.COASTING){
            newVelocity = currentVelocity;
            newDistanceTraveled = Kinematics.distanceVelocityTimeFormula1(currentDistanceTraveled,
                    newVelocity, timeSegmentDifference);
        }
        else if (newStatus == DrivingState.DECELERATING){
            newVelocity = Kinematics.velocityAccelerationTimeFormula2(currentVelocity,
                    driverTypeData.getMaxAcceleration() * -1, timeSegmentDifference);
            newDistanceTraveled = Kinematics.distanceVelocityAccelerationTimeFormula3(currentDistanceTraveled,
                    currentVelocity, driverTypeData.getMaxAcceleration() * -1, timeSegmentDifference);
        }
        newPositionOnTrack = newDistanceTraveled % TRACK.getTrackLength();
        targetVelocity = TRACK.getSpeedLimitFrom(newPositionOnTrack);

        /*System.out.println(this.getName() + ": " + "newVelocity=" + Double.toString(newTimeInquiry) +
                " newDistanceTraveled= " + Double.toString(newDistanceTraveled) + " newPositionOnTrack=" +
                Double.toString(newPositionOnTrack) + " newTimeInquiry=" + Double.toString(timeSegmentDifference));*/
    }


    public void setTimeHoursDelay(double timeHoursDelay){ this.timeHoursDelay = timeHoursDelay; }

    @Override
    public double getDistance(double timeInHours) {
        /*try{
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){
            //
        }*/
        update(timeInHours);
        if (driverTypeData == null)
            return 0;
        else {
            return this.currentDistanceTraveled;
        }
    }
    @Override
    public double getSpeed(double timeInHours) {
        update(timeInHours);
        if (driverTypeData == null)
            return 0;
        else {
            return this.currentVelocity;
        }
    }
    public String getName(){ return this.NAME; }
    public String getDriverTypeData() { return this.DRIVER_TYPE; }
    public DrivingState getStatus(){return this.currentStatus;}
}
