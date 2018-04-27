package autocode;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import lhsrobotapi.autocode.AutoCode;
import lhsrobotapi.robot.RobotDrivetrain;
import lhsrobotapi.robot.IdealRobotDrivetrain;

public class SampleAutoCode extends AutoCode
{
    @Component(modules = SampleModule.class) interface SampleComponent
    {
        RobotDrivetrain getDrivetrain();
    }

    @Module
    static class SampleModule
    {
        @Provides RobotDrivetrain requestDrivetrain()
        {
            return new IdealRobotDrivetrain();
        }
    }

    public static void main(String[] args)
    {
        SampleComponent sc = autocode.DaggerSampleAutoCode_SampleComponent.builder().sampleModule(new SampleModule()).build();
        SampleAutoCode autocode = new SampleAutoCode();
        autocode.drivetrain = sc.getDrivetrain();
        autocode.run();
    }

    public void run()
    {
        drivetrain.driveForward(100);
        System.out.println(drivetrain.getYTranslation());
    }
}
