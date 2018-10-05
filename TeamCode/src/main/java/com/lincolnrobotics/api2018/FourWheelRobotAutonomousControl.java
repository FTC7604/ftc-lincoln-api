package com.lincolnrobotics.api2018;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Predicate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * An implementation of {@link RobotAutonomousControl} that runs the command on a physical 4-wheel drive FTC robot using the FTC SDK.
 */
public class FourWheelRobotAutonomousControl implements RobotAutonomousControl
{
    private RunAction currentCommand = null;
    private Runnable postExec = () -> {};
    private List<Terminator> terminators = new LinkedList<>();
    private double speed = -1;
    private int FRONT_LEFT = 0, FRONT_RIGHT = 1, BACK_LEFT = 2, BACK_RIGHT = 3;
    private DcMotor[] motors = new DcMotor[4];
    private int[] motorBaseline = new int[motors.length];

    public FourWheelRobotAutonomousControl(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight)
    {
        motors[FRONT_LEFT] = frontLeft;
        motors[FRONT_RIGHT] = frontRight;
        motors[BACK_LEFT] = backLeft;
        motors[BACK_RIGHT] = backRight;
    }

    private interface RunAction
    {
        void run(double speed);
    }

    @Override
    public RobotAutonomousControl driveForward()
    {
        queueAction(this::ftcDriveForward);
        afterExecution(this::ftcStop);
        return this;
    }

    @Override
    public RobotAutonomousControl driveBackward()
    {
        queueAction(this::ftcDriveBackward);
        afterExecution(this::ftcStop);
        return this;
    }

    @Override
    public RobotAutonomousControl driveForward(double distance)
    {
        resetMotorDistance(FRONT_LEFT);
        driveForward();
        until(robot -> getDistanceTravelled(FRONT_LEFT) >= distance);
        return this;
    }

    @Override
    public RobotAutonomousControl driveBackward(double distance)
    {
        resetMotorDistance(FRONT_LEFT);
        driveBackward();
        until(robot -> getDistanceTravelled(FRONT_LEFT) >= distance);
        return this;
    }

    private void ftcStop()
    {
        runMotors(0, 0);
    }

    private void ftcDriveForward(double targetSpeed)
    {
        runMotors(targetSpeed, targetSpeed);
    }

    private void ftcDriveBackward(double targetSpeed)
    {
        ftcDriveForward(-targetSpeed);
    }

    private void ftcTurnRight(double targetSpeed)
    {
        runMotors(targetSpeed, -targetSpeed);
    }

    private void ftcTurnLeft(double targetSpeed)
    {
        ftcTurnRight(-targetSpeed);
    }

    private void runMotors(double leftSpeed, double rightSpeed)
    {
        motors[FRONT_LEFT].setPower(leftSpeed);
        motors[BACK_LEFT].setPower(leftSpeed);
        motors[FRONT_RIGHT].setPower(rightSpeed);
        motors[BACK_RIGHT].setPower(rightSpeed);
    }

    @Override
    public RobotAutonomousControl turnRight()
    {
        queueAction(this::ftcTurnRight);
        afterExecution(this::ftcStop);
        return this;
    }

    @Override
    public RobotAutonomousControl turnLeft()
    {
        queueAction(this::ftcTurnLeft);
        afterExecution(this::ftcStop);
        return this;
    }

    @Override
    public RobotAutonomousControl turnRight(double angle)
    {
        resetMotorDistance(FRONT_LEFT, FRONT_RIGHT);
        if (angle > 0)
        {
            turnRight();
        } else
        {
            turnLeft();
        }
        until(robot -> abs(robot.getDistanceTravelled(FRONT_LEFT)) >= angle && abs(robot.getDistanceTravelled(FRONT_RIGHT)) >= angle);
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
        queueAction(speed -> {});
        afterExecution(() -> {});
        return this;
    }

    @Override
    public RobotAutonomousControl retract(int id)
    {
        queueAction(speed -> {});
        afterExecution(() -> {});
        return this;
    }

    @Override
    public RobotAutonomousControl extendTo(int id, double pos)
    {
        if(getExtensionValue(id) > pos)
        {
            extend(id);
            until(robot -> getExtensionValue(id) >= pos);
        }
        else
        {
            retract(id);
            until(robot -> getExtensionValue(id) <= pos);
        }

        return this;
    }

    @Override
    public int getExtensionCount()
    {
        return 0;
    }

    @Override
    public int getExtensionValue(int id)
    {
        throw new ArrayIndexOutOfBoundsException();
    }


    @Override
    public RgbColor senseColor(int id)
    {
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public int getColorSensorCount()
    {
        return 0;
    }

    @Override
    public double getDistanceTravelled(int id)
    {
        return motors[id].getCurrentPosition() - motorBaseline[id];
    }

    @Override
    public void resetMotorDistance(int... id)
    {
        for(int i : id) {
            motorBaseline[i] = motors[i].getCurrentPosition();
        }
    }

    @Override
    public void resetAllMotorDistances()
    {
        for (int i = 0; i < getMotorCount(); i++)
        {
            resetMotorDistance(i);
        }
    }

    @Override
    public int getMotorCount()
    {
        return 4;
    }

    @Override
    public RobotAutonomousControl pauseRobot()
    {
        queueAction(robot -> ftcStop());
        return this;
    }

    @Override
    public RobotAutonomousControl until(Terminator... terminators)
    {
        this.terminators.addAll(Arrays.asList(terminators));
        return this;
    }

    @Override
    public RobotAutonomousControl requestSpeed(double speed)
    {
        this.speed = speed;
        return this;
    }

    private void queueAction(RunAction action)
    {
        terminators.clear();
        speed = -1;
        currentCommand = action;
    }

    @Override
    public RobotAutonomousControl afterExecution(Runnable postExec)
    {
        this.postExec = postExec;
        return this;
    }

    @Override
    public void go()
    {
        currentCommand.run(speed);

        // Returns true if all terminators return false
        final Predicate<List<Terminator>> allFalse = terminators -> {
            for (Terminator terminator : terminators)
            {
                if (terminator.shouldTerminate(FourWheelRobotAutonomousControl.this))
                {
                    return false;
                }
            }
            return true;
        };

        // While no terminators return true, wait
        // noinspection StatementWithEmptyBody
        while (allFalse.test(terminators));

        postExec.run();
    }
}
