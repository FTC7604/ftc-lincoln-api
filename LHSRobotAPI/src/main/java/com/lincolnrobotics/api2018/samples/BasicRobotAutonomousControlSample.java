package com.lincolnrobotics.api2018.samples;

import com.lincolnrobotics.api2018.RobotAutonomousControl;

public class BasicRobotAutonomousControlSample
{
    static RobotAutonomousControl robot;
    public static void main(String[] args)
    {
        robot.driveForward().until(robot -> robot.senseColor(1).getBlue() >= 128).go();
        robot.driveForward(50);
    }
}
