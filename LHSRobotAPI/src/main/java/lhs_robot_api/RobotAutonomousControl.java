package lhs_robot_api;

/**
 * Allows robot controllers to be used through this interface.
 * This allows the controller to work in multiple environments.
 *
 * CopyRight 2018 bsteele.com
 * User: bob
 * Date: 3/11/18
 */
public abstract class RobotAutonomousControl
{
    private static final double DEFAULT_MAX_SPEED = 60; // cm/s
    private static final double DEFAULT_MAX_ANGULAR_SPEED = 30; // degrees/s

    public abstract void run();

    public RobotInstructionSet robotInstructionSet;

    protected void driveForward(Distance distance, Speed speed)
    {
        robotInstructionSet.driveForward(distance.value, speed.value);
    }

    protected void driveForward(Distance distance)
    {
        driveForward(distance.value);
    }

    protected void driveForward(double distance, double speed)
    {
        driveForward(distance(distance, CM), speed(speed, CM_PER_SEC));
    }

    protected void driveForward(double distance)
    {
        driveForward(distance, DEFAULT_MAX_SPEED);
    }

    protected void driveBackward(Distance distance, Speed speed)
    {
        robotInstructionSet.driveForward(distance.value, -speed.value);
    }

    protected void driveBackward(Distance distance)
    {
        driveBackward(distance.value);
    }

    protected void driveBackward(double distance, double speed)
    {
        driveBackward(distance(distance, CM), speed(speed, CM_PER_SEC));
    }

    protected void driveBackward(double distance)
    {
        driveBackward(distance, DEFAULT_MAX_SPEED);
    }

    protected void turnLeft(Angle angle, AngularSpeed speed)
    {
        robotInstructionSet.turnLeft(angle.value, speed.value);
    }

    protected void turnLeft(Angle angle)
    {
        turnLeft(angle.value);
    }

    protected void turnLeft(double angle, double speed)
    {
        turnLeft(angle(angle, DEGREES), speed(speed, DEGREES_PER_SECOND));
    }

    protected void turnLeft(double distance)
    {
        turnLeft(distance, DEFAULT_MAX_ANGULAR_SPEED);
    }

    protected void turnRight(Angle angle, AngularSpeed speed)
    {
        robotInstructionSet.turnLeft(angle.value, -speed.value);
    }

    protected void turnRight(Angle angle)
    {
        turnRight(angle.value);
    }

    protected void turnRight(double angle, double speed)
    {
        turnRight(angle(angle, DEGREES), speed(speed, DEGREES_PER_SECOND));
    }

    protected void turnRight(double distance)
    {
        turnRight(distance, DEFAULT_MAX_ANGULAR_SPEED);
    }

    private enum SpeedUnit
    {
        CM_PER_SEC, M_PER_SEC
    }
    protected static final SpeedUnit CM_PER_SEC = SpeedUnit.CM_PER_SEC;
    protected static final SpeedUnit CENTIMETERS_PER_SECOND = SpeedUnit.CM_PER_SEC;
    protected static final SpeedUnit M_PER_SEC = SpeedUnit.M_PER_SEC;
    protected static final SpeedUnit METERS_PER_SECOND = SpeedUnit.CM_PER_SEC;
    private static class Speed
    {
        double value;
        private Speed(double value)
        {
            this.value = value;
        }
    }
    protected static Speed speed(double value, SpeedUnit unit)
    {
        switch (unit)
        {
            case CM_PER_SEC:
                return new Speed(value);
            case M_PER_SEC:
                return new Speed(value * 100);
        }
        throw new UnsupportedOperationException("Unsupported speed unit: " + unit);
    }

    private enum DistanceUnit
    {
        CM, M
    }
    protected static final DistanceUnit CM = DistanceUnit.CM;
    protected static final DistanceUnit CENTIMETERS = DistanceUnit.CM;
    protected static final DistanceUnit M = DistanceUnit.M;
    protected static final DistanceUnit METERS = DistanceUnit.M;
    private static class Distance
    {
        double value;
        private Distance(double value)
        {
            this.value = value;
        }
    }
    protected static Distance distance(double value, DistanceUnit unit)
    {
        switch (unit)
        {
            case CM:
                return new Distance(value);
            case M:
                return new Distance(value * 100);
        }
        throw new UnsupportedOperationException("Unsupported distance unit: " + unit);
    }

    private enum AngleUnit
    {
        DEGREES, RADIANS
    }
    protected static final AngleUnit DEG = AngleUnit.DEGREES;
    protected static final AngleUnit DEGREES = AngleUnit.DEGREES;
    protected static final AngleUnit RAD = AngleUnit.RADIANS;
    protected static final AngleUnit RADIANS = AngleUnit.RADIANS;
    private static class Angle
    {
        double value;
        private Angle(double value)
        {
            this.value = value;
        }
    }
    protected static Angle angle(double value, AngleUnit unit)
    {
        switch (unit)
        {
            case DEGREES:
                return new Angle(Math.PI * value / 180);
            case RADIANS:
                return new Angle(value);
        }
        throw new UnsupportedOperationException("Unsupported angle unit: " + unit);
    }

    private enum AngularSpeedUnit
    {
        DEG_PER_SEC, RAD_PER_SEC
    }
    protected static final AngularSpeedUnit DEG_PER_SEC = AngularSpeedUnit.DEG_PER_SEC;
    protected static final AngularSpeedUnit DEGREES_PER_SECOND = AngularSpeedUnit.DEG_PER_SEC;
    protected static final AngularSpeedUnit RAD_PER_SEC = AngularSpeedUnit.RAD_PER_SEC;
    protected static final AngularSpeedUnit RADIANS_PER_SECOND = AngularSpeedUnit.RAD_PER_SEC;
    private static class AngularSpeed
    {
        double value;
        private AngularSpeed(double value)
        {
            this.value = value;
        }
    }
    protected AngularSpeed speed(double value, AngularSpeedUnit unit)
    {
        switch (unit)
        {
            case DEG_PER_SEC:
                return new AngularSpeed(Math.PI * value / 180);
            case RAD_PER_SEC:
                return new AngularSpeed(value);
        }
        throw new UnsupportedOperationException("Unsupported angular speed unit: " + unit);
    }
}
