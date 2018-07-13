package com.lincolnrobotics.api2018;

/**
 * An implementation of {@link SimpleRobotAutonomousControl} that uses a provided {@link RobotAutonomousControl} to execute its commands.
 * Essentially, this class "passes through" all values and provided commands to the given {@link RobotAutonomousControl}.
 * TODO: inherit javadoc and add implementation details
 * TODO: unit test
 */
public class PassthroughSimpleRobotAutonomousControl implements SimpleRobotAutonomousControl
{
    private RobotAutonomousControl robot;

    /**
     * Constructs an instance given a {@link com.lincolnrobotics.api2018.RobotAutonomousControl RobotAutonomousControl} to pass values to.
     * @param robot
     */
    public PassthroughSimpleRobotAutonomousControl(RobotAutonomousControl robot)
    {
        this.robot = robot;
    }

    @Override
    public void driveForward(double distance)
    {
        robot.driveForward(distance).go();
    }

    @Override
    public void driveBackward(double distance)
    {
        robot.driveBackward(distance).go();
    }

    @Override
    public void turnRight(double angle)
    {
        robot.turnRight(angle).go();
    }

    @Override
    public void turnLeft(double angle)
    {
        robot.turnLeft(angle).go();
    }

    @Override
    public void extendTo(int id, double pos)
    {
        robot.extendTo(id, pos).go();
    }

    @Override
    public int getExtensionCount()
    {
        return robot.getExtensionCount();
    }

    @Override
    public RgbColor senseColor(int id)
    {
        return robot.senseColor(id);
    }

    @Override
    public int getColorSensorCount()
    {
        return robot.getColorSensorCount();
    }
}
