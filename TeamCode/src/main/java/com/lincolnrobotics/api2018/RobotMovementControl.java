package com.lincolnrobotics.api2018;

/**
 * Defines the conversion between motor power and robot wheel movement.
 * In other words, it converts between units of motor power and centimeters per second.
 */
public interface RobotMovementControl
{
    /**
     * Convert a value in terms of motor power to centimeters per second.
     * @param robotPower The power of the robot motor.
     * @return The speed of the wheel in centimeters per second.
     */
    double robotPowerToMovementSpeed(double robotPower);

    /**
     * Convert a value in terms of wheel/robot speed to motor power.
     * @param movementSpeed The speed of the wheel in centimeters per second.
     * @return The power of the robot motor.
     */
    double movementSpeedToRobotPower(double movementSpeed);
}
