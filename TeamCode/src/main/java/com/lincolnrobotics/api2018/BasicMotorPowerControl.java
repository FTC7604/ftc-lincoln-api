package com.lincolnrobotics.api2018;

/**
 * {@inheritDoc}
 * This implementation simply returns the target power provided.
 */
public class BasicMotorPowerControl implements MotorPowerControl
{
    @Override
    public double calculateMotorPower(double currentPower, double targetPower)
    {
        return targetPower;
    }
}
