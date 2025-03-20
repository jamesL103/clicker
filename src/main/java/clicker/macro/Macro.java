package clicker.macro;

import java.util.List;

/** A Macro represents a series of input commands that will be executed successively.
 * Individual inputs will be represented with the Swing InputEvent constants.
 *
 */
public class Macro {

    private List<MacroEvent> inputList;

    public List<MacroEvent> getInputSequence() {
        return inputList;
    }

    public void setInputSequence(List<MacroEvent> sequence) {
        inputList = sequence;
    }



}
