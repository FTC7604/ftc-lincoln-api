package com.lincolnrobotics.api2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static com.lincolnrobotics.api2018.Constants.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PIDMotorVelocityControl.class})
public class MovementControlTest
{
    @Test
    public void testStandardMovementControl()
    {
        StandardRobotMovementControl srmc = new StandardRobotMovementControl(1080, 1440, 18);
        assertEquals(0.17683882565766149, srmc.movementSpeedToRobotPower(15), DOUBLE_THRESHOLD);
        assertEquals(0.2864788975654116, srmc.movementSpeedToRobotPower(24.3), DOUBLE_THRESHOLD);
        assertEquals(0.1921648572146588, srmc.movementSpeedToRobotPower(16.3), DOUBLE_THRESHOLD);
        assertEquals(27.143360527015815, srmc.robotPowerToMovementSpeed(0.32), DOUBLE_THRESHOLD);
        assertEquals(67.85840131753953, srmc.robotPowerToMovementSpeed(0.8), DOUBLE_THRESHOLD);
        assertEquals(54.28672105403163, srmc.robotPowerToMovementSpeed(0.64), DOUBLE_THRESHOLD);
        assertEquals(0.3, srmc.movementSpeedToRobotPower(srmc.robotPowerToMovementSpeed(0.3)), DOUBLE_THRESHOLD);
        assertEquals(24.4, srmc.robotPowerToMovementSpeed(srmc.movementSpeedToRobotPower(24.4)), DOUBLE_THRESHOLD);

        StandardRobotMovementControl srmc2 = new StandardRobotMovementControl(360, 360, 12);
        assertEquals(0.19894367886486916, srmc2.movementSpeedToRobotPower(15), DOUBLE_THRESHOLD);
        assertEquals(0.32228875976108806, srmc2.movementSpeedToRobotPower(24.3), DOUBLE_THRESHOLD);
        assertEquals(0.21618546436649116, srmc2.movementSpeedToRobotPower(16.3), DOUBLE_THRESHOLD);
        assertEquals(24.12743157956961, srmc2.robotPowerToMovementSpeed(0.32), DOUBLE_THRESHOLD);
        assertEquals(60.31857894892403, srmc2.robotPowerToMovementSpeed(0.8), DOUBLE_THRESHOLD);
        assertEquals(48.25486315913922, srmc2.robotPowerToMovementSpeed(0.64), DOUBLE_THRESHOLD);
        assertEquals(0.3, srmc2.movementSpeedToRobotPower(srmc2.robotPowerToMovementSpeed(0.3)), DOUBLE_THRESHOLD);
        assertEquals(24.4, srmc2.robotPowerToMovementSpeed(srmc2.movementSpeedToRobotPower(24.4)), DOUBLE_THRESHOLD);

    }
}