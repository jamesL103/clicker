package gui.macroEdit;

import input.macro.Macro;

import javax.swing.*;
import java.awt.*;

public class MacroEditPanel extends JPanel {

    public final JLabel NAME_LABEL = new JLabel("default");

    private final JButton SAVE_BUTTON = new JButton("Save changes");

    private final JButton CANCEL_BUTTON = new JButton("Cancel");

    //currently edited macro
    private Macro macro;

    public MacroEditPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints titleConst = new GridBagConstraints();

        titleConst.gridx = 0;
        titleConst.gridy = 0;
        titleConst.fill = GridBagConstraints.HORIZONTAL;
        titleConst.weightx = 1.0;
        titleConst.weighty = 0.1;

        add(NAME_LABEL, titleConst);

        addButtons();

    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(SAVE_BUTTON);
        buttonPanel.add(CANCEL_BUTTON);

        add(buttonPanel, gbc);
    }

    public void setMacro(Macro macro) {
        this.macro = macro;
        updateView();
    }

    //updates the GUI to display the current macro
    private void updateView() {
        NAME_LABEL.setText(macro.getName());
    }


}
