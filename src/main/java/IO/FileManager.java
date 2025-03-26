package IO;

import input.macro.Macro;

public class FileManager {

    public static final String MACRO_DIR = ".";

    private final MacroSaver SAVE = new MacroSaver();
    private final MacroLoader LOAD = new MacroLoader();


    //load macro specified by name
    public Macro loadMacro(String name) {
        return LOAD.loadFromFile(MACRO_DIR + "/" + name + ".mac");
    }

    //save specified macro
    public void saveMacro(Macro macro) {
        SAVE.saveToFile(macro, MACRO_DIR + "/" + macro.getName() + ".mac");
    }

}
