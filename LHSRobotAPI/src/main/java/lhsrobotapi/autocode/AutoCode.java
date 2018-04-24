package lhsrobotapi.autocode;

import java.util.HashSet;
import java.util.Set;

import lhsrobotapi.robot.RobotDrivetrain;

public abstract class AutoCode
{
    private static final Set<Class<? extends AutoCode>> classList = new HashSet<>();

    public RobotDrivetrain drivetrain;
    public abstract void run();
}
