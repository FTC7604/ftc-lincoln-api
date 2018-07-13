package com.lincolnrobotics.api2018;

import com.qualcomm.robotcore.hardware.DcMotor;

import static java.lang.Math.PI;

/**
 * {@inheritDoc}
 * This implementation defines the movement of a "standard" robot (i.e. one with wheels laid out evenly on the left and right sides), with the same motors and wheel sizes.
 */
public class StandardRobotMovementControl implements RobotMovementControl
{
    private int maxTicksPerSecond;
    private int ticksPerRotation;
    private int wheelRadius;

    /**
     * Constructs an instance based on the parameters of the motors and wheels.
     * @param maxTicksPerSecond The number of encoder ticks turned per second at maximum power.
     * @param ticksPerRotation The number of encoder ticks turned in a single rotation of the motor.
     * @param wheelRadius The radius of the wheel.
     */
    public StandardRobotMovementControl(int maxTicksPerSecond, int ticksPerRotation, int wheelRadius)
    {
        this.maxTicksPerSecond = maxTicksPerSecond;
        this.ticksPerRotation = ticksPerRotation;
        this.wheelRadius = wheelRadius;
    }

    /**
     * Constructs an instance based on the parameters of the motors and wheels.
     * @param ftcMotor The FTC DcMotor object corresponding to the motor.
     * @param wheelRadius The radius of the wheel.
     */
    public StandardRobotMovementControl(DcMotor ftcMotor, int wheelRadius)
    {
        this(ftcMotor.getMotorType().getAchieveableMaxTicksPerSecondRounded(), (int) ftcMotor.getMotorType().getTicksPerRev(), wheelRadius);
    }

    @Override
    public double robotPowerToMovementSpeed(double robotPower)
    {
        return (robotPower * maxTicksPerSecond * 2 * PI * wheelRadius) / ticksPerRotation;
    }

    @Override
    public double movementSpeedToRobotPower(double movementSpeed)
    {
        return (movementSpeed * ticksPerRotation) / (maxTicksPerSecond * 2 * PI * wheelRadius);
    }
}
