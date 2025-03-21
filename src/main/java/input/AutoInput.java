package input;

import input.macro.Macro;

import java.awt.*;

public class AutoInput {

    //attempted inputs per second, anything above approx. 1000 results in max rate
    private int frequency = 10;

    //determines whether auto input is enabled
    private boolean inputActive = false;

    //thread to generate input
    private Thread input;

    //robot object to provide input
    private MacroRobot rob;


    {
        try {
            rob = new MacroRobot();
        } catch (AWTException e) {
            System.err.println("Error: unable to access input controls");
        }

    }


    /** Activates the currently active macro
     *
     */
    public void activateMacro() {

        if (rob.getMacroType() == Macro.MacroType.SINGLE) {
            input = new Thread(() -> rob.runMacro());
        } else if (!inputActive) {
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

    /** Updates the current Macro that is run
     *
     * @param macro the macro to run
     */
    public void setMacro(Macro macro) {
        rob.setMacro(macro);
    }

    /**Sets the frequency of the auto input to a specified integer.
     * Frequency is the number of times
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

}
