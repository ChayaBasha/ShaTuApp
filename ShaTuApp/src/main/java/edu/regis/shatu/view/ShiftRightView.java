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

import edu.regis.shatu.model.StepCompletion;
import edu.regis.shatu.model.aol.NewExampleRequest;
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
public class ShiftRightView extends UserRequestView implements ActionListener, KeyListener {
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
        // Convert the example input to a binary string and add spaces every 4 bits (nibble)
        String binaryStringWithSpaces = formatBinaryString(Integer.toBinaryString(EXAMPLE_INPUT));

        // Update the exampleInputLabel with the new string
        exampleInputLabel = new JLabel("Perform Shift right on a 32 bit   " 
                + binaryStringWithSpaces + "  number " + X_PLACES + " places");

        // Create a new label with correct spacing for the bit markers
        binaryLabelsLabel = new JLabel("                                                           "
                + "4|      8|    12|    16|    20|    24|    28|    32|");

        // Initialize other components as before
        answerField = new JTextField(20);
        answerField.addKeyListener(this);

        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
    }

    private String formatBinaryString(String binary) {
        StringBuilder sb = new StringBuilder(binary);
        // Ensure the string has a length of 32 characters
        while (sb.length() < 32) {
            sb.insert(0, "0");
        }
        // Insert spaces every 4 characters from the end
        for (int i = 4; i < sb.length(); i += 5) {
            sb.insert(sb.length() - i, " ");
        }
        return sb.toString();
    }

    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Common settings
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Title label
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        JLabel titleLabel = new JLabel("Binary Right Shift Tutor");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, gbc);

        // Description label
        gbc.gridy++;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        JLabel descriptionLabel = new JLabel("<html><p>Perform a binary right shift on the provided 32-bit number to learn how bitwise operations work in SHA-256 hash computations.</p></html>");
        add(descriptionLabel, gbc);

        // Bit position markers label
        gbc.gridy++;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        add(binaryLabelsLabel, gbc);

        // Input label
        gbc.gridy++;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        add(exampleInputLabel, gbc);

        // Input field
        gbc.gridy++;
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2; // Adjust weight as necessary to make the field smaller
        answerField.setPreferredSize(new Dimension(120, answerField.getPreferredSize().height)); // Set the preferred width
        add(answerField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        hintButton.setPreferredSize(new Dimension(80, 25)); // Set the preferred size for the buttons
        submitButton.setPreferredSize(new Dimension(80, 25));
        buttonPanel.add(hintButton);
        buttonPanel.add(submitButton);
        gbc.gridx++; gbc.weightx = 0.8; // Adjust weight to push buttons to the left
        gbc.fill = GridBagConstraints.NONE; // Do not fill horizontally
        add(buttonPanel, gbc);

        // Filler panel to push everything up
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty = 1; gbc.weightx = 1; // Assign more weight to push contents to the top
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        JPanel filler = new JPanel();
        add(filler, gbc);
    }

    public void setModel(String bitString, int shiftAmount, boolean isShiftLeft) {
        // Update the view with the provided information
        updateView(bitString, shiftAmount, isShiftLeft);
    }

    private void updateView(String bitString, int shiftAmount, boolean isShiftLeft) {
        // Update the exampleInputLabel to display the bitString and shiftAmount
        exampleInputLabel.setText("Perform " + (isShiftLeft ? "left" : "right") + " shift on a 32-bit " 
                + bitString + " number " + shiftAmount + " places");
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
        int result = x >>> places;

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
        String formattedBinary = formatBinaryString(correctAnswer);

        // Bit position markers formatted to align with the binary string
        String bitPositions = "  4|   8|  12|  16|  20|  24|  28|  32|";

        // Get the text from the answerField when the checkButton is clicked
        String userAnswer = answerField.getText();

        // Construct a formatted string to display the correct binary representation
        String message = "<html><pre>Bit positions: " + bitPositions + "<br/>Binary:        " + formattedBinary + "</pre></html>";

        if (userAnswer.equals(correctAnswer.replaceAll(" ", ""))) {
            JOptionPane.showMessageDialog(this, message, "Correct", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, message, "Incorrect. The correct answer is:", JOptionPane.ERROR_MESSAGE);
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

    @Override
    public NewExampleRequest newRequest() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public StepCompletion stepCompletion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
