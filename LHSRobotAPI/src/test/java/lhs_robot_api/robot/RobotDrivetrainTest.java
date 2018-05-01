package lhs_robot_api.robot;

public class RobotDrivetrainTest
{
    private RobotDrivetrain drivetrain;
    private static final double DOUBLE_THRESHOLD = 1e-6;

    public RobotDrivetrainTest(RobotDrivetrain drivetrain)
    {
        this.drivetrain = drivetrain;
    }

    public void driveForward()
    {
        drivetrain.reset();
        drivetrain.driveForward(10);
        assertEquals(10, drivetrain.getYTranslation(), DOUBLE_THRESHOLD);
    }

    public void driveBackward()
    {
        drivetrain.reset();
        drivetrain.driveBackward(10);
        assertEquals(-10, drivetrain.getYTranslation(), DOUBLE_THRESHOLD);
    }

    public void turnLeft()
    {
        drivetrain.reset();
        drivetrain.turnLeft(90);
        assertEquals(180, drivetrain.getAngle(), DOUBLE_THRESHOLD);
        drivetrain.driveForward(10);
        assertEquals(-10, drivetrain.getXTranslation(), DOUBLE_THRESHOLD);
    }

    public void turnRight()
    {
        drivetrain.reset();
        drivetrain.turnRight(90);
        assertEquals(0, drivetrain.getAngle(), DOUBLE_THRESHOLD);
        drivetrain.driveForward(10);
        assertEquals(10, drivetrain.getXTranslation(), DOUBLE_THRESHOLD);
    }
    
    public void reset()
    {
        drivetrain.reset();
        assertEquals(0, drivetrain.getXTranslation(), DOUBLE_THRESHOLD);
        assertEquals(0, drivetrain.getYTranslation(), DOUBLE_THRESHOLD);
        assertEquals(90, drivetrain.getAngle(), DOUBLE_THRESHOLD);
    }
}