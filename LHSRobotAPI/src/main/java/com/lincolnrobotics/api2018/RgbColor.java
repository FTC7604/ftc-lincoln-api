package com.lincolnrobotics.api2018;

/**
 * A simple color model to allow the autonomous control software
 * to use colors for detection but still remain independent of the
 * user interface (UI) it's currently running under.
 *
 * CopyRight 2018 bsteele.com
 * User: bob

 */
public class RgbColor {
    public RgbColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }


    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    private int red;
    private int green;
    private int blue;
}
