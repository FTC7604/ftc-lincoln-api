package com.lincolnrobotics.api2018;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImpl;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

import static com.lincolnrobotics.api2018.FourWheelRobotAutonomousControl.Wheel.*;

/**
 * An implementation of {@link RobotAutonomousControl} that runs the command on a physical 4-wheel drive FTC robot using the FTC SDK.
 * TODO: inherit javadoc and add implementation details
 * TODO: unit test
 */
public class FourWheelRobotAutonomousControl implements RobotAutonomousControl
{
    private CommandStack commandStack = new CommandStack();
    protected enum Wheel { FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT }
    private MotorPowerControl frontLeftControl, frontRightControl, backLeftControl, backRightControl;
    private RobotMovementControl robotMovementControl;

    private static class CommandStack
    {
        public interface RunAction
        {
            void run(double speed);
        }

        public static class RunCommand
        {
            RunAction action;
            double speed;
            List<Terminator> terminators = new LinkedList<>();

            private RunCommand(RunAction action)
            {
                this.action = action;
            }
        }

        private Deque<RunCommand> commands = new ArrayDeque<>();

        public void push(RunAction action)
        {
            commands.push(new RunCommand(action));
        }

        public RunCommand pop()
        {
            return commands.pop();
        }

        public RunCommand peek()
        {
            return commands.peek();
        }

        public void requestSpeed(double speed)
        {
            peek().speed = speed;
        }

        public void addTerminators(Terminator... terminators)
        {
            peek().terminators.addAll(Arrays.asList(terminators));
        }
        public boolean isEmpty()
        {
            return commands.isEmpty();
        }
    }

    /**
     * Method that is triggered in the FTC or simulator event loop and handles execution of actions.
     * TODO rename
     */
    public void eventLoop()
    {
        CommandStack.RunCommand currentCommand = commandStack.peek();
        for (Terminator terminator : currentCommand.terminators)
        {
            if(terminator.shouldTerminate(this))
            {
                // TODO terminate;
                return;
            }
        }

        currentCommand.action.run(currentCommand.speed);
    }

    private void ftcDriveForward(double targetSpeed)
    {
        double targetPower = robotMovementControl.movementSpeedToRobotPower(targetSpeed);

        getFtcMotor(FRONT_LEFT).setPower(frontLeftControl.calculateMotorPower(getFtcMotor(FRONT_LEFT).getPower(), targetPower));
        getFtcMotor(FRONT_RIGHT).setPower(frontRightControl.calculateMotorPower(getFtcMotor(FRONT_RIGHT).getPower(), targetPower));
        getFtcMotor(BACK_LEFT).setPower(backLeftControl.calculateMotorPower(getFtcMotor(BACK_LEFT).getPower(), targetPower));
        getFtcMotor(BACK_RIGHT).setPower(backRightControl.calculateMotorPower(getFtcMotor(BACK_RIGHT).getPower(), targetPower));
    }

    @Override
    public RobotAutonomousControl driveForward()
    {
        // noinspection Convert2MethodRef
        commandStack.push(speed -> ftcDriveForward(speed));
        return this;
    }

    @Override
    public RobotAutonomousControl driveBackward()
    {
        commandStack.push(speed -> ftcDriveForward(-speed));
        return this;
    }

    @Override
    public RobotAutonomousControl driveForward(double distance)
    {
        // TODO: implement position-based control
        return this;
    }

    @Override
    public RobotAutonomousControl driveBackward(double distance)
    {
        return this;
    }

    private void ftcTurnRight(double targetSpeed)
    {
        double targetPower = robotMovementControl.movementSpeedToRobotPower(targetSpeed);

        getFtcMotor(FRONT_LEFT).setPower(frontLeftControl.calculateMotorPower(getFtcMotor(FRONT_LEFT).getPower(), targetPower));
        getFtcMotor(FRONT_RIGHT).setPower(-frontRightControl.calculateMotorPower(getFtcMotor(FRONT_RIGHT).getPower(), targetPower));
        getFtcMotor(BACK_LEFT).setPower(backLeftControl.calculateMotorPower(getFtcMotor(BACK_LEFT).getPower(), targetPower));
        getFtcMotor(BACK_RIGHT).setPower(-backRightControl.calculateMotorPower(getFtcMotor(BACK_RIGHT).getPower(), targetPower));
    }

    @Override
    public RobotAutonomousControl turnRight()
    {
        // noinspection Convert2MethodRef
        commandStack.push((speed) -> ftcTurnRight(speed));
        return this;
    }

    @Override
    public RobotAutonomousControl turnLeft()
    {
        commandStack.push((speed) -> ftcTurnRight(-speed));
        return this;
    }

    @Override
    public RobotAutonomousControl turnRight(double angle)
    {
        // TODO: implement position-based control
        resetMotorDistance(FRONT_LEFT.ordinal(), FRONT_RIGHT.ordinal());
        if(angle > 0)
        {
            turnRight();
        }
        else
        {
            turnLeft();
        }
        until(robot -> abs(robot.getDistanceTravelled(Wheel.FRONT_LEFT.ordinal())) >= angle && abs(robot.getDistanceTravelled(Wheel.FRONT_RIGHT.ordinal())) >= angle);
        return this;
    }

    @Override
    public RobotAutonomousControl turnLeft(double angle)
    {
        return turnRight(-angle);
    }

    @Override
    public RobotAutonomousControl extend(int id)
    {
        commandStack.push((speed) -> {});
        return this;
    }

    @Override
    public RobotAutonomousControl retract(int id)
    {
        commandStack.push((speed) -> {});
        return this;
    }

    @Override
    public RobotAutonomousControl extendTo(int id, double pos)
    {
        extend(id);
        // OR
        retract(id);
        until(a -> false);
        return this;
    }

    @Override
    public int getExtensionCount()
    {
        return 0;
    }

    @Override
    public RgbColor senseColor(int id)
    {
        return new RgbColor(255, (255 * 0x38) / 0xff, 0);
    }

    @Override
    public int getColorSensorCount()
    {
        return 0;
    }

    @Override
    public double getDistanceTravelled(int id)
    {
        return 0;
    }

    @Override
    public void resetMotorDistance(int... id)
    {

    }

    @Override
    public void resetAllMotorDistances()
    {
        for(int i = 0; i < getMotorCount(); i++)
        {
            resetMotorDistance(i);
        }
    }

    @Override
    public int getMotorCount()
    {
        return 0;
    }

    @Override
    public RobotAutonomousControl pauseRobot()
    {
        commandStack.push((speed) -> {});
        return this;
    }

    @Override
    public RobotAutonomousControl until(Terminator... terminators)
    {
        commandStack.addTerminators(terminators);
        return this;
    }

    @Override
    public RobotAutonomousControl requestSpeed(double speed)
    {
        commandStack.requestSpeed(speed);
        return this;
    }

    @Override
    public void go()
    {
        while(!commandStack.isEmpty())
        {
            CommandStack.RunCommand command = commandStack.pop();
            command.action.run(command.speed);
        }
    }

    private DcMotor getFtcMotor(Wheel wheel)
    {
        // TODO implement getFtcMotor(Wheel wheel)
        return new DcMotorImpl(null, 0);
    }
}
