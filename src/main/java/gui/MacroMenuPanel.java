   package gui;

import IO.FileManager;
import input.macro.Macro;
import input.macro.Macros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//provides controls to change the currently active macro
public class MacroMenuPanel extends JPanel {

    private final JLabel TITLE = new JLabel();

    private final JList<String> MACRO_LIST = new JList<>();

    private final FileManager MANAGER;

    private Macro currentMacro;

    private final MacroGUI.ExitViewObserver exitObserver;
    //observer for selecting a new macro
    private final MacroGUI.ChangeMacroObserver changeObserver;

    //instance of exit button listener
    private final ExitButtonListener EXIT_LISTENER = new ExitButtonListener();

    public MacroMenuPanel(FileManager manager, MacroGUI.ExitViewObserver exitObserver, MacroGUI.ChangeMacroObserver changeObserver) {

        MANAGER = manager;
        this.exitObserver = exitObserver;
        this.changeObserver = changeObserver;

        setLayout(new GridBagLayout());

        addTitle();
        addExitButton();
        addSelectButtons();

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

    private void addExitButton() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;

        JButton exit = new JButton("X");
        exit.addActionListener(EXIT_LISTENER);

        add(exit, gbc);
    }

    private void addSelectButtons() {
        JPanel buttons = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(EXIT_LISTENER);

        JButton select = new JButton("Select");
        select.addActionListener(new SelectMacroButtonListener());

        buttons.add(select);
        buttons.add(cancel);

        add(buttons, gbc);
    }

    //initializes JList and its model
    private void initList() {
        DefaultListModel<String> model = new DefaultListModel<>();

        addPresetMacros(model);

        MACRO_LIST.setModel(model);

        List<String> macroNames = MANAGER.loadMacroNames();
        for (String name: macroNames) {
            model.addElement(name);
        }

        GridBagConstraints listConstraints = new GridBagConstraints();

        listConstraints.gridx = 0;
        listConstraints.gridy = 1;
        listConstraints.weightx = 1.0;
        listConstraints.weighty = 0.6;
        listConstraints.fill = GridBagConstraints.BOTH;

        add(MACRO_LIST, listConstraints);
    }

    //sets the list of the menu
    public void setList(List<String> list) {
        DefaultListModel<String> updated = new DefaultListModel<>();

        for (String name: list) {
            updated.addElement(name);
        }

        MACRO_LIST.setModel(updated);
    }

    public void setMacro(Macro macro) {
        currentMacro = macro;
    }

    //add all preset Macros to the list
    private static void addPresetMacros(DefaultListModel<String> model) {
        model.addElement(Macros.AUTO_CLICK.getName());
    }

    //listener for when macro select button is pressed
    private class SelectMacroButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (MACRO_LIST.getSelectedValue().equals(currentMacro.getName())) {
                return;
            } else if (MACRO_LIST.getSelectedValue().equals(Macros.AUTO_CLICK.getName())) {
                currentMacro = Macros.AUTO_CLICK;
            } else {
                currentMacro = MANAGER.loadMacro(MACRO_LIST.getSelectedValue());
            }
            changeObserver.changeMacroTo(currentMacro);
            exitObserver.notifyExit();
        }
    }

    //listener for exit button
    private class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            exitObserver.notifyExit();
        }
    }


}
