package com.lincolnrobotics.api2018;

/**
 * {@inheritDoc}
 * This implementation uses a Proportional/Integral/Differential (PID) controller to find the optimal motor power.
 */
public class PIDMotorPowerControl implements MotorPowerControl
{
    private double proportionalConstant, integralConstant, differentialConstant;

    private boolean firstIteration = true;
    private double integral;
    private double lastError;
    private long lastTime;

    /**
     * Constructs an instance using provided PID constants.
     * @param multiplier Defines the constant by which to multiply all terms
     * @param proportionalConstant Defines the constant by which to multiply the proportional term
     * @param integralConstant Defines the constant by which to multiply the integral term
     * @param differentialConstant Defines the constant by which to multiply the differential term
     */
    public PIDMotorPowerControl(double multiplier, double proportionalConstant, double integralConstant, double differentialConstant)
    {
        this.proportionalConstant = multiplier * proportionalConstant;
        this.integralConstant = multiplier * integralConstant;
        this.differentialConstant = multiplier * differentialConstant;
    }

    @Override
    public double calculateMotorPower(double currentPower, double targetPower)
    {
        double error = targetPower - currentPower;
        long time = System.currentTimeMillis();

        double dx = error - lastError;
        long dt = time - lastTime;

        integral += error * dt;
        double derivative = dx / dt;

        if(firstIteration)
        {
            derivative = 0;
            firstIteration = false;
        }

        double proportionalTerm = proportionalConstant * error;
        double integralTerm = integralConstant * integral;
        double differentialTerm = differentialConstant * derivative;

        lastError = error;
        lastTime = time;

        return currentPower + proportionalTerm + integralTerm + differentialTerm;
    }
}
