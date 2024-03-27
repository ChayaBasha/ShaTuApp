/**
 * SHATU: SHA-256 Tutor
 * <p>
 * (C) Johanna & Richard Blumenthal, All rights reserved
 * <p>
 * Unauthorized use, duplication, or distribution without the authors' permission is strictly prohibited.
 * <p>
 * Unless required by applicable law or agreed to in writing, this software is distributed on an "AS IS" basis
 * without warranties or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * ShiftRightView class represents the GUI view for performing the right shift operation on a binary number.
 * It extends GPanel and implements ActionListener and KeyListener interfaces.
 * <p>
 * The class provides a user interface for shifting a binary number to the right by a specified number of places
 * and checking the result. Inline comments have been added for better understanding of the code.
 *
 * @author rickb
 */
public class ShiftRightView extends GPanel implements ActionListener, KeyListener {
    /**
     * The number of places for the right shift operation.
     */
    private final int X_PLACES = 10; // will be changed and dynamically updated
    private final int EXAMPLE_INPUT = 0b11011010101010101010101010101010;

    private String answer;
    private JLabel exampleInputLabel;
    private JTextField answerField;
    private JButton hintButton;
    private JButton submitButton;
    private Component binaryLabelsLabel;

    /**
     * Initialize this view including creating and laying out its child components.
     */
    public ShiftRightView() {
        initializeComponents();
        initializeLayout();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == hintButton) {
            onNextHint();
        } else if (event.getSource() == submitButton) {
            onSubmitButton();
        }
    }

    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(EXAMPLE_INPUT));
        sb.insert(6, " ");
        sb.insert(12, " ");
        sb.insert(18, " ");
        String displayQuestion = sb.toString();
        
        sb = new StringBuilder();
        sb.append("                          6|");
        for (int i = 1; i < 3; i++) {
            sb.append("\t      ").append(6 + 5 * i).append("|");
        }
        sb.append("\t                            32|");
        binaryLabelsLabel = new JLabel(sb.toString());
             
        exampleInputLabel = new JLabel("Perform Shift right an 32 bit   " + displayQuestion + "  number " + X_PLACES + " places");

        answerField = new JTextField(10);
        answerField.addKeyListener(this);
      
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this); // Add an action listener for the check button
        
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
    }

    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {

        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.anchor = GridBagConstraints.CENTER;
        centerConstraints.insets = new Insets(5, 5, 5, 5);

        // Add binaryLabelsLabel
        addc(binaryLabelsLabel, 0, 0, 2, 1, 0.0, 0.0, 
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add exampleInputLabel centered below binaryLabelsLabel
        addc(exampleInputLabel, 0, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add answerField to the layout, centered below exampleInputLabel
        addc(answerField, 0, 2, 2, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        addc(hintButton, 0, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        addc(submitButton, 0, 5, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
    }


    /**
     * Performs the right shift operation on the given input binary number for the specified number of places.
     *
     * @param x      The input binary number.
     * @param places The number of places for the right shift.
     * @return The result after performing the right shift operation.
     */
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
            JOptionPane.showMessageDialog(this, "Please provide an answer");
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verifyAnswer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Verifies the user's answer by comparing it with the correct result of the right shift operation.
     */
    private void verifyAnswer() {      
        String correctAnswer = shiftRightString(EXAMPLE_INPUT, X_PLACES);
        // Get the text from the answerField when the checkButton is clicked
        String userAnswer = answerField.getText();

        if (userAnswer.equals(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct");
        } else {
            // Construct a formatted string to display the correct binary representation
            StringBuilder sb = new StringBuilder(correctAnswer);
            sb.insert(6, " ");
            sb.insert(12, " ");
            sb.insert(18, " ");
            correctAnswer = sb.toString();
        
            sb = new StringBuilder();
            sb.append("         6|");
            for (int i = 1; i < 3; i++) {
                sb.append("\t      ").append(6 + 5 * i).append("|");
            }
            sb.append("\t                           32|%n%32s");
            String correctBinary = String.format(sb.toString(), correctAnswer);
            // Display the incorrect message along with the correct binary representation
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is:\n\n" + correctBinary);
        }
    }
    
    /**
     * Displays a message dialog indicating the start of the next question.
     */
    private void onSubmitButton() {
        verifyAnswer(); //For noww, just verify the answer when submit is clicked
    }

    /**
     * Displays a message dialog indicating the provision of a hint.
     */
    private void onNextHint() {
        JOptionPane.showMessageDialog(this, "Hint");
    }
}
