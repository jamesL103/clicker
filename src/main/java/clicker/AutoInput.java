package clicker;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoInput {

    //attempted inputs per second, anything above approx. 1000 results in max rate
    private int frequency = 10;

    //determines whether auto input is enabled
    private boolean inputActive = false;

    //thread to provide clicking
    private Thread input;

    //robot object to provide clicking
    private MacroRobot rob;

    {
        try {
            rob = new MacroRobot();
        } catch (AWTException e) {
            System.err.println("Error: unable to access input controls");
        }

    }


    /** Inputs continuously at the set rate until stopped
     *
     */
    public void autoInput() {
        if (!inputActive) {
            int pause = (int) (1000 * (1.0 / frequency)); //delay in milliseconds between each click
            inputActive = true;
            input = new Thread(() -> { //create a thread to handle the autoclicking
                while (inputActive) {
                    rob.runMacro();
                }
            });
            input.start();//start the thread
        }
    }

    /**Disables auto input.
     *
     */
    public void disableInput() {
        inputActive = false;
    }

    /**Returns whether the auto input is active
     *
     * @return if the auto input is active
     */
    public boolean isActive() {
        return inputActive;
    }

    /**Sets the frequency of the auto input to a specified integer.
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

    /**Returns the frequency of the auto input
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }



}
