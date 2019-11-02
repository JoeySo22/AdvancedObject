/*
    Author: Jose Eduardo Soto

    Description: The purpose of this assignment is to create a GUI race track window for our circular track.
    This one IS different from the other programming assignments because we will implement the Model-Viewer-Controller
    Design Pattern. This will imply that we get rid of some things from our previous model.

    The thing we get rid of is the CircularTrackSimulator. It seems it was a placeholder for our Model. So we will
    follow the rules of the design patter.

    Model will contain this class the CircularTrackModel, Driver, and CircularTrack. (We use these for now because we
    don't have lanes right now).



 */

package org.josesoto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CircularTrackModel {
    protected List<Driver> drivers;
    protected CircularTrack track;
    protected ThreeLevelXMLIterator iterator;

    // We're going to also implement a Singleton Design Pattern.
    private static CircularTrackModel instance;

    private CircularTrackModel(String fileName){
        iterator = new ThreeLevelXMLIterator(fileName);
        int driverCount = 0;
        // The iterator is ready, we need to parse through iterator to populate drivers and track.
        track = new CircularTrack();
        drivers = new ArrayList<Driver>();
        List<DriverType> driverTypeList = new ArrayList<DriverType>();
        for (int i = 0; i < iterator.getSecondLevelLength(); i++){
            iterator.next();
            //populate track
            if (iterator.currentSecondLevelElementName().equalsIgnoreCase("SEGMENT")){
                double speedLimit = Double.parseDouble(iterator.getTextContextOf("SPEED_LIMIT"));
                double length = Double.parseDouble(iterator.getTextContextOf("LENGTH"));
                int place = Integer.parseInt(iterator.getTextContextOf("SEGMENT_NUMBER"));
                track.addSegment(new TrackSegment(speedLimit, length, place));
            }
            //populate driver types
            if (iterator.currentSecondLevelElementName().equalsIgnoreCase("DRIVER_TYPE")){
                String typeName = iterator.getTextContextOf("TYPE_NAME");
                double followType = Double.parseDouble(iterator.getTextContextOf("FOLLOW_TIME"));
                double speedLimit = Double.parseDouble(iterator.getTextContextOf("SPEED_LIMIT"));
                double maxAcceleration = Double.parseDouble(iterator.getTextContextOf("MAX_ACCELERATION"));
                driverTypeList.add(new DriverType(typeName, followType, speedLimit, maxAcceleration));
            }
            //populate the drivers.
            if (iterator.currentSecondLevelElementName().equalsIgnoreCase("DRIVER")){
                String name = iterator.getTextContextOf("NAME");
                String driverType = iterator.getTextContextOf("DRIVER_TYPE");
                Driver driver = new Driver(name, driverType, track);
                driver.setTimeHoursDelay((double)driverCount * 0.0084);
                driverCount++;
                drivers.add(driver);
            }
        }

        //Must add the drivertypes into each driver.
        for (Driver driver: drivers)
            driver.addDriverTypeData(driverTypeList);
        //Must make each car aware of each other
        for (int i = 0; i < drivers.size(); i++){
            for (int j = 0; j < drivers.size(); j++){
                if (i == j)
                    continue;
                else drivers.get(i).addOtherCarsOnTrack(drivers.get(j));
            }
        }
        track.setLaps(1);
    }



    public static CircularTrackModel getInstance(String fileName){
        if (instance == null){
            try {
                instance = new CircularTrackModel(fileName);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return instance;
    }

    public List<Driver> getDrivers(){
        return this.drivers;
    }
    public CircularTrack getTrack(){
        return this.track;
    }

    public void update(double time){
        for (Driver driver: drivers)
            driver.update(time);
    }

    public byte[] getSerializedDriverListUpdateQuery() throws IOException {
        /*
            This is a challenge. One of the things we need to do is...
            1. Make Deep Clones of all the cars.
            2. Put them into an ArrayList<Driver> object.
            3. Put it into an output stream and send it back
         */

        // How do we make deep clones?
        // So I just learned that Serializing is the best way to make a Deep copy of objects.
        // https://javatechniques.com/blog/faster-deep-copies-of-java-objects/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(drivers);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
