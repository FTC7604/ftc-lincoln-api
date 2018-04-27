package com.lhsrobotsimulator;

import java.util.concurrent.CountDownLatch;

import javafx.animation.AnimationTimer;
import lhsrobotapi.robot.RobotDrivetrain;

import static java.lang.Math.*;

public class SimulatedRobotDrivetrain implements RobotDrivetrain
{
    private LincolnRobot robot;
    private double xTranslation, yTranslation, robotAngle;
    private double xOffset, yOffset, angleOffset;
    private CountDownLatch threadPauseLatch = new CountDownLatch(0);

    public SimulatedRobotDrivetrain(LincolnRobot robot)
    {
        this.robot = robot;
        reset();
        AnimationTimer interpolationTimer = new AnimationTimer()
        {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                {
                    last = now;
                } else
                {
                    double diff = (now - last) / 1.0e9; // difference in seconds
                    last = now;

                    //// Movement interpolation
                    double movement = SimulatedRobotDrivetrain.this.robot.moveSpeed * diff;
                    // X
                    double xDist = getXTranslation() - SimulatedRobotDrivetrain.this.robot.x;
                    if (xDist < movement)
                    {
                        SimulatedRobotDrivetrain.this.robot.x = getXTranslation();
                    } else
                    {
                        SimulatedRobotDrivetrain.this.robot.x += movement * signum(xDist);
                    }
                    // Y
                    double yDist = getYTranslation() - SimulatedRobotDrivetrain.this.robot.y;
                    if (yDist < movement)
                    {
                        SimulatedRobotDrivetrain.this.robot.y = getYTranslation();
                    } else
                    {
                        SimulatedRobotDrivetrain.this.robot.y += movement * signum(yDist);
                    }

                    //// Rotation interpolation
                    double rotation = SimulatedRobotDrivetrain.this.robot.rotationSpeed * diff;
                    double rotationDist = getAngle() - SimulatedRobotDrivetrain.this.robot.rotation;
                    if (rotationDist < rotation)
                    {
                        SimulatedRobotDrivetrain.this.robot.rotation = getAngle();
                    } else
                    {
                        SimulatedRobotDrivetrain.this.robot.rotation += rotation * signum(rotationDist);
                    }
                    SimulatedRobotDrivetrain.this.robot.rotation = standardizeAngle(SimulatedRobotDrivetrain.this.robot.rotation);

                    boolean movementUnchanged = xDist == 0 && yDist == 0;
                    boolean rotationUnchanged = rotationDist == 0;

                    if (movementUnchanged && rotationUnchanged)
                    {
                        threadPauseLatch.countDown();
                    }
                    else
                    {
                        SimulatedRobotDrivetrain.this.robot.simulator.update(SimulatedRobotDrivetrain.this.robot);
                    }
                }
            }
        };
        interpolationTimer.start();
    }

    private double standardizeAngle(double angle)
    {
        while(angle > 360) angle -= 360;
        while(angle < 0) angle += 360;

        return angle;
    }

    /**
     * Instructs the drivetrain to move the robot forward a certain distance.
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
     *
     * @param distance The distance to travel in cm.
     */
    @Override
    public void driveForward(double distance)
    {
        xTranslation += (distance) * (cos(toRadians(robotAngle)));
        yTranslation += (distance) * (sin(toRadians(robotAngle)));

        threadPauseLatch = new CountDownLatch(1);
        try
        {
            threadPauseLatch.await();
        } catch (InterruptedException e)
        {
            throw new SimulatorException(e) ;
        }
    }

    /**
     * Instructs the drivetrain to move the robot backward a certain distance.
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
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
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
     *
     * @param angle The distance to travel in cm.
     */
    @Override
    public void turnLeft(double angle)
    {
        robotAngle += angle;
        try
        {
            wait();
        } catch (InterruptedException e)
        {
            throw new SimulatorException(e);
        }
        robotAngle = standardizeAngle(robotAngle);
    }

    /**
     * Instructs the drivetrain to rotate the robot in place to the right  a certain angle.
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
     *
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
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
     *
     * @return X translation, in cm.
     */
    @Override
    public double getXTranslation()
    {
        return xTranslation + xOffset;
    }

    /**
     * Gets the distance that the robot has moved in the Y direction.
     * The robot starts facing towards positive Y, with positive X to its right.
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
     *
     * @return Y translation, in cm.
     */
    @Override
    public double getYTranslation()
    {
        return yTranslation + yOffset;
    }

    /**
     * Gets the angle that the robot has rotated, based on the unit circle.
     * The robot starts at a 90 degree angle, as it is facing positive Y.
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
     *
     * @return Angle rotation in degrees.
     */
    @Override
    public double getAngle()
    {
        return robotAngle + angleOffset;
    }

    /**
     * Instructs the drivetrain to reset all its values to the defaults.
     * The translation values default to 0, and the angle to 90 degrees.
     * This implementation simulates the appropriate values for use in a JavaFX simulation.
     */
    @Override
    public void reset()
    {
        xOffset = xTranslation;
        yOffset = yTranslation;
        angleOffset = robotAngle - 90;

        xTranslation = 0;
        yTranslation = 0;
        robotAngle = 90;
    }
}
