package com.lincolnrobotics.api2018;

/**
 * A class which provides loopback velocity/power control for an FTC robot motor.
 * @author Sameer Suri
 */
public interface MotorPowerControl
{
    /**
     * Calculates the optimal motor power at the current point at time.
     * @param currentPower The actual current motor power.
     * @param targetPower The target motor power intended to be reached.
     * @return The optimal motor power that the system should adjust to.
     */
    double calculateMotorPower(double currentPower, double targetPower);
}
