package input.macro;

import java.awt.event.InputEvent;
import java.util.LinkedList;
import java.util.List;

/** Class to store preset macros
 *
 */
public class Macros {


    //autoclicking macro
    public static final Macro AUTO_CLICK = new Macro(Macro.MacroType.TOGGLE, "Auto Clicker Default");

    static {
        //initialize autoclicker macro
        List<MacroEvent> clicker = new LinkedList<>();
        clicker.add(new MacroEvent(MacroEvent.InputType.MOUSE_PRESS, InputEvent.BUTTON1_DOWN_MASK));
        clicker.add(new MacroEvent(MacroEvent.InputType.MOUSE_RELEASE, InputEvent.BUTTON1_DOWN_MASK));
        clicker.add(new MacroEvent(MacroEvent.InputType.DELAY, -1, 1));
        AUTO_CLICK.setInputSequence(clicker);
    }

}
