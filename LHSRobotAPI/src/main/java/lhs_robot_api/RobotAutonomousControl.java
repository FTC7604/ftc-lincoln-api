package lhs_robot_api;

/**
 * Defines an interface to allow control of an FTC robot.
 */
public interface RobotAutonomousControl
{
    /**
     * Issues a command to the robot to drive forward.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl driveForward();

    /**
     * Issues a command to the robot to drive forward for a certain distance (provided in centimeters).
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl driveForward(double distance);

    /**
     * Issues a command to the robot to drive backward.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl driveBackward();

    /**
     * Issues a command to the robot to drive forward for a certain distance (provided in centimeters).
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl driveBackward(double distance);

    /**
     * Issues a command to the robot to turn left.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl turnLeft();

    /**
     * Issues a command to the robot to turn left a certain angle (provided in degrees).
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl turnLeft(double angle);
    
    /**
     * Issues a command to the robot to turn right.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl turnRight();

    /**
     * Tells the robot when to stop running the previously issued command.
     * Specifically, when the method {@link Terminator#shouldTerminate()} returns true, the command will terminate.
     * @param terminators The terminator(s) to add. Multiple terminators may be added to a single command.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl until(Terminator... terminators);

    /**\
     * Requests a speed for the previously issued command. The unit changes depending on the context.
     * @param speed The speed given. Contextually assumed to be either degrees per second or centimeters per second.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl requestSpeed(double speed);

    /**
     * Instructs the robot to execute any queued commands.
     */
    void go();
}
