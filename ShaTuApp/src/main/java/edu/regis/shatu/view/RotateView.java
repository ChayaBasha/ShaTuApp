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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.regis.shatu.model.Step;
import edu.regis.shatu.model.StepCompletion;
import edu.regis.shatu.model.aol.ExampleType;
import edu.regis.shatu.model.aol.NewExampleRequest;
import edu.regis.shatu.model.aol.RotateStep;
import edu.regis.shatu.view.act.NewExampleAction;
import edu.regis.shatu.svc.ShaTuTutor;
import edu.regis.shatu.model.Task;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JPanel;
/**
 * RotateView class represents the GUI view for rotating strings using ROTR (Right Rotate).
 * It extends GPanel and implements ActionListener and KeyListener interfaces.
 * <p>
 * The class provides a user interface for performing right rotations on strings and checking the results.
 * Inline comments have been added for better understanding of the code.
 *
 * @author rickb
 */
public class RotateView extends UserRequestView implements ActionListener, KeyListener {
    /**
     * The number of rotations used in the ROTR operation.
     */
    private final int NO_ROTATIONS = 7; // will be changed and dynamically updated

    /**
     * Example input string for ROTR operation.
     */
    private final String EXAMPLE_INPUT = "Example Input";
    
    private int numRotations;
    private String problemString;
    private JTextPane bitShiftPane;
    private JLabel prompt;
    private JLabel problem;
    private JTextField answerField;
    private JButton checkButton; // Add the check button
    private JButton hintButton;
    private JButton nextQuestionButton;
    private JButton newExampleButton;
    private JButton nextStepButton;
    private JRadioButton shortProblem;
    private JRadioButton longProblem;
    private JRadioButton rightRotate;
    private JRadioButton leftRotate;
    private JRadioButton rotate7Bits;
    private JRadioButton rotate16Bits;
    private ButtonGroup rotateRadioGrp;
    private ButtonGroup lengthRadioGrp;
    private ButtonGroup amountRadioGrp;
    private JPanel mainRadioPanel;
    private JPanel rotateRadioPanel;
    private JPanel lengthRadioPanel;
    private JPanel amountRadioPanel;
    private JPanel buttonPanel;
    private RotateStep currentStep;
    /**
     * Initializes the RotateView by creating and laying out its child components.
     */
    public RotateView() {
        initializeComponents();
        initializeLayout();
    }

    /**
     * Generates a NewExampleRequest to be sent to the tutor based on the 
     * conditions selected by the user when newRequest() is called.
     * @return The NewExampleRequest object to be sent to the tutor
     */
    @Override
    public NewExampleRequest newRequest(){
       NewExampleRequest ex = new NewExampleRequest();
       
       //Set example type to the problem associated with the current view
       ex.setExampleType(ExampleType.ROTATE_BITS);
       
       //Create a new RotateStep object that will communicate the desired
       //conditions (direction of rotation and length of problem) to the tutor
       RotateStep newStep = new RotateStep();
       
       if(shortProblem.isSelected()) {
          newStep.setLength(16);
       }
       else {
          newStep.setLength(32);
       }
       
       if(rightRotate.isSelected()) {
          newStep.setDirection(RotateStep.Direction.RIGHT);
       }
       else {
          newStep.setDirection(RotateStep.Direction.LEFT);
       }
       
       if(rotate7Bits.isSelected()) {
           newStep.setAmount(7);
       }
       else {
           newStep.setAmount(16);
       }
       ShaTuTutor tutor = new ShaTuTutor();
       String inputString = tutor.getGeneratedInputString(newStep.getLength());
       //Set the data of the NewExampleRequest to the new RotateStep containing
       //the desired conditions
       newStep.setData(inputString);
       String rotateStepJson = gson.toJson(newStep);

       // Set the serialized JSON string in the NewExampleRequest
       ex.setData(rotateStepJson);
       
       return ex;
    }
    
