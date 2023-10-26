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

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author rickb
 */
public class RotateView extends GPanel implements ActionListener {
   /**
     * The ASCII character the student is being asked to convert
     */
    private final int NO_ROTATIONS = 7; // will be changed and dynamically updated
    private final String EXAMPLE_INPUT = "Example Input";
    
    private String answer;
    private JLabel exampleInputLabel;
    private JTextField answerField;
    private JLabel answerLabel;
    private JButton checkButton; // Add the check button
    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public RotateView() {
        initializeComponents();
        initializeLayout();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String correctAnswer;
        if (event.getSource() == checkButton) {
            correctAnswer = rotateString(EXAMPLE_INPUT,NO_ROTATIONS);
            // Get the text from the answerField when the checkButton is clicked
            String userAnswer = answerField.getText();
            
            if (userAnswer.equals(correctAnswer)) {
                JOptionPane.showMessageDialog(this, "Correct");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is: " + correctAnswer);
            }
        }
    }

    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        exampleInputLabel = new JLabel("Perform ROTR(" + NO_ROTATIONS + ") on: " + EXAMPLE_INPUT);

        answerLabel = new JLabel("Your answer: ");
        answerField = new JTextField(10);

        // Create and initialize the checkButton
        checkButton = new JButton("Check");
        checkButton.addActionListener(this); // Add an action listener for the check button
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
    }
    
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
}