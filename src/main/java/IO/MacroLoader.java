package IO;

import input.macro.Macro;
import input.macro.MacroEvent;
import input.macro.Macros;

import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Class to load macros from files
 *
 */
public class MacroLoader {


    /** Creates a macro based on the specified file, assuming there is one macro
     * in the file.
     *
     * @param path the path to the macro file
     * @return the macro from the file
     */
    public Macro loadFromFile(String path) {
        File file = new File(path);
        Scanner in;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Couldn't find file \"" + path + "\"");
            return Macros.AUTO_CLICK;
        }
        in.useDelimiter(",");

        Macro macro = new Macro(Macro.MacroType.SINGLE, "default");

        macro.setName(in.next().trim());

        byte type = in.nextByte();
        if (type == 0b1) {
            macro.setType(Macro.MacroType.TOGGLE);
        } else {
            if (type != 0) {
                System.err.println("Error loading from \"" + path + "\": \"" +
                        type + "\" is not a valid type.");
            }
            macro.setType(Macro.MacroType.SINGLE);
        }

        String inputs = in.nextLine();

        macro.setInputSequence(readInputSequence(inputs));

        return macro;
    }

    private static List<MacroEvent> readInputSequence(String sequence) {
        List<MacroEvent> inputList = new ArrayList<>();

        Scanner input = new Scanner(sequence);

        while (input.hasNext()) {
            byte flag = input.nextByte();
            MacroEvent event = null;

            //mouse down flag
            if ((flag & 0b1) == 0b1) {
                byte mouseCode = input.nextByte();
                if (mouseCode == 1) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_PRESS, InputEvent.BUTTON1_DOWN_MASK);
                } else if (mouseCode == 2) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_PRESS, InputEvent.BUTTON2_DOWN_MASK);
                }
            } else if ((flag & 0b1 << 1) == 0b1<<1) { //mouse release flag
                byte mouseCode = input.nextByte();
                if (mouseCode == 1) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_RELEASE, InputEvent.BUTTON1_DOWN_MASK);
                } else if (mouseCode == 2) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_RELEASE, InputEvent.BUTTON2_DOWN_MASK);
                }
            } else if ((flag & 0b1 << 2) == 0b1 << 2) { //key press flag
                int keyCode = input.nextInt(); //corresponds to int value of KeyEvent VK constant
                //fuck it this is a little hacky, but it will successfully store the key code
                event = new MacroEvent(MacroEvent.InputType.KEY_PRESS, keyCode);
            } else if ((flag & 0b1 <<3) == 0b1 << 3) { //key release flag
                int keyCode = input.nextInt(); //corresponds to int value of KeyEvent VK constant
                event = new MacroEvent(MacroEvent.InputType.KEY_RELEASE, keyCode);
            } else if ((flag & 0b1 << 3) == 0b1 <<3) {
                int delay = input.nextInt();
                event = new MacroEvent(delay);
            }

            inputList.add(event);
        }

        return inputList;
    }


}
