package input.macro;

import java.util.List;

/** A Macro represents a series of input commands that will be executed successively.
 * Individual inputs will be represented with the Swing InputEvent constants.
 *
 */
public class Macro {

    private List<MacroEvent> inputList;

    private MacroType type;

    private String name;

    private boolean PRESET;

    public Macro(MacroType type, String name, boolean preset) {
        this.type = type;
        this.name = name;
        this.PRESET = preset;
    }

    public void setType(MacroType type) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isPreset() {
        return PRESET;
    }

    public void setPreset(boolean isPreset) {
        PRESET = isPreset;
    }

    /** Types determine how the macro is activated and how many times it will run.
     *  Single types will only run once per activation.
     *  Toggle types will continuously run until toggled off.
     */
    public enum MacroType {
        SINGLE("single"), TOGGLE("toggle");

        private final String NAME;

        MacroType(String display) {
            NAME = display;
        }

        @Override
        public String toString() {
            return NAME;
        }
    }


}
