package com.lincolnrobotics.api2018;

/**
 * Defines a simple interface to allow basic control of an FTC robot.
 */
public interface SimpleRobotAutonomousControl
{
    /**
     * Issues a command to the robot to drive forward for a certain distance (provided in centimeters).
     * @param distance The distance to drive, in centimeters.
     */
    void driveForward(double distance);

    /**
     * Issues a command to the robot to drive backward for a certain distance (provided in centimeters).
     * @param distance The distance to drive, in centimeters.
     */
    void driveBackward(double distance);

    /**
     * Issues a command to the robot to turn right (clockwise) a certain angle (provided in degrees).
     * @param angle The angle to turn, in degrees.
     */
    void turnRight(double angle);

    /**
     * Issues a command to the robot to turn left (counter-clockwise) a certain angle (provided in degrees).
     * @param angle The angle to turn, in degrees.
     */
    void turnLeft(double angle);

    /**
     * Issues a command to the robot to extend or retract itself to the provided position.
     * @param id  The ID number of the extension, arbitrarily assigned.
     * @param pos How far to extend the robot. This is a number from 0.0 to 1.0, where 1.0 is fully extended and 0.0 is fully retracted.
     */
    void extendTo(int id, double pos);

    /**
     * Gets the number of extensions on the robot.
     * @return The number of extensions on the robot.
     */
    int getExtensionCount();

    /**
     * Gets the currently read color from a particular light sensor.
     * @param id The ID number of the color sensor, arbitrarily assigned.
     * @return The currently read color from the light sensor.
     */
    RgbColor senseColor(int id);

    /**
     * Gets the number of color sensors on the robot.
     * @return The number of color sensors on the robot.
     */
    int getColorSensorCount();
}
