package lhsrobotapi.robot;

import javax.inject.Inject;

import static java.lang.Math.*;

/**
 * Implements a pseudo-robot drivetrain that just simulates the values provided.
 */
public class SimulatedRobotDrivetrain implements RobotDrivetrain
{
    private double xTranslation, yTranslation, robotAngle;

    @Inject
    public SimulatedRobotDrivetrain()
    {
        reset();
    }

    private void standardizeAngle()
    {
        while(robotAngle > 360) robotAngle -= 360;
        while(robotAngle < 0) robotAngle += 360;
    }

    /**
     * Instructs the drivetrain to move the robot forward a certain distance.
     * This implementation only simulates the appropriate values.
     *
     * @param distance The distance to travel in cm.
     */
    @Override
    public void driveForward(double distance)
    {
        xTranslation += (distance) * (cos(toRadians(robotAngle)));
        yTranslation += (distance) * (sin(toRadians(robotAngle)));
    }

    /**
     * Instructs the drivetrain to move the robot backward a certain distance.
     * This implementation only simulates the appropriate values.
     *
     * @param distance The distance to travel in cm.
     */
    @Override
    public void driveBackward(double distance)
    {
        driveForward(-distance);
    }

    /**
     * Instructs the drivetrain to rotate the robot in place to the left a certain angle.
     * This implementation only simulates the appropriate values.
     *
     * @param angle The distance to travel in cm.
     */
    @Override
    public void turnLeft(double angle)
    {
        robotAngle += angle;
        standardizeAngle();
    }

    /**
     * Instructs the drivetrain to rotate the robot in place to the right  a certain angle.
     * This implementation only simulates the appropriate values.

     * @param angle The distance to travel in cm.
     */
    @Override
    public void turnRight(double angle)
    {
        turnLeft(-angle);
    }

    /**
     * Gets the distance that the robot has moved in the X direction.
     * The robot starts facing towards positive Y, with positive X to its right.
     *
     * @return X translation, in cm.
     */
    @Override
    public double getXTranslation()
    {
        return xTranslation;
    }

    /**
     * Gets the distance that the robot has moved in the Y direction.
     * The robot starts facing towards positive Y, with positive X to its right.
     *
     * @return Y translation, in cm.
     */
    @Override
    public double getYTranslation()
    {
        return yTranslation;
    }

    /**
     * Gets the angle that the robot has rotated, based on the unit circle.
     * The robot starts at a 90 degree angle, as it is facing positive Y.
     *
     * @return Angle rotation in degrees.
     */
    @Override
    public double getAngle()
    {
        return robotAngle;
    }

    /**
     * Instructs the drivetrain to reset all its values to the defaults.
     */
    @Override
    public void reset()
    {
        xTranslation = 0;
        yTranslation = 0;
        robotAngle = 90;
    }
}
