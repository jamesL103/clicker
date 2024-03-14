package gui;

import clicker.Clicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**The GUI that provides the interaction with the autoclicker
 *
 */
public class ClickerGUI extends JFrame {

    //clicker used to provide clicking input
    private final Clicker clicker = new Clicker();

    //label to show clicking status
    Label clickStatus = new Label("Autoclicker status: inactive");
    //label to show frequency
    Label clickerFrequency = new Label("AutoClicker Frequency: " + clicker.getFrequency());

    /**Instantiates and displays the GUI for the clicker.
     *
     */
    public ClickerGUI() {
        initGui();
    }

    private void initGui() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(layout);

        setFocusable(true);

        addKeyListener(new GUIKeyListener());

        addMouseListener(new GUIMouseListener());

        addFocusListener(new GUIFocusListener());

        setName("Auto Clicker");
        add(new Label("Auto Clicker"));
        add(clickStatus);
        add(clickerFrequency);
        initFrequencySetter();

        setSize(400, 300);
        setVisible(true);
    }

    private void initFrequencySetter() {
        Container frequencySetter = new Container();
        frequencySetter.setLayout(new FlowLayout());
        frequencySetter.add(new Label("Set Frequency: "));

        JTextField frequencyField = new JTextField(); //text field to input frequency
        frequencyField.setPreferredSize(new Dimension(70, 30));

        JButton submit = new JButton("Confirm"); //button to confirm frequency update

        submit.addActionListener((e) -> {
            try { //checks if there is a valid int to parse from the text field
                int freq = Integer.parseInt(frequencyField.getText());
                if (freq > 0) {
                clicker.setFrequency(freq);
                clickerFrequency.setText("AutoClicker Frequency: " + clicker.getFrequency());
                }
            } catch (NumberFormatException ignored) { //if no valid int, do nothing

            }
        });

        //add label and button
        frequencySetter.add(frequencyField);
        frequencySetter.add(submit);

        add(frequencySetter);
    }

    /**KeyListener for the main panel
     * Handles the hotkeys that toggle the autoclicker.
     *
     */
    private class GUIKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                if (clicker.isClicking()) {
                    clicker.disableClick();
                    clickStatus.setText("Autoclicker status: inactive");
                } else {
                    clicker.autoClick();
                    clickStatus.setText("Autoclicker status: active");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**MouseListener for the main panel
     *
     */
    private class GUIMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        /**Brings the main panel into focus when the mouse enters it.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            requestFocus();
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
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
            clicker.disableClick();
            clickStatus.setText("Autoclicker status: inactive");
        }

        @Override
        public void focusLost(FocusEvent e) {

        }
    }



}
