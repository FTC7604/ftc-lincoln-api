package lhsrobotapi.robot;

/**
 * Defines the methods for a robot drivetrain, which gives a robot its mobility.
 */
public interface RobotDrivetrain
{
    /**
     * Instructs the drivetrain to move the robot forward a certain distance.
     * @param distance The distance to travel in cm.
     */
    void driveForward(double distance);

    /**
     * Instructs the drivetrain to move the robot backward a certain distance.
     * @param distance The distance to travel in cm.
     */
    void driveBackward(double distance);

    /**
     * Instructs the drivetrain to rotate the robot in place to the left a certain angle.
     * @param angle The distance to travel in cm.
     */
    void turnLeft(double angle);

    /**
     * Instructs the drivetrain to rotate the robot in place to the right  a certain angle.
     * @param angle The distance to travel in cm.
     */
    void turnRight(double angle);

    /**
     * Gets the distance that the robot has moved in the X direction.
     * The robot starts facing towards positive Y, with positive X to its right.
     *
     * @return X translation, in cm.
     */
    double getXTranslation();

    /**
     * Gets the distance that the robot has moved in the Y direction.
     * The robot starts facing towards positive Y, with positive X to its right.
     *
     * @return Y translation, in cm.
     */
    double getYTranslation();

    /**
     * Gets the angle that the robot has rotated, based on the unit circle.
     * The robot starts at a 90 degree angle, as it is facing positive Y.
     *
     * @return Angle rotation in degrees.
     */
    double getAngle();

    /**
     * Instructs the drivetrain to reset all its values to the defaults.
     * The translation values default to 0, and the angle to 90 degrees.
     */
    void reset();
}
