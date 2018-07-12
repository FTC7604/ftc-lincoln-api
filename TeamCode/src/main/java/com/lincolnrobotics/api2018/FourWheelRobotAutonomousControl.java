package com.lincolnrobotics.api2018;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * An implementation of {@link RobotAutonomousControl} that runs the command on a physical 4-wheel drive FTC robot using the FTC SDK.
 */
public class FourWheelRobotAutonomousControl implements RobotAutonomousControl
{
    private CommandStack commandStack = new CommandStack();
    private static final int FRONT_LEFT = 0, FRONT_RIGHT = 1, BACK_LEFT = 2, BACK_RIGHT = 3;

    private static class CommandStack
    {
        private static class RunCommand
        {
            Runnable runnable;
            double speed;
            List<Terminator> terminators = new LinkedList<>();

            private RunCommand(Runnable runnable)
            {
                this.runnable = runnable;
            }
        }

        private Deque<RunCommand> commands = new ArrayDeque<>();

        private void push(Runnable runnable)
        {
            commands.push(new RunCommand(runnable));
        }

        private RunCommand pop()
        {
            return commands.pop();
        }

        private void requestSpeed(double speed)
        {
            commands.peek().speed = speed;
        }

        private void addTerminators(Terminator... terminators)
        {
            commands.peek().terminators.addAll(Arrays.asList(terminators));
        }
        private boolean isEmpty()
        {
            return commands.isEmpty();
        }
    }
    /**
     * Issues a command to the robot to drive forward.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl driveForward()
    {
        commandStack.push(() -> {});
        return this;
    }

    /**
     * Issues a command to the robot to drive backward.
     *
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl driveBackward()
    {
        commandStack.push(() -> {});
        return this;
    }

    /**
     * Issues a command to the robot to drive forward for a certain distance (provided in centimeters).
     * @param distance The distance to drive, in centimeters. A positive distance corresponds to driving forward; a negative distance corresponds to driving backwards.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl drive(double distance)
    {
        resetMotorDistance(FRONT_LEFT, FRONT_RIGHT);
        if(distance > 0)
        {
            driveForward();
        }
        else
        {
            driveBackward();
        }
        until(robot -> abs(robot.getDistanceTravelled(FRONT_LEFT)) >= distance && abs(robot.getDistanceTravelled(FRONT_RIGHT)) >= distance);
        return this;
    }

    /**
     * Issues a command to the robot to turn right (clockwise).
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl turnRight()
    {
        commandStack.push(() -> {});
        return this;
    }

    /**
     * Issues a command to the robot to turn left (counter-clockwise).
     *
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl turnLeft()
    {
        commandStack.push(() -> {});
        return this;
    }

    /**
     * Issues a command to the robot to turn right (clockwise) a certain angle (provided in degrees).
     * @param angle The angle to turn, in degrees. A positive angle corresponds to rotating clockwise; a negative angle corresponds to rotating counterclockwise.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl turn(double angle)
    {
        resetMotorDistance(FRONT_LEFT, FRONT_RIGHT);
        if(angle > 0)
        {
            turnRight();
        }
        else
        {
            turnLeft();
        }
        until(robot -> abs(robot.getDistanceTravelled(FRONT_LEFT)) >= angle && abs(robot.getDistanceTravelled(FRONT_RIGHT)) >= angle);
        return this;
    }

    /**
     * Issues a command to the robot to extend itself.*
     * @param id The ID number of the extension, arbitrarily assigned.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl extend(int id)
    {
        commandStack.push(() -> {});
        return this;
    }

    /**
     * Issues a command to the robot to retract itself.
     * @param id The ID number of the extension, arbitrarily assigned.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl retract(int id)
    {
        commandStack.push(() -> {});
        return this;
    }

    /**
     * Issues a command to the robot to extend or retract itself to the provided position.
     * @param id  The ID number of the extension, arbitrarily assigned.
     * @param pos How far to extend the robot. This is a number from 0.0 to 1.0, where 1.0 is fully extended and 0.0 is fully retracted.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl setExtension(int id, double pos)
    {
        extend(id);
        // OR
        retract(id);
        until(a -> false);
        return this;
    }

    /**
     * Gets the number of extensions on the robot.
     * @return The number of extensions on the robot.
     */
    @Override
    public int getExtensionCount()
    {
        return 0;
    }

    /**
     * Gets the currently read color from a particular light sensor.
     * @param id The ID number of the color sensor, arbitrarily assigned.
     * @return The currently read color from the light sensor.
     */
    @Override
    public RgbColor senseColor(int id)
    {
        return new RgbColor(255, (255 * 0x38) / 0xff, 0);
    }

    /**
     * Gets the number of color sensors on the robot.
     * @return The number of color sensors on the robot.
     */
    @Override
    public int getColorSensorCount()
    {
        return 0;
    }

    /**
     * Gets the current distance travelled on a particular DC motor.
     * @param id The ID number of the motor, arbitrarily assigned.
     * @return The current distance travelled in CM.
     */
    @Override
    public double getDistanceTravelled(int id)
    {
        return 0;
    }

    /**
     * Resets the encoder (distance travelled value) on one or many particular DC motors.
     * @param id The ID number(s) of the motor(s), arbitrarily assigned.
     */
    @Override
    public void resetMotorDistance(int... id)
    {

    }

    /**
     * Resets the encoder (distance travelled value) on all DC motors.
     */
    @Override
    public void resetAllMotorDistances()
    {
        for(int i = 0; i < getMotorCount(); i++)
        {
            resetMotorDistance(i);
        }
    }

    /**
     * Gets the number of DC motors on the robot.
     * @return The number of DC motors on the robot.
     */
    @Override
    public int getMotorCount()
    {
        return 0;
    }

    /**
     * Instructs the robot to stop doing anything.
     */
    @Override
    public RobotAutonomousControl pauseRobot()
    {
        commandStack.push(() -> {});
        return this;
    }

    /**
     * Tells the robot when to stop running the previously issued command.
     * Specifically, when the method {@link Terminator#shouldTerminate(RobotAutonomousControl)} returns true, the command will terminate.
     * @param terminators The terminator(s) to add. Multiple terminators may be added to a single command.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl until(Terminator... terminators)
    {
        commandStack.addTerminators(terminators);
        return this;
    }

    /**
     * Requests a speed for the previously issued command. The unit changes depending on the context.
     * @param speed The speed given. Contextually assumed to be either degrees per second or centimeters per second.
     * @return This object (to allow method chaining).
     */
    @Override
    public RobotAutonomousControl requestSpeed(double speed)
    {
        commandStack.requestSpeed(speed);
        return this;
    }

    /**
     * Instructs the robot to execute any queued commands.
     */
    @Override
    public void go()
    {
        while(!commandStack.isEmpty())
        {
            CommandStack.RunCommand command = commandStack.pop();
            command.runnable.run();
        }
    }
}
