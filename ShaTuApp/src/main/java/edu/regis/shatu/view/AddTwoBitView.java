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
import java.math.BigInteger;

/**
 *
 * @author rickb
 */
public class AddTwoBitView extends GPanel implements ActionListener {
    /**
     * The ASCII character the student is being asked to convert
     */
    private final int m = 8; // will be changed and dynamically updated

    private final String binary1 = "101100";
    private final String binary2 = "011011";


    private JTextField answerField;
    private JLabel answerLabel;
    private JButton checkButton; // Add the check button
    private JButton hintButton;

    /**
     * Initialize this view including creating and laying out its child components.
     */
    public AddTwoBitView() {
        initializeComponents();
        initializeLayout();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String correctAnswer;
        if (event.getSource() == checkButton) {
            correctAnswer = calculateModulo(binary1, binary2);
            // Get the text from the answerField when the checkButton is clicked
            String userAnswer = answerField.getText();

            if (userAnswer.equals(correctAnswer)) {
                JOptionPane.showMessageDialog(this, "Correct");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect.");
            }
        }
        else if (event.getSource() == hintButton) {
            JOptionPane.showMessageDialog(this, "Hint");
        }
    }

    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {

        answerLabel = new JLabel("         Your answer: ");
        answerField = new JTextField(10);

        // Create and initialize the checkButton
        checkButton = new JButton("Check");
        checkButton.addActionListener(this); // Add an action listener for the check button
        
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);
    }

    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {

        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.anchor = GridBagConstraints.CENTER;
        centerConstraints.insets = new Insets(5, 5, 5, 5);

        // Add exampleInputLabel centered
        addc(new JLabel("Add two binary numbers using modulo 2^"+ m+ " addition"), 0, 0, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        addc(new JLabel("binary number1 : "), 0, 1, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        addc(new JLabel(binary1), 1, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        addc(new JLabel("binary number2 : "), 0, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        addc(new JLabel(binary2), 1, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);


        // Add answerLabel to the layout, centered
        addc(answerLabel, 0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        // Add answerField to the layout, centered
        addc(answerField, 1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.LINE_START, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        addc(checkButton, 0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);
        
        addc(hintButton, 0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);
    }

    public String calculateModulo(String binary1, String binary2) {
        if (binary1 == null || binary1.isEmpty()) {
            return "";
        }
        if (binary2 == null || binary2.isEmpty()) {
            return "";
        }

        // Convert binary strings to BigIntegers
        BigInteger num1 = new BigInteger(binary1, 2);
        BigInteger num2 = new BigInteger(binary2, 2);

        // Perform addition
        BigInteger sum = num1.add(num2);

        // Calculate the result modulo 2^256
        BigInteger modulo = new BigInteger("2").pow(m);
        BigInteger result = sum.mod(modulo);

        // Convert the result back to a binary string
        String resultBinary = result.toString(2);

        // Ensure the binary string has 256 bits (pad with leading zeros if necessary)
        while (resultBinary.length() < m) {
            resultBinary = "0" + resultBinary;
        }

        System.out.println("Result : " + resultBinary);

        return resultBinary;
    }
}