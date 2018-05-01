/*
 * Copyright 2016 Robert Steele, bsteele.com
 * All rights reserved.
 */
package lhs_robot_api;

public class SampleRobotAutonomousControl extends RobotAutonomousControl
{
    @Override
    public void run()
    {
//        driveForward(distance(120, CM), speed(60, CM_PER_SEC));
//        turnLeft(angle(90, DEGREES), speed(30, DEGREES_PER_SECOND));
//        driveForward(distance(350, CM), speed(60, CM_PER_SEC));
//        turnRight(angle(90, DEGREES), speed(30, DEGREES_PER_SECOND));
//        driveBackward(distance(100, CM), speed(60, CM_PER_SEC));

        driveBackward(120);
        turnRight(120);
        turnLeft(120);
    }
}
