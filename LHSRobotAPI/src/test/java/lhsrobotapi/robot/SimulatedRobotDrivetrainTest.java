package lhsrobotapi.robot;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimulatedRobotDrivetrainTest
{
    private RobotDrivetrainTest drivetrainTester = new RobotDrivetrainTest(new SimulatedRobotDrivetrain());

    @Test
    public void driveForward()
    {
        drivetrainTester.driveForward();
    }

    @Test
    public void driveBackward()
    {
        drivetrainTester.driveBackward();
    }

    @Test
    public void turnLeft()
    {
        drivetrainTester.turnLeft();
    }

    @Test
    public void turnRight()
    {
        drivetrainTester.turnRight();
    }

    @Test
    public void reset()
    {
        drivetrainTester.reset();
    }
}