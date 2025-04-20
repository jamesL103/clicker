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


        currentMacro = Macros.AUTO_CLICK;
        INPUT.setMacro(currentMacro);
        MACRO_MENU_PANEL.setMacro(currentMacro);
        MACRO_EDIT_PANEL.setMacro(currentMacro);


        MACRO_USE_PANEL.setLayout(new GridBagLayout());

        addStatusLabels();
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
        NAME_LABEL.setFont(Fonts.NORMAL);
        labelPanel.add(TYPE_LABEL);
        TYPE_LABEL.setBorder(new EmptyBorder(0, 10, 0, 10));
        TYPE_LABEL.setFont(Fonts.NORMAL);
        labelPanel.add(STATUS_LABEL);
        STATUS_LABEL.setBorder(new EmptyBorder(0, 10, 0, 0));
        STATUS_LABEL.setFont(Fonts.NORMAL);

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
        changeMacroButton.addActionListener(new changeMacroListener());

        JButton editCurrentButton = new JButton("Edit Current Macro");
        editCurrentButton.addActionListener(new editMacroListener());


        buttonPanel.add(changeMacroButton);
        buttonPanel.add(editCurrentButton);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 2;
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
        MACRO_MENU_PANEL.repaint();
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

    //update labels when macro is changed
    private void updateLabels() {
        NAME_LABEL.setText("Current Macro: " + currentMacro.getName());
        TYPE_LABEL.setText("Macro Type: default");
    }

    //changes currently selected macro to specified one
    private void changeCurrentMacro(Macro macro) {
        currentMacro = macro;
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
    private class changeMacroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            openMacroMenu();
        }
    }

    //action listener for button to edit current macro
    private class editMacroListener implements ActionListener {

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
