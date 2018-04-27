package com.lhsrobotsimulator;

public class SimulatorException extends RuntimeException
{
    public SimulatorException()
    {
        super();
    }

    public SimulatorException(String message)
    {
        super(message);
    }

    public SimulatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SimulatorException(Throwable cause)
    {
        super(cause);
    }
}
