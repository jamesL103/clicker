package IO;

import input.macro.Macro;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileManager {

    public static final String MACRO_DIR = "./macros";

    private final MacroSaver SAVE = new MacroSaver();
    private final MacroLoader LOAD = new MacroLoader();

    //checks if the macros directory exists and creates it if not
    public FileManager() {
        Path macrosDir = Paths.get(MACRO_DIR);
        if (!Files.exists(macrosDir)) {
            try {
                Files.createDirectory(macrosDir);
            } catch (IOException e) {
                System.err.println("Error creating macro directory: " + e.getMessage());
            }
        }
    }


    //load macro specified by name
    public Macro loadMacro(String name) {
        return LOAD.loadFromFile(MACRO_DIR + "/" + name + ".mac");
    }

    //save specified macro
    public void saveMacro(Macro macro) {
        SAVE.saveToFile(macro, MACRO_DIR + "/" + macro.getName() + ".mac");
    }

    //return a list of all macro names
    public List<String> loadMacroNames() {
        List<String> list = new LinkedList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(MACRO_DIR))) {
            for (Path entry: stream) {
                String fileName = entry.getFileName().toString();
                fileName = fileName.substring(0, fileName.length() - 4);
                list.add(fileName);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    /** Deletes a macro with the specified name.
     *
     * @param name the name of the macro to delete
     */
    public void deleteMacro(String name) {
        Path path = Paths.get(MACRO_DIR + "/" + name + ".mac");
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.println("Error deleting macro:" + e.getMessage());
        }
    }

}
