package com.lincolnrobotics.api2018;

/**
 * Defines a simple interface to allow basic control of an FTC robot.
 */
public interface SimpleRobotAutonomousControl
{
    /**
     * Instructs the robot to drive a certain distance.
     * @param distance The distance to drive, in centimeters. A positive distance corresponds to driving forward; a negative distance corresponds to driving backwards.
     */
    void drive(double distance);

    /**
     * Instructs the robot to rotate a certain angle.
     * @param angle The angle to rotate, in degrees. A positive angle corresponds to rotating clockwise; a negative angle corresponds to rotating counterclockwise.
     */
    void rotate(double angle);

    /**
     * Instructs the robot to extend or retract itself.
     * @param id The ID number of the extension, arbitrarily assigned.
     * @param pos How far to extend the robot. This is a number from 0.0 to 1.0, where 1.0 is fully extended and 0.0 is fully retracted.
     */
    void extend(int id, double pos);

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
