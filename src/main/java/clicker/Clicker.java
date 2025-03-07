package clicker;
import java.awt.*;
import java.awt.event.InputEvent;

public class Clicker {

    //clicks per second, anything above approx. 1000 results in max clicking rate
    private int frequency = 10;

    //determines whether autoclicking is in progress
    private boolean clickActive = false;

    //thread to provide clicking
    private Thread clicker;

    //robot object to provide clicking
    private Robot rob;

    {
        try {
            rob = new Robot();
        } catch (AWTException e) {
            System.out.println("Error: unable to access input controls");
        }

    }


    public Clicker() {

    }

    /**Clicks continuously at the Clickers rate until stopped
     *
     */
    public void autoClick() {
        if (!clickActive) {
            int clickPause = (int) (1000 * (1.0 / frequency)); //delay in milliseconds between each click
            clickActive = true;
            clicker = new Thread(() -> { //create a thread to handle the autoclicking
                while (clickActive) {
                    rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    rob.delay(clickPause);
                }
            });
            clicker.start();//start the thread
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
