/*
 * SHATU: SHA-256 Tutor
 *
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 *
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibted.
 *
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author rickb
 */
public class ShaOne extends GPanel implements ActionListener, KeyListener {
    /**
     * The ASCII character the student is being asked to convert
     */
    private final int X_PLACES = 10; // will be changed and dynamically updated
    private final int EXAMPLE_INPUT = 0b11011010101010101010101010101010;

    private String answer;
    private JLabel exampleInputLabel;
    private JTextField answerField;
    private JLabel answerLabel;
    private JButton checkButton; // Add the check button
    private JButton hintButton;

    /**
     * Initialize this view including creating and laying out its child components.
     */
    public ShaOne() {
        initializeComponents();
        initializeLayout();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == checkButton) {
            if (answerField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please proivde an answer");
            } else {
                verifyAnswer();
            }
        } else if (event.getSource() == hintButton) {
            JOptionPane.showMessageDialog(this, "Hint");
        }
    }

    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {

        exampleInputLabel = new JLabel("Given an 𝑛 bit binary number, output the value of the SHA 𝛴₁ function");

        answerLabel = new JLabel("Your answer: ");
        answerField = new JTextField(10);
        answerField.addKeyListener(this);

        // Create and initialize the checkButton
        checkButton = new JButton("Check");
        checkButton.addActionListener(this);
        
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this); // Add an action listener for the check button
    }

    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {

        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.anchor = GridBagConstraints.CENTER;
        centerConstraints.insets = new Insets(5, 5, 5, 5);

        // Add exampleInputLabel centered
        addc(exampleInputLabel, 0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add answerLabel to the layout, centered
        addc(answerLabel, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add answerField to the layout, centered
        addc(answerField, 1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        addc(checkButton, 0, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        addc(hintButton, 0, 3, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
    }

    public String shiftRightString(int x, int places) {

        // Perform the right shift operation
        int result = x >> places;

        // Print the original and shifted binary numbers
        System.out.println("Original Binary: " + Integer.toBinaryString(x));
        System.out.println("Shifted Binary:  " + Integer.toBinaryString(result));

        return Integer.toBinaryString(result);

    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && answerField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please proivde an answer");
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verifyAnswer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void verifyAnswer() {
        String correctAnswer = shiftRightString(EXAMPLE_INPUT, X_PLACES);
        // Get the text from the answerField when the checkButton is clicked
        String userAnswer = answerField.getText();

        if (userAnswer.equals(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is: " + correctAnswer);
        }
    }
}
