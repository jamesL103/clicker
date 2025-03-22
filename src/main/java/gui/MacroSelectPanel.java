package gui;

import input.macro.Macro;
import input.macro.Macros;

import javax.swing.*;
import java.awt.*;

//provides controls to change the currently active macro
public class MacroSelectPanel extends JPanel {

    private final JLabel TITLE = new JLabel();

    private final JList<Macro> MACRO_LIST = new JList<>();

    public MacroSelectPanel() {

        setLayout(new GridBagLayout());

        addTitle();

        initList();

    }

    private void addTitle() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        TITLE.setText("Available Macros");
        TITLE.setFont(Fonts.TITLE);

        add(TITLE, gbc);
    }

    //initializes JList and its model
    private void initList() {
        DefaultListModel<Macro> model = new DefaultListModel<>();

        addPresetMacros(model);

        MACRO_LIST.setModel(model);

        GridBagConstraints listConstraints = new GridBagConstraints();

        listConstraints.gridx = 0;
        listConstraints.gridy = 1;
        listConstraints.weightx = 1.0;
        listConstraints.weighty = 0.6;
        listConstraints.fill = GridBagConstraints.BOTH;

        add(MACRO_LIST, listConstraints);
    }

    //add all preset Macros to the list
    private void addPresetMacros(DefaultListModel<Macro> model) {
        model.addElement(Macros.AUTO_CLICK);
    }

    @Override
    public void paint(Graphics g) {
        TITLE.repaint();
        MACRO_LIST.repaint();
        super.paint(g);
    }

}
