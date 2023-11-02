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
public class ChoiceFunctionView extends GPanel implements ActionListener {
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
    
   /**
     * The ASCII character the student is being asked to convert
     */
    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public ChoiceFunctionView() {
        initializeComponents();
        initializeLayout();
    }

   

    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        instructionLabel = new JLabel("Ch(𝑥,𝑦,𝑧)=(𝑥∧𝑦)⊕(¬𝑥∧𝑧)");
        
        stringXLabel = new JLabel("x: " + stringX);
        stringYLabel = new JLabel("x: " + stringY);
        stringZLabel = new JLabel("x: " + stringZ);

        answerLabel = new JLabel("Your answer: ");
        answerField = new JTextField(10);

        // Create and initialize the checkButton
        checkButton = new JButton("Check");
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
        addc(stringXLabel, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add binaryNumberTwoLabel centered below binaryNumberOneLabel
        addc(stringYLabel, 0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        addc(stringZLabel, 0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add answerField centered below binaryNumberTwoLabel
        addc(answerField, 0, 4, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        // Add checkButton centered below answerField
        addc(checkButton, 0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        checkButton.addActionListener(this); // Add an action listener for the check button
    }
    
    public static String choiceFunction(String x, String y, String z) {
        // Convert the binary strings to integer values
        int intX = Integer.parseInt(x, 2);
        int intY = Integer.parseInt(y, 2);
        int intZ = Integer.parseInt(z, 2);

       
        int xy = intX & intY;

        int notX = ~intX & intZ;

        int result = xy ^ notX;

        String binaryResult = Integer.toBinaryString(result);

        return binaryResult;
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        String correctAnswer = choiceFunction(stringX, stringY, stringZ);
       
       if(correctAnswer.equals(answerField.getText())) {
           JOptionPane.showMessageDialog(this, "Correct!");
       } else {
           JOptionPane.showMessageDialog(this, "Incorrect, "
                   + "correct answer: " + correctAnswer);
       }
    }
}