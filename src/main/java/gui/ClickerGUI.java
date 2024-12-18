package gui;

import clicker.Clicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;


/**The GUI that provides the interaction with the autoclicker
 *
 */
public class ClickerGUI extends JFrame {

    //clicker used to provide clicking input
    private final Clicker CLICKER = new Clicker();

    //key to toggle clicking
    private final int TOGGLE_KEY = NativeKeyEvent.VC_ESCAPE;

    //label to show clicking status
    private final JLabel CLICK_STATUS_LABEL = new JLabel("Autoclicker status: inactive");
    //label to show frequency
    private final JLabel CLICKER_FREQ_LABEL = new JLabel("AutoClicker Frequency: " + CLICKER.getFrequency() + " clicks/s");

    /**Instantiates and displays the GUI for the clicker.
     *
     */
    public ClickerGUI() {
        initGui();
    }

    private void initGui() {

        BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(layout);


        addFocusListener(new GUIFocusListener());

        setName("Auto Clicker");
        add(new JLabel("Auto Clicker"));
        add(CLICK_STATUS_LABEL);
        add(CLICKER_FREQ_LABEL);
        initFrequencySetter();

        initGlobalInput();

        setSize(400, 300);
        setVisible(true);
        setFocusable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initFrequencySetter() {
        JPanel freq_set_panel = new JPanel();
        freq_set_panel.setLayout(new FlowLayout());
        freq_set_panel.add(new JLabel("Set Frequency: "));

        JTextField frequencyField = new JTextField(); //text field to input frequency
        frequencyField.setText(String.valueOf(CLICKER.getFrequency()));
        frequencyField.setPreferredSize(new Dimension(70, 30));

        JButton submit = new JButton("Confirm"); //button to confirm frequency update

        submit.addActionListener((e) -> {
            try { //checks if there is a valid int to parse from the text field
                int freq = Integer.parseInt(frequencyField.getText());
                if (freq > 0) {
                    CLICKER.setFrequency(freq);
                    CLICKER_FREQ_LABEL.setText("AutoClicker Frequency: " + CLICKER.getFrequency() + " clicks/s");
                }
            } catch (NumberFormatException ignored) { //if no valid int, do nothing

            }
        });

        //add label and button
        freq_set_panel.add(frequencyField);
        freq_set_panel.add(submit);

        add(freq_set_panel);
    }

    //init native hook and global input listener
    private void initGlobalInput() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.out.println("error initializing native hook");
        }
        GlobalScreen.addNativeKeyListener(new globalKeyListener());
    }


    /**FocusListener for the main frame.
     * Allows the main frame to disable the autoclicker when focus is gained
     * on it.
     */
    private class GUIFocusListener implements FocusListener {

        /**Disables the autoclicker when focus is gained in the main frame.
         *
         * @param e the event to be processed
         */
        @Override
        public void focusGained(FocusEvent e) {
            CLICKER.disableClick();
            CLICK_STATUS_LABEL.setText("Autoclicker status: inactive");
        }

        @Override
        public void focusLost(FocusEvent e) {

        }
    }

    /**Global input listener to detect when user presses the key to
     * toggle clicking.
     *
     */
    private class globalKeyListener implements NativeKeyListener {

        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {
            if (e.getKeyCode() == TOGGLE_KEY) {
                if (CLICKER.isClicking()) {
                    CLICKER.disableClick();
                    CLICK_STATUS_LABEL.setText("Autoclicker status: inactive");
                } else {
                    CLICKER.autoClick();
                    CLICK_STATUS_LABEL.setText("Autoclicker status: active");
                }
            }
        }
    }

}
