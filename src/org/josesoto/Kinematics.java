/*
    Author: Jose Eduardo Soto 88565673

    Description: This class doesn't need to be instantiated. It is a util class that is to have all the combinations
    of Kinematic physics formulas.

    Formula # 1: d = v * t
        Formula # 2: v = v_i + a*t
        Formula # 3: d = d_i + v_i*t + 1/2 * a * t^2
        Formula # 4: v_f^2 = v_i^2 + 2*a*d

        d = distance
        v = velocity
        t = time
        a = acceleration
        f = final
        i = initial
        _ = subscript
        ^ = superscript

    Log:
        10/18/2019 05:42PM -    Filled out the log. I plan on to continue to add more formula's from physics. The
        design of the formula is supposed to help the users inside an IDE. If the user needs distance, then they
        begin to type Kinematics.di... and they will see the variant different formula's they can use.

        10/18/2019 05:42PM -    As for now we only have the first four formulas

 */

package org.josesoto;

public class Kinematics {

    public static double distanceVelocityTimeFormula1(double distanceInitial, double velocity, double time)
    {
        return distanceInitial + (velocity * time);
    }
    public static double timeDistanceVelocityFormula1(double timeInitial, double distance, double velocity){
        return timeInitial + (distance / velocity);
    }
    public static double velocityDistanceTimeFormula1(double velocityInitial, double distance, double time){
        return velocityInitial + (distance / time);
    }


    public static double velocityAccelerationTimeFormula2(double velocityInitial, double accelerationRate, double time){
        return velocityInitial + (accelerationRate * time);
    }
    public static double accelerationVelocityTimeFormula2(double accelerationInitial, double velocityFinal, double
                                                          velocityInitial, double time){
        return accelerationInitial + ((velocityFinal - velocityInitial) / time);
    }
    public static double timeVelocityAccelerationFormula2(double timeInitial, double velocityFinal,
                                                          double velocityInitial, double accelerationRate){
        return timeInitial + ((velocityFinal - velocityInitial) / accelerationRate);
    }


    public static double distanceVelocityAccelerationTimeFormula3(double distanceInitial, double velocityInitial,
                                                                  double accelerationRate, double time){
        return distanceInitial + (velocityInitial * time) + (0.5 * accelerationRate * (Math.pow(time, 2)));
    }
    public static double velocityInitialDistanceAccelerationTimeFormula3(double distance, double accelerationRate,
                                                                         double time){
        return (distance - (0.5 * accelerationRate * (Math.pow(time, 2)))) / time;
    }
    public static double accelerationDistanceVelocityTimeFormula3(double distance, double velocityInitial,
                                                                  double time){
        return 2.0 * (distance - (velocityInitial * time)) / Math.pow(time, 2);
    }


    public static double velocityVelocityInitialAccelerationDistanceFormula4(double velocityInitial,
                                                                             double acceleration,
                                                                             double distance){
        return Math.sqrt(Math.pow(velocityInitial, 2) + 2 * acceleration * distance);
    }
    public static double distanceVelocityFinalVelocityInitialDistanceFormula4(double velocityFinal,
                                                                              double velocityInitial,
                                                                              double acceleration){
        return Math.pow(velocityFinal, 2) - Math.pow(velocityInitial, 2) / 2.0 * acceleration;
    }

}
