import gui.ClickerGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClickerGUI gui = new ClickerGUI();
        });
    }

}
