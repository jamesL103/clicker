package gui;

import clicker.Clicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**The GUI that provides the interaction with the autoclicker
 *
 */
public class ClickerGUI extends JFrame {

    //clicker used to provide clicking input
    private final Clicker clicker = new Clicker();

    //label to show clicking status
    Label clickStatus = new Label("Autoclicker status: " + (clicker.isClicking() ? "active" : "inactive"));
    //label to show frequency
    Label clickerFrequency = new Label("AutoClicker Frequency: " + clicker.getFrequency());

    public ClickerGUI() {
        initGui();
    }

    private void initGui() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(layout);

        getContentPane().addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    /*if (clicker.isClicking()) {
                        clicker.disableClick();
                    } else {
                        clicker.autoClick();
                    }*/
                    System.out.println("success");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

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
            } catch (NumberFormatException ignored) {

            }
        });

        //add label and button
        frequencySetter.add(frequencyField);
        frequencySetter.add(submit);

        add(frequencySetter);
    }



}
