package input;

import input.macro.Macro;
import input.macro.MacroEvent;

import java.awt.*;

/** Class that will run a macro that is specified when instantiated.
 *
 */
public class MacroRobot extends Robot {

    private Macro macro;


    public MacroRobot() throws AWTException{
        super();
    }

    /** Sets the MacroRobot's macro
     *
     * @param macro the macro to run
     */
    public void setMacro(Macro macro) {
        this.macro = macro;
    }

    public Macro.MacroType getMacroType() {
        return macro.getType();
    }

    /** Runs the macro once
     *
     */
    public void runMacro() {
        for (MacroEvent input: macro.getInputSequence()) {
            switch (input.type) {
                case MOUSE_PRESS:
                    mousePress(input.input_code);
                    break;
                case MOUSE_RELEASE:
                    mouseRelease(input.input_code);
                    break;
                case KEY_PRESS:
                    keyPress(input.input_code);
                    break;
                case KEY_RELEASE:
                    keyRelease(input.input_code);
                    break;
                case DELAY:
                    delay(input.delay);
                    break;
            }
        }
    }

}
