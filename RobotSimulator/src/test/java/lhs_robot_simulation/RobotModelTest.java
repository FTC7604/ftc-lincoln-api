package lhs_robot_simulation;

import lhs_robot_simulation.RobotModel;

import static org.junit.Assert.*;

public class RobotModelTest
{

    @org.junit.Test
    public void setLocation()
    {
        int width = 30;
        int height = 20;

        System.out.println("test setLocation()");
        RobotModel robotModel = new RobotModel(width, height);

        //  verify initial location
        assertEquals(width / 2, robotModel.getX(), 1e-30);
        assertEquals(height / 2, robotModel.getY(), 1e-30);

        //  verify max location
        double w = width;
        double h = height;
        robotModel.setLocation(w, h);
        assertEquals(width, robotModel.getX(), 1e-30);
        assertEquals(height, robotModel.getY(), 1e-30);
        robotModel.setLocation(2 * w, 2 * h);
        assertEquals(width, robotModel.getX(), 1e-30);
        assertEquals(height, robotModel.getY(), 1e-30);

        //  verify min location
        robotModel.setLocation(-w, -h);
        assertEquals(0, robotModel.getX(), 1e-30);
        assertEquals(0, robotModel.getY(), 1e-30);

    }

    @org.junit.Test
    public void reset()
    {
        System.out.println("test reset()");
    }
}