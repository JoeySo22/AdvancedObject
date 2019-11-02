package org.josesoto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KinematicsTest {

    @Test
    void distanceVelocityTimeFormula1() {
        // 10.0m + (20m/s)(30.0s) = 610.0
        assertEquals(610.0,Kinematics.distanceVelocityTimeFormula1(10.0, 20.0, 30.0));
    }
    /*
    @Test
    void timeDistanceVelocityFormula1() {
        //
    }

    @Test
    void velocityDistanceTimeFormula1() {
    }

    @Test
    void velocityAccelerationTimeFormula2() {
    }

    @Test
    void accelerationVelocityTimeFormula2() {
    }

    @Test
    void timeVelocityAccelerationFormula2() {
    }

    @Test
    void distanceVelocityAccelerationTimeFormula3() {
    }

    @Test
    void velocityInitialDistanceAccelerationTimeFormula3() {
    }

    @Test
    void accelerationDistanceVelocityTimeFormula3() {
    }

    @Test
    void velocityVelocityInitialAccelerationDistanceFormula4() {
    }

    @Test
    void distanceVelocityFinalVelocityInitialDistanceFormula4() {
    }*/
}