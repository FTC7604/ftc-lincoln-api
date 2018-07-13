package com.lincolnrobotics.api2018;

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
     * Issues a command to the robot to drive backward.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl driveBackward();

    /**
     * Issues a command to the robot to drive forward for a certain distance (provided in centimeters).
     * If a negative distance is passed, the robot should move backward.
     * @param distance The distance to drive, in centimeters.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl driveForward(double distance);

    /**
     * Issues a command to the robot to drive backward for a certain distance (provided in centimeters).
     * If a negative distance is passed, the robot should move forward.
     * @param distance The distance to drive, in centimeters.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl driveBackward(double distance);

    /**
     * Issues a command to the robot to turn right (clockwise).
     * Should perform a "point turn"; i.e. the center of the robot does not move.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl turnRight();

    /**
     * Issues a command to the robot to turn left (counter-clockwise).
     * Should perform a "point turn"; i.e. the center of the robot does not move.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl turnLeft();

    /**
     * Issues a command to the robot to turn right (clockwise) a certain angle (provided in degrees).
     * If a negative angle is passed, the robot should turn left (counter-clockwise).
     * Should perform a "point turn"; i.e. the center of the robot does not move.
     * @param angle The angle to turn, in degrees.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl turnRight(double angle);

    /**
     * Issues a command to the robot to turn left (counter-clockwise) a certain angle (provided in degrees).
     * If a negative distance is passed, the robot should turn right (clockwise).
     * Should perform a "point turn"; i.e. the center of the robot does not move.
     * @param angle The angle to turn, in degrees.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl turnLeft(double angle);

    /**
     * Issues a command to the robot to extend itself.
     * @param id  The ID number of the extension, arbitrarily assigned.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl extend(int id);

    /**
     * Issues a command to the robot to retract itself.
     * @param id  The ID number of the extension, arbitrarily assigned.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl retract(int id);

    /**
     * Issues a command to the robot to extend or retract itself to the provided position.
     * @param id  The ID number of the extension, arbitrarily assigned.
     * @param pos How far to extend the robot. This is a number from 0.0 to 1.0, where 1.0 is fully extended and 0.0 is fully retracted.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl extendTo(int id, double pos);

    /**
     * Gets the number of extensions on the robot.
     * @return The number of extensions on the robot.
     */
    int getExtensionCount();

    /**
     * Gets the currently read color from a particular light sensor.
     * @param id The ID number of the color sensor, arbitrarily assigned.
     * @return The currently read color from the light sensor.
     */
    RgbColor senseColor(int id);

    /**
     * Gets the number of color sensors on the robot.
     * @return The number of color sensors on the robot.
     */
    int getColorSensorCount();

    /**
     * Gets the current distance travelled on a particular DC motor.
     * @param id The ID number of the motor, arbitrarily assigned.
     * @return The current distance travelled in CM.
     */
    double getDistanceTravelled(int id);

    /**
     * Resets the encoder (distance travelled value) on one or many particular DC motors.
     * @param id The ID number(s) of the motor(s), arbitrarily assigned.
     */
    void resetMotorDistance(int... id);

    /**
     * Resets the encoder (distance travelled value) on all DC motors.
     */
    void resetAllMotorDistances();

    /**
     * Gets the number of DC motors on the robot.
     * @return The number of DC motors on the robot.
     */
    int getMotorCount();

    /**
     * Instructs the robot to stop doing anything.
     */
    RobotAutonomousControl pauseRobot();

    /**
     * Tells the robot when to stop running the previously issued command.
     * Specifically, when the method {@link Terminator#shouldTerminate(RobotAutonomousControl)} returns true, the command will terminate.
     * @param terminators The terminator(s) to add. Multiple terminators may be added to a single command.
     * @return This object (to allow method chaining).
     */
    RobotAutonomousControl until(Terminator... terminators);

    /**
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
