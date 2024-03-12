package clicker;
import java.awt.*;
import java.awt.event.InputEvent;

public class Clicker {

    //clicks per second, maximum before int precision is lost is 1000
    private int frequency = 10;

    //determines whether autoclicking is in progress
    private boolean clickActive = false;

    //robot object to provide clicking
    Robot rob;

    {
        try {
            rob = new Robot();
        } catch (AWTException e) {
            System.out.println("Error: unable to access input controls");
        }

    }

    /**Constructs a new Clicker with the specified frequency.
     * Frequency is measured in number of clicks per second, and must have a minumum
     * value of 1.The maximum value before delay goes to zero is 1000.
     *
     * @param frequency the frequency of the clicker
     */
    public Clicker(int frequency) {
        this.frequency = frequency;
    }

    public Clicker() {

    }

    /**Clicks continously at the Clickers rate until stopped
     *
     */
    public void autoClick() {
        int clickPause = (int)(1000 * (1.0/frequency)); //delay in milliseconds between each click
        while (clickActive) {
            rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            rob.delay(clickPause);
        }
    }

    /**Disables autoclicking.
     *
     */
    public void disableClick() {
        clickActive = false;
    }

    /**Returns whether the autoclicker is active
     *
     * @return if the autoclicker is active
     */
    public boolean isClicking() {
        return clickActive;
    }

    /**Sets the frequency of the autoclicker to a specified integer.
     *
     * Frequency is measured in number of clicks per second, and must have a minumum
     * value of 1.The maximum value before delay goes to zero is 1000.
     *
     * @param freq the frequency, in clicks/second
     */
    public void setFrequency(int freq) {
        if (freq < 1) {
            throw new IllegalArgumentException("Error: Invalid frequency of " + freq);
        }
        frequency = freq;
    }

    /**Returns the frequency of the autoclicker
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }


}