    @Override
    public StepCompletion stepCompletion() {
        Step currentStep = model.currentTask().currentStep();
        
        RotateStep example = gson.fromJson(currentStep.getData(), RotateStep.class);
        
        String userResponse = answerField.getText().replaceAll("\\s", "");
        
        example.setData(userResponse);
        
        StepCompletion step = new StepCompletion(currentStep, gson.toJson(example));
        step.setStep(currentStep);
        return step;
    }
    
    /**
     * Creates and initializes the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        //Prompt and Problem labels should be populated by the result of 
        //a tutor reply for a new example. Done by pressing the New Example
        //button and on selection of the Panel
        prompt = new JLabel("Default Prompt Text");
        problem = new JLabel("Default Problem Text");
        
        answerField = new JTextField(10);
        answerField.addKeyListener(this);
        answerField.setHorizontalAlignment(JTextField.CENTER);

        checkButton = new JButton("Check");
        checkButton.addActionListener(this);

        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);
        
        newExampleButton = new JButton(NewExampleAction.instance());
        newExampleButton.setToolTipText("Generate New Example Problem");
        newExampleButton.addActionListener(this);
        
        shortProblem = new JRadioButton("16-bit");
        shortProblem.setSelected(true);
        longProblem = new JRadioButton("32-bit");
        
        rightRotate = new JRadioButton("Right Rotation");
        rightRotate.setSelected(true);
        leftRotate = new JRadioButton("Left Rotation");
        
        rotate7Bits = new JRadioButton("Rotate 7 bits");
        rotate7Bits.setSelected(true);
        rotate16Bits = new JRadioButton("Rotate 16 bits");
        
        lengthRadioGrp = new ButtonGroup();
        lengthRadioGrp.add(shortProblem);
        lengthRadioGrp.add(longProblem);
        
        rotateRadioGrp = new ButtonGroup();
        rotateRadioGrp.add(rightRotate);
        rotateRadioGrp.add(leftRotate);
        
        amountRadioGrp = new ButtonGroup();
        amountRadioGrp.add(rotate7Bits);
        amountRadioGrp.add(rotate16Bits);
    }
    
    /**
     * Lays out the child components in this view using GridBagConstraints.
     */
    private void initializeLayout() {
        lengthRadioPanel = new JPanel(new GridLayout(2, 1));
        lengthRadioPanel.add(shortProblem);
        lengthRadioPanel.add(longProblem);
        
        rotateRadioPanel = new JPanel(new GridLayout(2, 1));
        rotateRadioPanel.add(rightRotate);
        rotateRadioPanel.add(leftRotate);
        
        amountRadioPanel = new JPanel(new GridLayout(2, 1));
        amountRadioPanel.add(rotate7Bits);
        amountRadioPanel.add(rotate16Bits);
        
        mainRadioPanel = new JPanel(new GridLayout(1, 3));
        mainRadioPanel.add(lengthRadioPanel);
        mainRadioPanel.add(rotateRadioPanel);
        mainRadioPanel.add(amountRadioPanel);
        
        buttonPanel = createButtonPanel();
        
        GridBagConstraints c = new GridBagConstraints();
        // Prompt and problem labels
        addc(prompt, 0, 0, 2, 1, 0.2, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            5, 5, 5, 5);

        addc(problem, 0, 1, 2, 1, 0.2, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            5, 5, 5, 5);
        // Answer field
        addc(answerField, 0, 2, 2, 1, 1.0, 0.0, 
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            5, 5, 5, 5);
        addc(buttonPanel, 0, 3, 3, 1, 1.0, 1.0, 
            GridBagConstraints.CENTER, GridBagConstraints.NONE, 
            10, 0, 0, 0);
        // Radio buttons section
        addc(mainRadioPanel, 0, 4, 1, 1, 0.0, 0.0, 
             GridBagConstraints.WEST, GridBagConstraints.NONE, 
             5, 5, 5, 5);
    }
    
   
   private JPanel createButtonPanel(){
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(checkButton);
        buttonPanel.add(hintButton);
        buttonPanel.add(newExampleButton);
       return buttonPanel;
   }
   
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == checkButton) {
            onCheckButton();
        } else if (event.getSource() == hintButton) {
            onNextHint();
        } else if (event.getSource() == newExampleButton){
            NewExampleRequest example = newRequest();
            System.out.println(example.getData());
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
            verifyAnswer(this.problemString, this.numRotations);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    /**
     * Performs rotation (ROR or ROL) on the given input string for the 
     * specified number of positions.
     *
     * @param input     The input string to rotate.
     * @param positions The number of positions for the rotation.
     * @return The rotated string.
     */
    protected String rotateString(String input, int positions) {
        String inputStr = input.replaceAll("\\s", "");
        if (inputStr == null || inputStr.isEmpty()) {
            return input;
        }

        int length = inputStr.length();
        positions = positions % length; // Ensure positions is within the string length

        System.out.println("Original string: " + inputStr);
        System.out.println("Positions to rotate: " + positions);

        if (positions < 0) {
            positions = length + positions; // Handle negative positions
        }

        // Perform the rotation
        if (currentStep == null) {
            System.out.println("currentStep is null!");
        } else {
            System.out.println("Direction: " + currentStep.getDirection());
        }

        if (currentStep.getDirection() == RotateStep.Direction.RIGHT) {
            // Right rotation
            String rotated = inputStr.substring(length - positions) + inputStr.substring(0, length - positions);
            System.out.println("Rotated (right): " + rotated);
            return rotated;
        } else {
            // Left rotation
            String rotated = inputStr.substring(positions) + inputStr.substring(0, positions);
            System.out.println("Rotated (left): " + rotated);
            return rotated;
        }
    }

    /**
     * Verifies the user's answer by comparing it with the correct rotated string.
     */
    private void verifyAnswer(String probString, int numRot) {
        System.out.println("Problem String: " + probString + "\n" + numRot + "\n");
        String correctAnswer = rotateString(probString, numRot);  // Use probString instead of problemString
        // Get the text from the answerField when the checkButton is clicked
        String userAnswer = answerField.getText().replaceAll("\\s", "");

        if (userAnswer.equals(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is: " + correctAnswer);
        }
    }

    /**
     * Displays a message dialog indicating the provision of a hint.
     */
    private void onNextHint() {
        JOptionPane.showMessageDialog(this, "Hint");
    }

    /**
     * Handles the click event of the check button, verifying the user's answer.
     */
    private void onCheckButton() {
        if (answerField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please provide an answer");
        } else {
            String prob = this.problemString;
            int rotations = this.numRotations;
            System.out.println("This problem string: " + problem + ", this numrotations: " + rotations + "\n");
            verifyAnswer(prob, rotations);
        }
    }
     /**
     * Update the view with the contents of a new step sent by the tutor
     */
    @Override
    protected void updateView() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Step step = model.currentTask().getCurrentStep();
        
        RotateStep example = gson.fromJson(step.getData(), RotateStep.class);

        this.currentStep = example;
        
        String problemData = currentStep.getData();

        if (rightRotate.isSelected()) {
            currentStep.setDirection(RotateStep.Direction.RIGHT);
        } else {
            currentStep.setDirection(RotateStep.Direction.LEFT);
        }

        if (currentStep.getDirection() == RotateStep.Direction.RIGHT) {
            prompt.setText("Perform ROR(" + currentStep.getAmount() + ") on the following String:");
        } else {
            prompt.setText("Perform ROL(" + currentStep.getAmount() + ") on the following String:");
        }

        // Get and set problem data
        if (problemData == null || problemData.isEmpty()) {
            prompt.setText("");
            problem.setText("Click 'New Example' when ready.");
        } else {
            // Set the problem label
            problem.setText(problemData);
            this.problemString = problemData;
            this.numRotations = currentStep.getAmount();
        }
    }
    
    @Override
    public void setCurrentTask(Task task) {
        this.model.addCurrentTask(task);
        updateView();
    }
}