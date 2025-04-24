package IO;

import input.macro.Macro;
import input.macro.MacroEvent;
import org.junit.jupiter.api.Test;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @org.junit.jupiter.api.Test
    void loadMacro() {
        FileManager manager = new FileManager();

        Macro macro = manager.loadMacro("Auto Clicker");

        System.out.println("Macro: " + macro);

        System.out.println("Type: " + macro.getType());

        for (MacroEvent e: macro.getInputSequence()) {
            if (e.type == MacroEvent.InputType.MOUSE_PRESS) {
                System.out.print("mouse press: ");
            } else if (e.type == MacroEvent.InputType.MOUSE_RELEASE) {
                System.out.print("mouse release: ");
            } else {
                System.out.print("Unread: ");
            }
            System.out.println(e.input_code);
        }
    }

    @org.junit.jupiter.api.Test
    void saveMacro() {
        FileManager manager = new FileManager();

        Macro macro = new Macro(Macro.MacroType.TOGGLE, "Auto Clicker", false);
        ArrayList<MacroEvent> sequence = new ArrayList<>();
        sequence.add(new MacroEvent(MacroEvent.InputType.MOUSE_PRESS, InputEvent.BUTTON1_DOWN_MASK));
        sequence.add(new MacroEvent(MacroEvent.InputType.MOUSE_RELEASE, InputEvent.BUTTON1_DOWN_MASK));
        sequence.add(new MacroEvent(MacroEvent.InputType.DELAY, -1, 1));
        macro.setInputSequence(sequence);

        manager.saveMacro(macro);

    }

    @Test
    public void loadKeyMacro() {
        FileManager manager = new FileManager();
        Macro save = new Macro(Macro.MacroType.SINGLE, "keys", true);

        List<MacroEvent> list = new ArrayList<>();

        list.add(new MacroEvent(MacroEvent.InputType.KEY_PRESS, KeyEvent.VK_A));
        list.add(new MacroEvent(MacroEvent.InputType.KEY_PRESS, KeyEvent.VK_B));
        list.add(new MacroEvent(MacroEvent.InputType.KEY_PRESS, KeyEvent.VK_ESCAPE));

        save.setInputSequence(list);

        manager.saveMacro(save);

        Macro load = manager.loadMacro("keys");

        assertEquals(save.getName(), load.getName());
        assertEquals(save.getType(), load.getType());

        System.out.println("Macro: " + load);

        System.out.println("Type: " + load.getType());

        for (MacroEvent e: load.getInputSequence()) {
            if (e.type == MacroEvent.InputType.KEY_PRESS) {
                System.out.print("key press: ");
            } else {
                System.out.print("Unread: ");
            }
            System.out.println(e.input_code);
        }

        assertEquals(save.getInputSequence(), load.getInputSequence());

    }

    @Test
    public void createEditMacro() {
        FileManager manager = new FileManager();
        Macro save = new Macro(Macro.MacroType.SINGLE, "edit", false);

        List<MacroEvent> list = new ArrayList<>();

        list.add(new MacroEvent(MacroEvent.InputType.KEY_PRESS, KeyEvent.VK_A));
        list.add(new MacroEvent(MacroEvent.InputType.KEY_PRESS, KeyEvent.VK_B));
        list.add(new MacroEvent(MacroEvent.InputType.KEY_PRESS, KeyEvent.VK_ESCAPE));

        save.setInputSequence(list);

        manager.saveMacro(save);
    }
}