package gui;

import IO.FileManager;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import gui.macroEdit.MacroEditPanel;
import input.AutoInput;
import input.macro.Macro;
import input.macro.Macros;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MacroGUI extends JFrame {

    private Macro currentMacro;


    private final FileManager FILE_MANAGER = new FileManager();

    private final JPanel MACRO_USE_PANEL = new JPanel();
    private final MacroEditPanel MACRO_EDIT_PANEL = new MacroEditPanel(new ExitViewObserver());
    private final MacroMenuPanel MACRO_MENU_PANEL = new MacroMenuPanel(FILE_MANAGER, new ExitViewObserver(), new ChangeMacroObserver());
    private JPanel currentView;

    private final JLabel NAME_LABEL = new JLabel();
    private final JLabel TYPE_LABEL = new JLabel();
    private final JLabel STATUS_LABEL = new JLabel("Macro Status: Inactive");
    private static final String STATUS_MESSAGE = "Macro Status: ";

    private final JButton EDIT_BUTTON = new JButton("Edit Current Macro");

    //input object
    private final AutoInput INPUT = new AutoInput();


    public MacroGUI() {
        super();
        new NativeInputListener();

        //listeners that disable macro input for safety reasons
        addFocusListener(new AppFocusListener());
        addMouseListener(new MouseEnterListener());

        setFont(Fonts.NORMAL);

        add(MACRO_USE_PANEL);
        currentView = MACRO_USE_PANEL;

        changeCurrentMacro(Macros.AUTO_CLICK);

        MACRO_USE_PANEL.setLayout(new GridBagLayout());

        addStatusLabels();
        addHints();
        addButtons();

        setSize(new Dimension(600, 400));
        setTitle("Macro");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //add labels showing macro information to macro use panel
    private void addStatusLabels() {
        JPanel labelPanel = new JPanel();

        updateLabels();

        labelPanel.add(NAME_LABEL);
        NAME_LABEL.setHorizontalAlignment(SwingConstants.CENTER);
        NAME_LABEL.setBorder(new EmptyBorder(0, 0, 0, 10));
        NAME_LABEL.setFont(Fonts.HEADER);
        labelPanel.add(TYPE_LABEL);
        TYPE_LABEL.setBorder(new EmptyBorder(0, 10, 0, 10));
        TYPE_LABEL.setFont(Fonts.HEADER);
        labelPanel.add(STATUS_LABEL);
        STATUS_LABEL.setBorder(new EmptyBorder(0, 10, 0, 0));
        STATUS_LABEL.setFont(Fonts.HEADER);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 1;
        labelConstraints.weightx = 1.0;
        labelConstraints.weighty = 0.2;
        labelConstraints.fill = GridBagConstraints.BOTH;

        MACRO_USE_PANEL.add(labelPanel, labelConstraints);

    }

    private void addHints() {
        JPanel hintPanel = new JPanel();
        JLabel hotkey = new JLabel("Press ctrl + q to toggle the macro.");
        JLabel warning = new JLabel("Using a macro containing inputs in the toggle hotkey is" +
                " NOT recommended.");

        warning.setForeground(Color.RED);

        hintPanel.add(hotkey);
        hintPanel.add(warning);

        GridBagConstraints infoConstraints = new GridBagConstraints();
        infoConstraints.gridx = 0;
        infoConstraints.gridy = 2;
        infoConstraints.weightx = 1.0;
        infoConstraints.weighty = 0.4;
        infoConstraints.fill = GridBagConstraints.BOTH;

        MACRO_USE_PANEL.add(hintPanel, infoConstraints);
    }

    //add buttons for changing macro or editing
    private void addButtons() {
        JPanel buttonPanel = new JPanel();

        JButton changeMacroButton = new JButton("Change Macro");
        changeMacroButton.addActionListener(new ChangeMacroButtonListener());

        EDIT_BUTTON.addActionListener(new EditMacroListener());


        buttonPanel.add(changeMacroButton);
        buttonPanel.add(EDIT_BUTTON);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0.5;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        MACRO_USE_PANEL.add(buttonPanel, constraints);

    }

    //edit current macro
    private void editMacro() {
        setView(MACRO_EDIT_PANEL);
        MACRO_EDIT_PANEL.setMacro(currentMacro);
    }

    //open macro manager menu
    private void openMacroMenu() {
        setView(MACRO_MENU_PANEL);
    }

    //sets the currently displayed panel
    private void setView(JPanel view) {
        if (currentView != view) {
            remove(currentView);
            add(view);
            currentView = view;
            revalidate(); //motherfucker took like an hour just to figure out
            repaint();
        }
    }

    //update informational labels when macro is changed
    private void updateLabels() {
        NAME_LABEL.setText("Current Macro: " + currentMacro.getName());
        TYPE_LABEL.setText("Macro Type: " + currentMacro.getType().toString());
    }

    //changes currently selected macro to specified one
    private void changeCurrentMacro(Macro macro) {
        if (macro.equals(currentMacro)) {
            return;
        }

        currentMacro = macro;
        INPUT.setMacro(currentMacro);
        MACRO_MENU_PANEL.setMacro(currentMacro);
        MACRO_EDIT_PANEL.setMacro(currentMacro);

        if (currentMacro.isPreset()) {
            //disable editing buttons
            EDIT_BUTTON.setEnabled(false);
        } else {
            EDIT_BUTTON.setEnabled(true);
        }
        updateLabels();
    }


    //activates input and updates status
    public void activateInput() {
        if (INPUT.isActive()) {
            return;
        }
        INPUT.activate();
        if (currentMacro.getType() != Macro.MacroType.SINGLE) {
            STATUS_LABEL.setText(STATUS_MESSAGE + "Active");
        }
    }

    //disables input and updates status
    public void disableInput() {
        INPUT.disableInput();
        STATUS_LABEL.setText(STATUS_MESSAGE + "Inactive");
    }

    //action listener for button to change the current macro
    private class ChangeMacroButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            openMacroMenu();
        }
    }

    //action listener for button to edit current macro
    private class EditMacroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editMacro();
        }
    }

    //observer to notify the GUI to update the selected macro
    public class ChangeMacroObserver {

        public void changeMacroTo(Macro macro) {
            changeCurrentMacro(macro);
        }

    }

    //observer to notify closing other view panels
    //and setting back to default view
    public class ExitViewObserver {

        public void notifyExit() {
                setView(MACRO_USE_PANEL);
        }

    }

    //native input listener for toggling macro
    private class NativeInputListener implements NativeKeyListener {

        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {
            if (e.getKeyCode() == NativeKeyEvent.VC_Q && (e.getModifiers() & NativeKeyEvent.ALT_L_MASK) != 0) {
                if (INPUT.isActive()) {
                    disableInput();
                } else {
                    activateInput();
                }
            }
        }

        public NativeInputListener() {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                System.err.println("Error registering native input hook");
                System.err.println(e.getMessage());
                System.exit(1);
            }

            GlobalScreen.addNativeKeyListener(this);
        }
    }

    //listener for when focus is gained on the application
    private class AppFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            disableInput();
        }

        @Override
        public void focusLost(FocusEvent e) {

        }
    }

    //listener for mouse entering the application
    private class MouseEnterListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            disableInput();
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}
