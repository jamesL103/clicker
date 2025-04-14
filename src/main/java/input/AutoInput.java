package input;

import input.macro.Macro;

import java.awt.*;

public class AutoInput {

    //determines whether auto input is enabled
    //note: this causes a data race. This is bad but it should still work
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
            System.err.println(e.getMessage());
        }

    }


    /** Activates the currently active macro
     *
     */
    public void activate() {

        if (rob.getMacroType() == Macro.MacroType.SINGLE) {
            input = new Thread(() -> rob.runMacro());
        } else if (!inputActive) {
            inputActive = true;
            input = new Thread(() -> { //create a thread to handle the autoclicking
                while (inputActive) {
                    rob.runMacro();
                }
                rob.waitForIdle();
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


}
