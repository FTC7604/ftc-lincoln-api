package com.lincolnrobotics.api2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.lincolnrobotics.api2018.Constants.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PIDMotorPowerControl.class})
public class PowerControlTest
{
    @Test
    public void testBasicPowerControl()
    {
        BasicMotorPowerControl bmvc = new BasicMotorPowerControl();
        assertEquals(1.0, bmvc.calculateMotorPower(1, 1), DOUBLE_THRESHOLD);
        assertEquals(1.0, bmvc.calculateMotorPower(0.4, 1), DOUBLE_THRESHOLD);
        assertEquals(0.3, bmvc.calculateMotorPower(1, 0.3), DOUBLE_THRESHOLD);
        assertEquals(0.5, bmvc.calculateMotorPower(0.2, 0.5), DOUBLE_THRESHOLD);
        assertEquals(1.0, bmvc.calculateMotorPower(0.6, 1), DOUBLE_THRESHOLD);
    }

    @Test
    public void testPIDPowerControl()
    {
        // PIDMotorPowerControl uses System.currentTimeMillis().
        // If this changes¸ this test will likely fail. Update the test if that is the case.
        PowerMockito.mockStatic(System.class);

        PIDMotorPowerControl pmvc = new PIDMotorPowerControl(1, 1, 1, 1);

        setCurrentTimeMillis(0L);
        assertEquals(1.0, pmvc.calculateMotorPower(1, 1), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(100L);
        assertEquals(1.0, pmvc.calculateMotorPower(1, 1), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(200L);
        assertEquals(1.0, pmvc.calculateMotorPower(1, 1), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(300L);
        assertEquals(-49.005, pmvc.calculateMotorPower(1.5, 1), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(400L);
        assertEquals(-149.005, pmvc.calculateMotorPower(2, 1), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(500L);
        assertEquals(-148.99, pmvc.calculateMotorPower(1, 1), DOUBLE_THRESHOLD);

        PIDMotorPowerControl pmvc2 = new PIDMotorPowerControl(3, 0.2, 1.6, 0.84);
        setCurrentTimeMillis(0L);
        assertEquals(0.768, pmvc2.calculateMotorPower(0.87, 0.7), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(100L);
        assertEquals(-31.910229600000036, pmvc2.calculateMotorPower(0.768, 0.7), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(200L);
        assertEquals(6.431729599999954, pmvc2.calculateMotorPower(0.62, 0.7), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(300L);
        assertEquals(-377.24217600000014, pmvc2.calculateMotorPower(1.5, 0.7), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(400L);
        assertEquals(-1001.0326000000002, pmvc2.calculateMotorPower(2, 0.7), DOUBLE_THRESHOLD);
        setCurrentTimeMillis(500L);
        assertEquals(-1145.3948000000003, pmvc2.calculateMotorPower(1, 0.7), DOUBLE_THRESHOLD);

    }

    private void setCurrentTimeMillis(long currentTimeMillis)
    {
        PowerMockito.when(System.currentTimeMillis()).thenReturn(currentTimeMillis);
    }

    @Test
    public void testRampingPowerControl()
    {
        // Mock the intended behavior of BasicMotorPowerControl without depending on it
        // Provides an implementation of MotorPowerControl that always returns the target speed
        MotorPowerControl baseControl = mock(MotorPowerControl.class);
        when(baseControl.calculateMotorPower(anyDouble(), anyDouble())).thenAnswer(invocation -> invocation.getArguments()[1]);

        RampingMotorPowerControl rmvc = new RampingMotorPowerControl(baseControl, 0.5);
        assertEquals(1.0, rmvc.calculateMotorPower(1, 1), DOUBLE_THRESHOLD);
        assertEquals(0.9, rmvc.calculateMotorPower(0.4, 1), DOUBLE_THRESHOLD);
        assertEquals(0.5, rmvc.calculateMotorPower(1, 0.4), DOUBLE_THRESHOLD);
        assertEquals(0.0, rmvc.calculateMotorPower(-0.5, 0.5), DOUBLE_THRESHOLD);
        assertEquals(1.0, rmvc.calculateMotorPower(0.6, 1), DOUBLE_THRESHOLD);

        RampingMotorPowerControl rmvc2 = new RampingMotorPowerControl(baseControl, 0.2);
        assertEquals(1.0, rmvc2.calculateMotorPower(1, 1), DOUBLE_THRESHOLD);
        assertEquals(0.6, rmvc2.calculateMotorPower(0.4, 1), DOUBLE_THRESHOLD);
        assertEquals(0.8, rmvc2.calculateMotorPower(1, 0.4), DOUBLE_THRESHOLD);
        assertEquals(-0.3, rmvc2.calculateMotorPower(-0.5, 0.5), DOUBLE_THRESHOLD);
        assertEquals(0.8, rmvc2.calculateMotorPower(0.6, 1), DOUBLE_THRESHOLD);
    }
}