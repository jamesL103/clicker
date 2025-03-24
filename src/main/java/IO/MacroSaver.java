package IO;

import input.macro.Macro;
import input.macro.MacroEvent;

import java.io.*;
import java.util.Scanner;

public class MacroSaver {

    public void saveToFile(Macro macro, String path) {
        File file = new File(path);
        FileWriter out;

        try {
            out = new FileWriter(file);
            out.write(macro.getName() + ",");
            //write type byte
            Macro.MacroType type = macro.getType();
            if (type == Macro.MacroType.SINGLE) {
                out.write(0);
            } else {
                out.write(0b1);
            }

            out.write(",");

            try { //for writing the sequence of inputs
                FileOutputStream stream = new FileOutputStream(file);
                for (MacroEvent event: macro.getInputSequence()) {
                    if (event.type == MacroEvent.InputType.MOUSE_PRESS) {
                        stream.write((byte) 0b1);
                    }
                }

            } catch (FileNotFoundException e) {

            }

        } catch (IOException e) {
           System.err.println("Saving Error: can't save to file \"" + path + "\"");
           return;
        }




    }


}
