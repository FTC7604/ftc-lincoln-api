package com.lincolnrobotics.api2018;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class LHSOpMode extends LinearOpMode
{
    private RobotAutonomousControl control;
    protected LHSOpMode(RobotAutonomousControl control) {
        this.control = control;
    }

    @Override
    public void runOpMode() {
        run(control);
    }

    public abstract void run(RobotAutonomousControl control);
}
