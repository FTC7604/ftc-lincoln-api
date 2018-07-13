package com.lincolnrobotics.api2018;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

/**
 * {@inheritDoc}
 * This implementation applies a ramping factor (maximum acceleration) to an existing {@link com.lincolnrobotics.api2018.MotorPowerControl MotorPowerControl}.
 */
public class RampingMotorPowerControl implements MotorPowerControl
{
    private MotorPowerControl baseControl;
    private double maximumAcceleration;

    /**
     * Construct a power control based on an existing control, with a maximum acceleration determined in terms of motor power.
     * @param baseControl The existing power control to use as a base for the one to be constructed.
     * @param maximumPowerAcceleration The maximum acceleration of this control, in units of power per second.
     */
    public RampingMotorPowerControl(MotorPowerControl baseControl, double maximumPowerAcceleration)
    {
        this.baseControl = baseControl;
        this.maximumAcceleration = maximumPowerAcceleration;
    }

    /**
     * Construct a power control based on an existing control, with a maximum acceleration determined in terms of real-world units.
     * @param baseControl The existing power control to use as a base for the one to be constructed.
     * @param maximumAcceleration The maximum acceleration of this control, in units of centimeters per second per second.
     * @param movementControl A robot movement control to convert between the units of distance provided and robot power.
     */
    public RampingMotorPowerControl(MotorPowerControl baseControl, double maximumAcceleration, RobotMovementControl movementControl)
    {
        this(baseControl, movementControl.movementSpeedToRobotPower(maximumAcceleration));
    }

    @Override
    public double calculateMotorPower(double currentPower, double targetPower)
    {
        double optimalValue = baseControl.calculateMotorPower(currentPower, targetPower);
        double difference = optimalValue - currentPower;

        if(abs(difference) > maximumAcceleration)
        {
            return (signum(difference) * maximumAcceleration) + currentPower;
        }

        return optimalValue;
    }
}
