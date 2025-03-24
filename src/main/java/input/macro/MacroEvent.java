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
    //will be null if type is not DELAY
    public int delay;

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

}
