/*
 * Copyright 2016 Robert Steele, bsteele.com
 * All rights reserved.
 */
package lhs_robot_simulation;

import lhs_robot_api.RgbColor;
import lhs_robot_api.RobotAutonomousControl;
import lhs_robot_api.RobotInstructionSet;
import lhs_robot_api.TerminationException;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.abs;

/** Robot control sequencer to run a sequence of robot motion instructions
 * to the simulated robot.
 * <p>
 *     The implementation uses two threads.  This allows the setup to return
 *     immediately but still do work over time.  The first thread runs the autonomous
 *     control robot instruction sequence from the robot controls, suspending until a
 *     requested instruction has completed.  A termination exception is thrown if the
 *     instruction sequence has been terminated.   The second thread is used to simulate
 *     the given instruction.  This may take a number of sample periods before it's
 *     completed... just as it would with a real robot.
 * </p>
 * @author bob
 */
public final class RobotSimulationControlSequencer implements RobotInstructionSet, RestartEventHandler {

    private static final BooleanSupplier falseSupplier = () -> false;
    private BooleanSupplier notifyCondition = falseSupplier;
    private double distanceTravelled, distanceRotated;
    private final RobotAutonomousControl robotAutonomousControl;
    private final RobotModel robotModel;
    private double power, angularPower;
    private static final double sampleHertz = 60;
    private boolean doRun = true;
    private static final Logger logger = Logger.getLogger(RobotSimulationControlSequencer.class.getName());

    /**
     * Start simulating a robot by emulating robot command sequences
     * from the control on the given robot.
     *
     * @param robotAutonomousControl
     * @param robotModel
     */
    RobotSimulationControlSequencer(RobotAutonomousControl robotAutonomousControl, RobotModel robotModel) {
        this.robotAutonomousControl = robotAutonomousControl;
        this.robotModel = robotModel;
    }

    public void driveForward(double distance, double speed) {
        System.out.printf("driveForward( %f, %f )%n", distance, speed);
        this.power = speed / sampleHertz; // 1 pixel = 1 cm
        distanceTravelled = 0;
        notifyCondition = () -> abs(distanceTravelled) >= abs(distance);
        pause();
        this.power = 0;
    }

    public void turnLeft(double angle, double speed) {
        System.out.printf("turnLeft( %f, %f )%n", angle, speed);
        this.angularPower = -speed / sampleHertz;
        this.distanceRotated = 0;
        notifyCondition = () -> abs(distanceRotated) >= abs(angle);
        pause();
        this.angularPower = 0;
    }

    private void pause()
    {
        synchronized (RobotSimulationControlSequencer.this) {
            try {
                RobotSimulationControlSequencer.this.wait();
                if (!doRun)
                    throw new TerminationException("Control sequencing has been terminated.");
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    private final Runnable target = new Runnable() {
        @Override
        public void run() {
            while (doRun) {
                try {
                    Thread.sleep((int) (1000 / sampleHertz));
                } catch (InterruptedException ex) {
                    // do nothing
                }
                if (notifyCondition.getAsBoolean()) {
                    notifyCondition = falseSupplier;
                    synchronized (RobotSimulationControlSequencer.this) {
                        //System.out.println();
                        RobotSimulationControlSequencer.this.notify();
                    }
                }

                distanceTravelled += power;
                distanceRotated += angularPower;

                robotModel.setLocation(
                        robotModel.getX() + power * Math.sin(robotModel.getRot()),
                        robotModel.getY() + power * Math.cos(robotModel.getRot()));
                robotModel.setRot(robotModel.getRot() + angularPower);
            }
            //  get the  autonomous control thread to terminate, if it hasn't
            synchronized (RobotSimulationControlSequencer.this) {
                //System.out.println();
                RobotSimulationControlSequencer.this.notifyAll();
            }
        }

    };
    private Thread timingThread;

    private final Runnable autonomousControl = new Runnable() {
        @Override
        public void run() {
            robotAutonomousControl.robotInstructionSet = RobotSimulationControlSequencer.this;
            robotAutonomousControl.run();
            doRun = false;
        }

    };
    private Thread autonomousControlThread;

    public void start() {
        doRun = true;
        timingThread = new Thread(target);
        timingThread.start();
        autonomousControlThread = new Thread(autonomousControl);
        autonomousControlThread.start();
    }

    public void stop() {
        doRun = false;
        //  wait for end of threads
        while (timingThread.isAlive() || autonomousControlThread.isAlive()) {
            try {
                Thread.sleep((int) (2000 / sampleHertz));
            } catch (InterruptedException ex) {
                //  do nothing
            }
        }
    }

    @Override
    public void onRestartEvent(RestartEvent event) {
        stop();
        robotModel.reset();
        start();
    }
}
