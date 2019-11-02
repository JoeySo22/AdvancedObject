package org.josesoto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularTrackSimulation {

    protected final ThreeLevelXMLIterator xmlIterator;

    protected Logger log;
    protected CircularTrack track;
    protected List<DriverType> driverTypeList;
    protected List<Driver> driverList;
    protected boolean simulationStillRunning;

    public CircularTrackSimulation(){
        this.track = new CircularTrack();
        this.xmlIterator = new ThreeLevelXMLIterator("test.xml");
        this.log = new Logger();
        this.driverTypeList = new ArrayList<DriverType>();
        this.driverList = new ArrayList<Driver>();
        this.simulationStillRunning = true;
    }
    public CircularTrackSimulation(String fullFileName){
        this.track = new CircularTrack();
        this.xmlIterator = new ThreeLevelXMLIterator(fullFileName);
        this.log = new Logger();
        this.driverTypeList = new ArrayList<DriverType>();
        this.driverList = new ArrayList<Driver>();
        this.simulationStillRunning = true;
    }

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
        for (int i = 0; i < xmlIterator.getSecondLevelLength(); i++){
            xmlIterator.next();
            if (xmlIterator.currentSecondLevelElementName().equalsIgnoreCase("SEGMENT")){
                double speedLimit = Double.parseDouble(xmlIterator.getTextContextOf("SPEED_LIMIT"));
                double length = Double.parseDouble(xmlIterator.getTextContextOf("LENGTH"));
                int place = Integer.parseInt(xmlIterator.getTextContextOf("SEGMENT_NUMBER"));
                track.addSegment(new TrackSegment(speedLimit, length, place));
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
                Driver driver =new Driver(name, driverType, track);
                driver.setTimeHoursDelay((double)driverCounter * 0.0084);
                driverCounter++;
                driverList.add(driver);
            }
        }
        for (Driver driver: driverList)
            driver.addDriverTypeData(driverTypeList);
        for (int i = 0; i < driverList.size(); i++){
            for (int j = 0; j < driverList.size(); j++){
                if (i == j)
                    continue;
                else driverList.get(i).addOtherCarsOnTrack(driverList.get(j));
            }
        }
        track.setLaps(2);
    }

    class Logger{
        protected StringBuilder sb;
        // Time Header
        protected String t;
        // Time Tail
        protected String ut;
        // Distance Header
        protected String d;
        // Distance Tail
        protected String ud;
        // Speed Header
        protected String s;
        // Speed Tail
        protected String us;

        protected DecimalFormat format;

        public Logger(){
            sb = new StringBuilder();
            t = "Time:";
            ut = "hour(s)";
            d = "Distance";
            ud = "miles";
            s = "Speed";
            us = ud + "/" + ut;
            format = new DecimalFormat("0.0000");
        }

        public void load(){
            sb.append("\t\t");
            for (Driver driver : driverList)
                sb.append(driver.getName() + "\t");
            sb.append("\n");
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
