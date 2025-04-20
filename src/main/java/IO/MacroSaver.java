package IO;

import input.macro.Macro;
import input.macro.MacroEvent;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.nio.ByteBuffer;

public class MacroSaver {

    public void saveToFile(Macro macro, String path) {
        File file = new File(path);
        FileWriter out;

        try {
            //create file if it does not exist
            file.createNewFile();

            //write name
            out = new FileWriter(file);
            out.write(macro.getName() + ",");

            //write type byte
            Macro.MacroType type = macro.getType();
            if (type == Macro.MacroType.SINGLE) {
                out.append((char)0);
            } else {
                out.append((char)0b1);
            }
            out.append(",");

            //write isPreset byte
            if (macro.isPreset()) {
                out.append((char)0b1);
            } else {
                out.append((char)0);
            }
            out.append(",");

            out.close();

            try { //for writing the sequence of inputs
                FileOutputStream stream = new FileOutputStream(file, true);
                for (MacroEvent event: macro.getInputSequence()) {
                    if (event.type == MacroEvent.InputType.MOUSE_PRESS) {
                        stream.write((byte) 0b1);
                        writeMouseButtonCode(stream, event.input_code);
                    } else if (event.type == MacroEvent.InputType.MOUSE_RELEASE) {
                        stream.write((byte) (0b1 << 1));
                        writeMouseButtonCode(stream, event.input_code);
                    } else if (event.type == MacroEvent.InputType.KEY_PRESS) {
                        stream.write((byte) (0b1<<2));
                        stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(event.input_code).array());
                    } else if (event.type == MacroEvent.InputType.KEY_RELEASE) {
                        stream.write((byte) (0b1<<3));
                        stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(event.input_code).array());
                    } else { //write delay
                        stream.write((byte) 0b1 <<4);
                        stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(event.delay).array());
                    }
                }
                stream.close();

            } catch (FileNotFoundException e) {
                System.err.println("Error: Couldn't find file \"" + file.getPath() + "\"");
            }

        } catch (IOException e) {
           System.err.println("Saving Error: can't save to file \"" + path + "\"");
           System.err.println(e.getMessage());
        }

    }

    //helper to write the appropriate byte representation of the mouse button code for the MacroEvent
    //Button1 = 1
    //button2 = 2
    private void writeMouseButtonCode(FileOutputStream stream, int code) throws IOException{
        if (code == InputEvent.BUTTON1_MASK) {
            stream.write((byte) 1);
        } else {
            stream.write((byte) 2);
        }
    }


}
