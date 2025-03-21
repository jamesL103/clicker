package gui;

import input.macro.Macro;
import input.macro.Macros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MacroGUI extends JFrame {

    private Macro currentMacro;

    private final JPanel MACRO_USE_PANEL = new JPanel();
    private final MacroEditPanel MACRO_EDIT_PANEL = new MacroEditPanel();
    private JPanel currentView;

    private final GridBagLayout MACRO_USE_LAYOUT = new GridBagLayout();
    private final GridBagLayout MACRO_EDIT_LAYOUT = new GridBagLayout();

    private final JLabel NAME_LABEL = new JLabel();
    private final JLabel TYPE_LABEL = new JLabel();
    private final JLabel STATUS_LABEL = new JLabel("Macro Status: Inactive");

    public MacroGUI() {


        add(MACRO_USE_PANEL);
        currentView = MACRO_USE_PANEL;

        currentMacro = Macros.AUTO_CLICK;

        MACRO_USE_PANEL.setLayout(MACRO_USE_LAYOUT);

        addStatusLabels();
        addButtons();

        setSize(new Dimension(400, 300));
        setTitle("Macro");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //add labels showing macro information to macro use panel
    private void addStatusLabels() {
        JPanel labelPanel = new JPanel();
        initEditPanel();

        NAME_LABEL.setText("Current Macro: " + currentMacro.getName());
        TYPE_LABEL.setText("Macro Type: default");

        labelPanel.add(NAME_LABEL);
        labelPanel.add(TYPE_LABEL);
        labelPanel.add(STATUS_LABEL);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 1;
        labelConstraints.weightx = 1.0;
        labelConstraints.weighty = 0.2;
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;

        MACRO_USE_PANEL.add(labelPanel, labelConstraints);

    }

    //add buttons for changing macro or editing
    private void addButtons() {
        JPanel buttonPanel = new JPanel();

        JButton changeMacroButton = new JButton("Change Macro");

        JButton editCurrentButton = new JButton("Edit Current Macro");
        editCurrentButton.addActionListener(new editMacroListener());


        buttonPanel.add(changeMacroButton);
        buttonPanel.add(editCurrentButton);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(0, 0, 0, 100);

        MACRO_USE_PANEL.add(buttonPanel, constraints);

    }

    //creates the macro edit panel
    private void initEditPanel() {
        MACRO_EDIT_PANEL.setLayout(MACRO_EDIT_LAYOUT);



    }

    //edit current macro
    private void editMacro() {
        setView(MACRO_EDIT_PANEL);
        MACRO_EDIT_PANEL.setMacro(currentMacro);
    }

    //sets the currently displayed panel
    private void setView(JPanel view) {
        if (currentView != view) {
            remove(currentView);
            add(view);
            currentView = view;
            repaint();
        }
    }


    //action listener for button to change the current macro
    private static class changeMacroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    //action listener for button to edit current macro
    private class editMacroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editMacro();
        }
    }





}
