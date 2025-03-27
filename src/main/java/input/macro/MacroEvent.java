package input.macro;

/** Class representing an input event that can either be keyboard or mouse input.
 *
 */
public class MacroEvent {

    //types that the MacroEvent can be
    public enum InputType {
        MOUSE_PRESS, MOUSE_RELEASE, KEY_PRESS, KEY_RELEASE, DELAY
    }

    public InputType type;

    //can be any kind for type delay
    public int input_code;

    //delay in milliseconds
    //only used if type = DELAY
    public int delay = 0;

    public MacroEvent(InputType type, int code) {
        this.type = type;
        input_code = code;
    }

    public MacroEvent(InputType type, int code, int delay) {
        this(type, code);
        this.delay = delay;
    }

    /** Constructor for making DELAY events
     *
     * @param delayDuration the duration of the delay
     */
    public MacroEvent(int delayDuration) {
        this.type = InputType.DELAY;
        this.delay = delayDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MacroEvent)) {
            return false;
        }
        MacroEvent other = (MacroEvent) obj;
        return (type == other.type) && (input_code == other.input_code) && (delay == other.delay);
    }

    @Override
    public int hashCode() {
        if (type == InputType.MOUSE_PRESS) {
            return input_code * 123 + delay * 89;
        } else if (type == InputType.MOUSE_RELEASE) {
            return input_code * (int)Math.pow(123, 2) + delay * 89;
        } else if (type == InputType.KEY_PRESS) {
            return input_code * (int)Math.pow(123, 3) + delay * 89;
        } else if (type == InputType.KEY_RELEASE) {
            return input_code * (int)Math.pow(123, 4) + delay * 89;
        } else {
            return input_code * (int)Math.pow(123, 5) + delay * 89;
        }
    }

}
