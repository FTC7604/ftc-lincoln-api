package com.lincolnrobotics.api2018;

/**
 * CopyRight 2018 bsteele.com
 * User: bob

 */
public class TerminationException extends RuntimeException {
    /**
     * Thrown when the control session has been terminated early
     * and the autonomous control period has ended.
     * 
     * @param message  message sent to user on early termination
     */
    public TerminationException( String message ) {
        super(message);
    }
}
