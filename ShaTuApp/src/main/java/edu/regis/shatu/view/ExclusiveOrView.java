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
 * ExclusiveOrView class represents a GUI view for performing Exclusive OR (XOR)
 * on two given binary numbers. Users can input their answers in a JTextField
 * and check correctness. Provides functionality for hints and moving to the
 * next question.
 *
 * @author rickb
 */
public class ExclusiveOrView extends GPanel implements ActionListener, KeyListener {

    private final String BINARY_NUMBER_ONE = "0001";
    private final String BINARY_NUMBER_TWO = "1111";
    private JLabel binaryNumberOneLabel;
    private JLabel binaryNumberTwoLabel;
    private JLabel instructionLabel;
    private JTextField answerField;
    private JLabel answerLabel;
    private JButton checkButton;
    private JButton hintButton;
    private JButton nextQuestionButton;

    /**
     * Initialize this view including creating and laying out its child
     * components.
     */
    public ExclusiveOrView() {
        initializeComponents();
        initializeLayout();
    }

    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        instructionLabel = new JLabel("Perform Exclusive OR (XOR) on given binary numbers");

        binaryNumberOneLabel = new JLabel(BINARY_NUMBER_ONE);
        binaryNumberTwoLabel = new JLabel(BINARY_NUMBER_TWO);

        answerLabel = new JLabel("Your answer: ");
        answerField = new JTextField(10);
        answerField.addKeyListener(this);

        // Create and initialize the checkButton
        checkButton = new JButton("Check");
        checkButton.addActionListener(this);

        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);

        nextQuestionButton = new JButton("Next Question");
        nextQuestionButton.addActionListener(this);
    }

    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {
        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.anchor = GridBagConstraints.CENTER;
        centerConstraints.insets = new Insets(5, 5, 5, 5);

        // Add instructionLabel centered
        addc(instructionLabel, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add binaryNumberOneLabel centered below instructionLabel
        addc(binaryNumberOneLabel, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add binaryNumberTwoLabel centered below binaryNumberOneLabel
        addc(binaryNumberTwoLabel, 0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add answerField centered below binaryNumberTwoLabel
        addc(answerField, 0, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        // Add checkButton centered below answerField
        addc(checkButton, 0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add hintButton centered below checkButton
        addc(hintButton, 0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add nextQuestionButton centered below hintButton
        addc(nextQuestionButton, 0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

    }

    /**
     * Performs the XOR operation on two binary strings.
     * 
     * The method compares
     * corresponding bits of the two binary strings and produces a new string
     * where a bit is set to '1' if the corresponding bits in the input strings
     * are different and '0' otherwise.
     * 
     * @param binary1 The first binary string.
     * @param binary2 The second binary string.
     * @return The result of XOR operation as a binary string.
     */
    public static String performXOR(String binary1, String binary2) {
        // Ensure that both input strings have the same length
        int maxLength = Math.max(binary1.length(), binary2.length());
        binary1 = padWithZeroes(binary1, maxLength);
        binary2 = padWithZeroes(binary2, maxLength);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < maxLength; i++) {
            char char1 = binary1.charAt(i);
            char char2 = binary2.charAt(i);

            if (char1 != char2) {
                result.append('1');
            } else {
                result.append('0');
            }
        }

        return result.toString();
    }

    /**
     * Pads the binary string with leading zeroes to make it of the specified
     * length.
     *
     * @param binary The binary string to pad.
     * @param length The desired length.
     * @return The padded binary string.
     */
    private static String padWithZeroes(String binary, int length) {
        StringBuilder paddedBinary = new StringBuilder(binary);
        while (paddedBinary.length() < length) {
            paddedBinary.insert(0, '0');
        }
        return paddedBinary.toString();
    }

    /**
     * Handles the actionPerformed event for buttons in the view.
     *
     * @param event The ActionEvent that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == checkButton) {
            onCheckButton();
        } else if (event.getSource() == hintButton) {
            onNextHint();
        } else if (event.getSource() == nextQuestionButton) {
            onNextQuestion();
        }
    }

    /**
     * Handles the keyTyped event for the view.
     *
     * @param e The KeyEvent that occurred.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this context
    }

    /**
     * Handles the keyPressed event for the view.
     *
     * @param e The KeyEvent that occurred.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && answerField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please provide an answer");
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verifyAnswer();
        }
    }

    /**
     * Handles the keyReleased event for the view.
     *
     * @param e The KeyEvent that occurred.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this context
    }

    /**
     * Verifies the user's answer against the correct answer.
     */
    private void verifyAnswer() {
        String correctAnswer = performXOR(BINARY_NUMBER_ONE, BINARY_NUMBER_TWO);
        if (correctAnswer.equals(answerField.getText())) {
            JOptionPane.showMessageDialog(this, "Correct!");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect, correct answer: " + correctAnswer);
        }
    }

    /**
     * Handles the action for the Next Question button.
     */
    private void onNextQuestion() {
        JOptionPane.showMessageDialog(this, "Next Question");
    }

    /**
     * Handles the action for the Hint button.
     */
    private void onNextHint() {
        JOptionPane.showMessageDialog(this, "Hint");
    }

    /**
     * Handles the action for the Check button.
     */
    private void onCheckButton() {
        if (answerField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please provide an answer");
        } else {
            verifyAnswer();
        }
    }
}
