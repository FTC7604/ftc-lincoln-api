package lhs_robot_api.robot;

import org.junit.Test;

public class IdealRobotDrivetrainTest
{
    private RobotDrivetrainTest drivetrainTester = new RobotDrivetrainTest(new IdealRobotDrivetrain());

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