package IO;

import input.macro.Macro;
import input.macro.MacroEvent;
import input.macro.Macros;

import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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
        FileInputStream in;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Couldn't find file \"" + path + "\"");
            return Macros.AUTO_CLICK;
        }

        Macro macro = new Macro(Macro.MacroType.SINGLE, "default");
        try {
            byte curr = (byte) in.read();
            String name = "";
            while (curr != ',') {
                name = name.concat(String.valueOf((char)curr));
                curr = (byte) in.read();
            }

            macro.setName(name.trim());

            byte type = (byte) in.read();
            if (type == 0b1) {
                macro.setType(Macro.MacroType.TOGGLE);
            } else {
                if (type != 0) {
                    System.err.println("Error loading from \"" + path + "\": \"" +
                            type + "\" is not a valid type.");
                }
                macro.setType(Macro.MacroType.SINGLE);
            }
            in.skip(1);//skip comma

            macro.setInputSequence(readInputSequence(in));
        } catch (IOException e) {
            System.err.println("Error: Couldn't read file \"" + path + "\"");
            System.err.println(e.getMessage());
        }

        return macro;
    }

    //read the sequence of MacroEvents and return them as a list
    //scanner position must be at the start of the input sequence in the file

    /*
    Input sequence file format:
    byte 1: InputType flag
    Type mouse press or mouse release: 2nd byte determines mouse button
    Type key press or key release: int follows representing KeyEvent key code
    Type delay: int follows representing delay time in ms
     */
    private static List<MacroEvent> readInputSequence(FileInputStream input) throws IOException {
        List<MacroEvent> inputList = new ArrayList<>();

        while (input.available() > 0) {
            byte flag = (byte) input.read();
            MacroEvent event = null;

            //mouse down flag
            if ((flag & 0b1) == 0b1) {
                int mouseCode = input.read();
                if (mouseCode == 1) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_PRESS, InputEvent.BUTTON1_DOWN_MASK);
                } else if (mouseCode == 2) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_PRESS, InputEvent.BUTTON2_DOWN_MASK);
                }
            } else if ((flag & 0b1 << 1) == 0b1<<1) { //mouse release flag
                int mouseCode = input.read();
                if (mouseCode == 1) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_RELEASE, InputEvent.BUTTON1_DOWN_MASK);
                } else if (mouseCode == 2) {
                    event = new MacroEvent(MacroEvent.InputType.MOUSE_RELEASE, InputEvent.BUTTON2_DOWN_MASK);
                }
            } else if ((flag & 0b1 << 2) == 0b1 << 2) { //key press flag
                byte[] code = new byte[Integer.BYTES];
                input.read(code);
                int keyCode = ByteBuffer.wrap(code).getInt(); //corresponds to int value of KeyEvent VK constant
                //fuck it this is a little hacky, but it will successfully store the key code
                event = new MacroEvent(MacroEvent.InputType.KEY_PRESS, keyCode);
            } else if ((flag & 0b1 <<3) == 0b1 << 3) { //key release flag
                byte[] code = new byte[Integer.BYTES];
                input.read(code);
                int keyCode = ByteBuffer.wrap(code).getInt(); //corresponds to int value of KeyEvent VK constant
                event = new MacroEvent(MacroEvent.InputType.KEY_RELEASE, keyCode);
            } else if ((flag & 0b1 << 4) == 0b1 <<4) {
                byte[] code = new byte[Integer.BYTES];
                input.read(code);
                int delay = ByteBuffer.wrap(code).getInt();
                event = new MacroEvent(delay);
            }

            inputList.add(event);
        }

        return inputList;
    }


}
