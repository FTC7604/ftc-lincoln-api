package com.lincolnrobotics.api2018.implementation;

import com.lincolnrobotics.api2018.RgbColor;
import com.lincolnrobotics.api2018.RobotAutonomousControl;
import com.lincolnrobotics.api2018.SimpleRobotAutonomousControl;

/**
 * RENAME
 */
public class InitialSimpleRobotAutonomousControl implements SimpleRobotAutonomousControl
{
    private RobotAutonomousControl control;

    public InitialSimpleRobotAutonomousControl(RobotAutonomousControl control)
    {
        this.control = control;
    }

    /**
     * Instructs the robot to drive a certain distance.
     * @param distance The distance to drive, in centimeters. A positive distance corresponds to driving forward; a negative distance corresponds to driving backwards.
     */
    @Override
    public void drive(double distance)
    {
        control.driveForward(distance);
    }

    /**
     * Instructs the robot to rotate a certain angle.
     * @param angle The angle to rotate, in degrees. A positive angle corresponds to rotating clockwise; a negative angle corresponds to rotating counterclockwise.
     */
    @Override
    public void rotate(double angle)
    {
        control.turnRight(angle);
    }

    /**
     * Instructs the robot to extend or retract itself.
     * @param id  The ID number of the extension, arbitrarily assigned.
     * @param pos How far to extend the robot. This is a number from 0.0 to 1.0, where 1.0 is fully extended and 0.0 is fully retracted.
     */
    @Override
    public void extend(int id, double pos)
    {
        control.setExtensionPosition(id, pos);
    }

    /**
     * Gets the number of extensions on the robot.
     * @return The number of extensions on the robot.
     */
    @Override
    public int getExtensionCount()
    {
        return control.getExtensionCount();
    }

    /**
     * Gets the currently read color from a particular light sensor.
     * @param id The ID number of the color sensor, arbitrarily assigned.
     * @return The currently read color from the light sensor.
     */
    @Override
    public RgbColor senseColor(int id)
    {
        return control.senseColor(id);
    }

    /**
     * Gets the number of color sensors on the robot.
     * @return The number of color sensors on the robot.
     */
    @Override
    public int getColorSensorCount()
    {
        return control.getColorSensorCount();
    }
}
