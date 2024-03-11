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
 * ChoiceFunctionView class represents a GUI view for a choice function Ch(x, y, z).
 * Users can input their answers in a JTextField and check correctness.
 * Provides functionality for hints and moving to the next question.
 *
 * @author rickb
 */
public class ChoiceFunctionView extends GPanel implements ActionListener, KeyListener {
    private final String stringX = "10100";
    private final String stringY = "11100";
    private final String stringZ = "10111";
    
    private JLabel instructionLabel;
    private JLabel stringXLabel;
    private JLabel stringYLabel;
    private JLabel stringZLabel;
    private JTextField answerField;
    private JLabel answerLabel;
    private JButton checkButton;
    private JButton hintButton;
    private JButton nextQuestionButton;
    
    /**
     * Initializes the ChoiceFunctionView by creating and laying out its child components.
     */
    public ChoiceFunctionView() {
        initializeComponents();
        initializeLayout();
    }

    /**
     * Creates child GUI components for the view.
     */
    private void initializeComponents() {
        instructionLabel = new JLabel("Ch(𝑥,𝑦,𝑧)=(𝑥∧𝑦)⊕(¬𝑥∧𝑧)");
        
        stringXLabel = new JLabel("x: " + stringX);
        stringYLabel = new JLabel("y: " + stringY);
        stringZLabel = new JLabel("z: " + stringZ);

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
     * Lays out the child components in the view.
     */
    private void initializeLayout() {
        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.anchor = GridBagConstraints.CENTER;
        centerConstraints.insets = new Insets(5, 5, 5, 5);

        // Add instructionLabel centered
        addc(instructionLabel, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add stringXLabel centered below instructionLabel
        addc(stringXLabel, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add stringYLabel centered below stringXLabel
        addc(stringYLabel, 0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        // Add stringZLabel centered below stringYLabel
        addc(stringZLabel, 0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add answerField centered below stringZLabel
        addc(answerField, 0, 4, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        // Add checkButton centered below answerField
        addc(checkButton, 0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        // Add hintButton centered below checkButton
        addc(hintButton, 0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);  
        
        // Add nextQuestionButton centered below hintButton
        addc(nextQuestionButton, 0, 7, 1,1,0.0,0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
    }
    
    /**
     * Evaluates the choice function Ch(x, y, z).
     *
     * @param x Binary string representation of x.
     * @param y Binary string representation of y.
     * @param z Binary string representation of z.
     * @return Binary string result of Ch(x, y, z).
     */
    private static String choiceFunction(String x, String y, String z) {
        // Convert the binary strings to integer values
        int intX = Integer.parseInt(x, 2);
        int intY = Integer.parseInt(y, 2);
        int intZ = Integer.parseInt(z, 2);

        int xy = intX & intY;

        int notX = ~intX & intZ;

        int result = xy ^ notX;

        // Convert the result back to binary string
        String binaryResult = Integer.toBinaryString(result);

        return binaryResult;
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
    }

    /**
     * Verifies the user's answer against the correct answer.
     */
    private void verifyAnswer() {
        String correctAnswer = choiceFunction(stringX, stringY, stringZ);
        if (correctAnswer.equals(answerField.getText())) {
            JOptionPane.showMessageDialog(this, "Correct!");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect, "
                    + "correct answer: " + correctAnswer);
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
