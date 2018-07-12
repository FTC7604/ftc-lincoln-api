package com.lincolnrobotics.api2018;

/**
 * An implementation of {@link SimpleRobotAutonomousControl} that uses a provided {@link RobotAutonomousControl} to execute its commands.
 * Essentially, this class "passes through" all values and provided commands to the given {@link RobotAutonomousControl}.
 */
public class PassthroughSimpleRobotAutonomousControl implements SimpleRobotAutonomousControl
{
    private RobotAutonomousControl robot;

    public PassthroughSimpleRobotAutonomousControl(RobotAutonomousControl robot)
    {
        this.robot = robot;
    }

    /**
     * Instructs the robot to drive a certain distance.
     * @param distance The distance to drive, in centimeters. A positive distance corresponds to driving forward; a negative distance corresponds to driving backwards.
     */
    @Override
    public void drive(double distance)
    {
        robot.drive(distance).go();
    }

    /**
     * Instructs the robot to turn a certain angle.
     * @param angle The angle to turn, in degrees. A positive angle corresponds to rotating clockwise; a negative angle corresponds to rotating counterclockwise.
     */
    @Override
    public void rotate(double angle)
    {
        robot.turn(angle).go();
    }

    /**
     * Instructs the robot to extend or retract itself.
     * @param id  The ID number of the extension, arbitrarily assigned.
     * @param pos How far to extend the robot. This is a number from 0.0 to 1.0, where 1.0 is fully extended and 0.0 is fully retracted.
     */
    @Override
    public void setExtension(int id, double pos)
    {
        robot.setExtension(id, pos).go();
    }

    /**
     * Gets the number of extensions on the robot.
     * @return The number of extensions on the robot.
     */
    @Override
    public int getExtensionCount()
    {
        return robot.getExtensionCount();
    }

    /**
     * Gets the currently read color from a particular light sensor.
     * @param id The ID number of the color sensor, arbitrarily assigned.
     * @return The currently read color from the light sensor.
     */
    @Override
    public RgbColor senseColor(int id)
    {
        return robot.senseColor(id);
    }

    /**
     * Gets the number of color sensors on the robot.
     * @return The number of color sensors on the robot.
     */
    @Override
    public int getColorSensorCount()
    {
        return robot.getColorSensorCount();
    }
}
