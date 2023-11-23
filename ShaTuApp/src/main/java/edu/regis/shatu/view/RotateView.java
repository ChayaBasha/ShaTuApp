/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * RotateView class represents the GUI view for rotating strings using ROTR (Right Rotate).
 * It extends GPanel and implements ActionListener and KeyListener interfaces.
 * 
 * @author rickb
 */
public class RotateView extends GPanel implements ActionListener, KeyListener {

    /**
     * The number of rotations used in the ROTR operation.
     */
    private final int NO_ROTATIONS = 7; // will be changed and dynamically updated
    
    /**
     * Example input string for ROTR operation.
     */
    private final String EXAMPLE_INPUT = "Example Input";
    
    private String answer;
    private JLabel exampleInputLabel;
    private JTextField answerField;
    private JLabel answerLabel;
    private JButton checkButton; // Add the check button
    private JButton hintButton;
    
    /**
     * Initializes the RotateView by creating and laying out its child components.
     */
    public RotateView() {
        initializeComponents();
        initializeLayout();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == checkButton) {
            if (answerField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please provide an answer");
            } else {
                verifyAnswer();
            }
        } else if (event.getSource() == hintButton) {
            JOptionPane.showMessageDialog(this, "Hint");
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && answerField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please provide an answer");
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verifyAnswer();
        }
    }

    /**
     * Creates and initializes the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        exampleInputLabel = new JLabel("Perform ROTR(" + NO_ROTATIONS + ") on: " + EXAMPLE_INPUT);

        answerLabel = new JLabel("Your answer: ");
        answerField = new JTextField(10);
        answerField.addKeyListener(this);
        
        // Create and initialize the checkButton
        checkButton = new JButton("Check");
        checkButton.addActionListener(this); // Add an action listener for the check button
        
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);
    }
    
    /**
     * Lays out the child components in this view using GridBagConstraints.
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
    
    /**
     * Performs right rotation (ROTR) on the given input string for the specified number of positions.
     * 
     * @param input The input string to rotate.
     * @param positions The number of positions for the rotation.
     * @return The rotated string.
     */
    public String rotateString(String input, int positions) {
        if (input == null || input.isEmpty()) {
            return input; 
        }

        int length = input.length();
        positions = positions % length; // Ensure positions is within the string length

        if (positions < 0) {
            positions = length + positions; // Handle negative positions
        }

        // Perform the rotation
        answer = input.substring(length - positions) + input.substring(0, length - positions);

        return answer;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    /**
     * Verifies the user's answer by comparing it with the correct rotated string.
     */
    private void verifyAnswer() {
        String correctAnswer = rotateString(EXAMPLE_INPUT, NO_ROTATIONS);
        // Get the text from the answerField when the checkButton is clicked
        String userAnswer = answerField.getText();
        
        if (userAnswer.equals(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is: " + correctAnswer);
        }
    }
}
