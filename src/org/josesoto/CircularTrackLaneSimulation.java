/*
    Author: Jose Eduardo Soto 88565673

    Description:    This is our new CircularTrack but with lanes now. It should just extend everything for now and I
    think our cars can just drive fluidly.

    Log:
    10/18/2019 06:45PM      This project just started. I've already and made a quick testCase for instantiating our new
    simulation class here with a small new test xml document. What I need to do next is...
        - Get the class far enough to test loading Data from the xml page. This means Testing the loadData part and make
        sure there isn't runtime errors.

    10/18/2019 09:25PM      Continuing to figure out the OOP obstacles...
    Finished the hard part of extending all the CircularLaned part of our class.

    10/18/2019 09:35PM      I can't find a safe way to extend CircularTrackSimulation.loadData() so I'm just going to
    copy and paste. NEED TO ASK ABOUT WHY I CAN'T CREATE AN ARRAYLIST OF TYPE LANEDTRACKSEGMENT

    10/19/2019 01:06PM      Able to figure out the problem I previously stated. Now I think I can use loadData() I need
    to make sure there are no runtime errors. It passed!

    10/19/2019 09:51PM      I need to override some updating methods in LanedDriver from Driver to switch lanes and all.
    One of the main issue is making the rules for if all of the other lanes are occupied with slow drivers.
    IF they are all occupied then it should obviously follow the fastest one.

 */

package org.josesoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularTrackLaneSimulation extends CircularTrackSimulation{
    protected List<LanedDriver> driverList;
    protected CircularLaneTrack track;

    public CircularTrackLaneSimulation(String fileName){
        super(fileName);
        this.track = new CircularLaneTrack();
        this.driverList = new ArrayList<LanedDriver>();
        this.log = new LLogger();
    }
    @Override
    public void runSimulation(){
        loadData();
        log.load();
        for(double timeInHours = 0.0000; simulationStillRunning; timeInHours += 0.0084){
            // 0.0084 is 30 seconds in hours
            log.addTimeInquiryLog(timeInHours);
        }
        System.out.println(log.sb.toString());
    }

    public void loadData(){
        int driverCounter = 0;
        int laneCounter = 0;
        for (int i = 0; i < xmlIterator.getSecondLevelLength(); i++){
            xmlIterator.next();
            if (xmlIterator.currentSecondLevelElementName().equalsIgnoreCase("SEGMENT")){
                double speedLimit = Double.parseDouble(xmlIterator.getTextContextOf("SPEED_LIMIT"));
                double length = Double.parseDouble(xmlIterator.getTextContextOf("LENGTH"));
                int place = Integer.parseInt(xmlIterator.getTextContextOf("SEGMENT_NUMBER"));
                track.addSegment(new LanedTrackSegment(speedLimit, length, place, laneCounter));
                laneCounter++;
            }
            else if (xmlIterator.currentSecondLevelElementName().equalsIgnoreCase("DRIVER_TYPE")){
                String typeName = xmlIterator.getTextContextOf("TYPE_NAME");
                double followTime = Double.parseDouble(xmlIterator.getTextContextOf("FOLLOW_TIME"));
                double speedLimit = Double.parseDouble(xmlIterator.getTextContextOf("SPEED_LIMIT"));
                double maxAcceleration = Double.parseDouble(xmlIterator.getTextContextOf("MAX_ACCELERATION"));
                driverTypeList.add(new DriverType(typeName, followTime, speedLimit, maxAcceleration));
            }
            else if (xmlIterator.currentSecondLevelElementName().equalsIgnoreCase("DRIVER")){
                String name = xmlIterator.getTextContextOf("NAME");
                String driverType = xmlIterator.getTextContextOf("DRIVER_TYPE");
                int lane = Integer.parseInt(xmlIterator.getTextContextOf("LANE_ASSIGNMENT")); //<<<<<<< CHANGE THIS
                LanedDriver driver = new LanedDriver(name, driverType, (CircularLaneTrack) track, lane);
                driver.setTimeHoursDelay((double)driverCounter * 0.0084);
                driverCounter++;
                driverList.add(driver);
            }
        }
        for (Driver driver: driverList)
            driver.addDriverTypeData(driverTypeList);
        updateDriversOfEachOther();
        track.setLaps(1);
    }

    protected void updateDriversOfEachOther(){
        for (int i = 0; i < driverList.size(); i++){
            for (int j = 0; j < driverList.size(); j++){
                if (i == j)
                    continue;
                else if (driverList.get(i).getSetupLane() == driverList.get(j).getSetupLane())
                    driverList.get(i).addOtherCarsOnTrack(driverList.get(j));
            }
        }
    }

    class LLogger extends Logger{
        protected String l = "lane";

        public LLogger(){
            super();
        }
        public void addTimeInquiryLog(double time){
            //System.out.println("TIME=" + time);
            /*try{
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e){
                e.printStackTrace();
            }*/
            sb.append(t + "\t\t");
            for (Driver driver: driverList){
                sb.append(format.format(driver.getDistance(time)) + "\t\t");
            }
            sb.append("\n\t\t\t");
            for (Driver driver: driverList){
                sb.append(format.format(driver.getSpeed(time)) + "\t\t");
            }
            sb.append("\n");
            simulationStillRunning = checkIfCarsAreAllFinished();
        }
        protected boolean checkIfCarsAreAllFinished(){
            for (Driver driver: driverList){
                if (driver.currentStatus != DrivingState.FINISH)
                    return true;
            }
            return false;
        }
    }
}
