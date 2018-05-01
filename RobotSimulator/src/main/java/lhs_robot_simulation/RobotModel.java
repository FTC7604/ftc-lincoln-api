package lhs_robot_simulation;

/**
 * Robot simulation model used for maintaining state and
 * rendering on the simulated playing field.
 * <p>
 * CopyRight 2018 bsteele.com
 * User: bob
 */
class RobotModel
{
    RobotModel(double width, double height)
    {
        maxWidth = width;
        maxHeight = height;
        reset();
    }

    void setLocation(double x, double y)
    {
        this.x = Math.max(0, Math.min(x, getMaxWidth()));
        this.y = Math.max(0, Math.min(y, getMaxHeight()));
    }

    void setRot(double rot)
    {
        this.rot = rot;
    }

    double getX()
    {
        return x;
    }

    double getY()
    {
        return y;
    }

    double getRot()
    {
        return rot;
    }

    double getMaxWidth()
    {
        return maxWidth;
    }

    double getMaxHeight()
    {
        return maxHeight;
    }

    void reset()
    {
        setLocation(maxWidth / 2, maxHeight / 2);
        rot = -Math.PI / 4;
    }

    private double x = 100;
    private double y = 100;
    private double rot = 0; //  rotation in radians
    private final double maxWidth;
    private final double maxHeight;
}
