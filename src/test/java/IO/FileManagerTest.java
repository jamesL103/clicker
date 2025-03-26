package IO;

import input.macro.Macro;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @org.junit.jupiter.api.Test
    void loadMacro() {

    }

    @org.junit.jupiter.api.Test
    void saveMacro() {
        FileManager manager = new FileManager();

        Macro macro = new Macro(Macro.MacroType.SINGLE, "one");

        macro.setInputSequence(new ArrayList<>());

        manager.saveMacro(macro);

    }
}