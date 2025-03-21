package clicker.macro;

import java.util.List;

/** A Macro represents a series of input commands that will be executed successively.
 * Individual inputs will be represented with the Swing InputEvent constants.
 *
 */
public class Macro {

    private List<MacroEvent> inputList;

    private MacroType type = MacroType.SINGLE;

    public Macro(MacroType type) {
        this.type = type;
    }

    public MacroType getType() {
        return type;
    }

    public List<MacroEvent> getInputSequence() {
        return inputList;
    }

    public void setInputSequence(List<MacroEvent> sequence) {
        inputList = sequence;
    }

    /** Types determine how the macro is activated and how many times it will run.
     *  Single types will only run once per activation.
     *  Toggle types will continuously run until toggled off.
     */
    public enum MacroType {
        SINGLE, TOGGLE
    }


}
