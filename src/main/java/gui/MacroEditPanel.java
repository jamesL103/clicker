package gui;

import input.macro.Macro;

import javax.swing.*;
import java.awt.*;

public class MacroEditPanel extends JPanel {

    public final JLabel NAME_LABEL = new JLabel("default");

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
        add(new JButton("######3"));

    }

    public void setMacro(Macro macro) {
        this.macro = macro;
        updateView();
    }

    //updates the GUI to display the current macro
    private void updateView() {
        NAME_LABEL.setText(macro.getName());
        repaint();
    }

    @Override
    public void paint (Graphics g) {
        NAME_LABEL.repaint();
        super.paint(g);
    }


}
