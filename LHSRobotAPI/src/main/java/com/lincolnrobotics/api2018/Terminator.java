package com.lincolnrobotics.api2018;

/**
 * Defines a condition upon when a robot action (driving, turning, etc.) should terminate.
 */
@FunctionalInterface
public interface Terminator
{
    /**
     * A function that returns a boolean value corresponding to whether or not the robot action should terminate.
     * @param robot The robot performing the action.
     * @return true if the terminator's condition is met (the robot action should terminate), false if not
     */
    boolean shouldTerminate(RobotAutonomousControl robot);
}
