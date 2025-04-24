package gui.macroEdit;

import gui.MacroGUI;
import input.macro.Macro;
import input.macro.MacroEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MacroEditPanel extends JPanel {

    public final JLabel NAME_LABEL = new JLabel("default");

    private final JButton SAVE_BUTTON = new JButton("Save changes");

    private final JButton CANCEL_BUTTON = new JButton("Cancel");

    private final MacroGUI.ExitViewObserver EXIT_OBSERVER;

    //field for editing macro name
    private final JTextField NAME_EDIT = new JTextField();

    //dropdown menu for editing macro type
    private final JComboBox<String> TYPE_SELECT = new JComboBox<>();
    private final static String[] TYPES = {"single", "toggle"};

    //tracks the pre-edit name of the macro
    private String originalName = "";

    //temporary macro sequence used when editing the macro
    private List<MacroEvent> tempList = new ArrayList<>();

    //single instance of Exit Button Listener
    private final ExitButtonListener EXIT_LISTENER = new ExitButtonListener();

    //currently edited macro
    private Macro currentMacro;

    public MacroEditPanel(MacroGUI.ExitViewObserver exitObserver) {
        super();
        EXIT_OBSERVER = exitObserver;

        setLayout(new GridBagLayout());

        addHeader();
        addButtons();
        addTypeSelect();

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
        SAVE_BUTTON.addActionListener(new SaveButtonListener());
        buttonPanel.add(CANCEL_BUTTON);
        CANCEL_BUTTON.addActionListener(EXIT_LISTENER);

        add(buttonPanel, gbc);
    }

    private void addHeader() {
        JPanel header = new JPanel();
        header.setLayout(new GridBagLayout());

        GridBagConstraints headerConstraints = new GridBagConstraints();

        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.weightx = 1.0;
        headerConstraints.weighty = 0.3;
        headerConstraints.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Currently Editing: ");
        header.add(title, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.NONE;
        JButton exit = new JButton("x");
        exit.addActionListener(EXIT_LISTENER);
        header.add(exit, gbc);


        NAME_EDIT.setText("default");
        NAME_EDIT.setToolTipText("Name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        header.add(NAME_EDIT, gbc);

        add(header, headerConstraints);

    }

    private void addTypeSelect() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(TYPES);
        TYPE_SELECT.setModel(model);

        add(TYPE_SELECT, gbc);

    }

    //resets the temp list to the macro's sequence
    public void resetTempList() {
        tempList.clear();
        tempList.addAll(currentMacro.getInputSequence());
    }

    public void setMacro(Macro macro) {
        this.currentMacro = macro;
        updateView();
    }

    //updates the GUI to display the current macro
    private void updateView() {
        NAME_LABEL.setText(currentMacro.getName());
        NAME_EDIT.setText(currentMacro.getName());
        if (currentMacro.getType() == Macro.MacroType.SINGLE) {
            TYPE_SELECT.setSelectedIndex(0);
        } else {
            TYPE_SELECT.setSelectedIndex(1);
        }
    }


    //action listener for cancel and close buttons
    private class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            resetTempList();
            EXIT_OBSERVER.notifyExit();
        }
    }

    //listener for save button
    private class SaveButtonListener implements ActionListener {

        //saves the new data to the macro
        @Override
        public void actionPerformed(ActionEvent e) {
            currentMacro.setInputSequence(tempList);
            currentMacro.setName(NAME_EDIT.getText());
            if (TYPE_SELECT.getSelectedIndex() == 0) {
                currentMacro.setType(Macro.MacroType.SINGLE);
            } else {
                currentMacro.setType(Macro.MacroType.TOGGLE);
            }
            EXIT_OBSERVER.notifyExit();
        }
    }

}
