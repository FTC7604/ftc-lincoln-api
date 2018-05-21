package com.lincolnrobotics.api2018;

/**
 * Defines the methods that are fundamental to a robot
 */
public interface RobotInstructionSet
{
    /**
     * Instructs the drivetrain to move the robot forward a certain distance.
     * @param distance The distance to travel in cm.
     */
    void driveForward(double distance, double speed);

    /**
     * Instructs the drivetrain to rotate the robot in place to the left a certain angle.
     * @param angle The distance to travel in cm.
     */
    void turnLeft(double angle, double speed);
}
