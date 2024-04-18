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
import edu.regis.shatu.model.Task;
import static edu.regis.shatu.model.aol.ExampleType.ROTATE_BITS;
import edu.regis.shatu.model.aol.NewExampleRequest;
import edu.regis.shatu.model.aol.RotateStep;
import edu.regis.shatu.view.act.NewExampleAction;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;


/**
 * RotateView class represents the GUI view for rotating strings using ROTR (Right Rotate).
 * It extends GPanel and implements ActionListener and KeyListener interfaces.
 * <p>
 * The class provides a user interface for performing right rotations on strings and checking the results.
 * Inline comments have been added for better understanding of the code.
 *
 * @author rickb
 */
public class RotateView extends UserRequestView<RotateStep> implements ActionListener, KeyListener {

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
    private String answer;
    private JLabel prompt;
    private JLabel problem;
    private JTextField answerField;
    private JButton checkButton; // Add the check button
    private JButton hintButton;
    private JButton nextQuestionButton;
    private JButton newExampleButton;
    private JRadioButton shortProblem;
    private JRadioButton longProblem;
    private JRadioButton rightRotate;
    private JRadioButton leftRotate;
    private ButtonGroup lengthType;
    private ButtonGroup rotationType;
    private RotateStep currentStep;

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
            onCheckButton();
        } else if (event.getSource() == hintButton) {
            onNextHint();
        } else if (event.getSource() == nextQuestionButton) {
            onNextQuestion();
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
        
        //Prompt and Problem labels should be populated by the result of 
        //a tutor reply for a new example. Done by pressing the New Example
        //button and on selection of the Panel
        prompt = new JLabel("Default Prompt Text");
       
        problem = new JLabel("Default Problem Text");
        
        answerField = new JTextField(10);
        answerField.addKeyListener(this);
        answerField.setHorizontalAlignment(JTextField.CENTER);

        // Create and initialize the checkButton
        checkButton = new JButton("Check");
        checkButton.addActionListener(this); // Add an action listener for the check button

        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);

        nextQuestionButton = new JButton("Next Question");
        nextQuestionButton.addActionListener(this);
        
        newExampleButton = new JButton(NewExampleAction.instance());
        newExampleButton.setToolTipText("Generate New Example Problem");
        
        shortProblem = new JRadioButton("16-bit");
        shortProblem.setSelected(true);
        
        longProblem = new JRadioButton("32-bit");
        
        rightRotate = new JRadioButton("Right Rotation");
        rightRotate.setSelected(true);
        
        leftRotate = new JRadioButton("Left Rotation");
        
        lengthType = new ButtonGroup();
        lengthType.add(shortProblem);
        lengthType.add(longProblem);
        
        rotationType = new ButtonGroup();
        rotationType.add(rightRotate);
        rotationType.add(leftRotate);
        
    }

    /**
     * Lays out the child components in this view using GridBagConstraints.
     */
    private void initializeLayout() {

        GridBagConstraints c = new GridBagConstraints();
        
        

        // Add exampleInputLabel centered
        
        addc(prompt, 2, 0, 2, 1, 0.2, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        addc(problem, 2, 1, 2, 1, 0.2, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        // Add answerField to the layout, centered
        addc(answerField, 2, 2, 2, 1, 0.2, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        addc(checkButton, 2, 3, 2, 1, 0.2, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        addc(hintButton, 2, 4, 2, 1, 0.2, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);

        addc(nextQuestionButton, 7, 7, 2, 1, 0.0, 0.2,
                GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE,
                10, 5, 5, 5);
        
        addc(newExampleButton, 1, 5, 2, 1, 0.0, 0.2, 
              GridBagConstraints.WEST, GridBagConstraints.NONE,
              5, 5, 5, 5);
        
        addc(shortProblem, 0, 6, 1, 1, 0.0, 0.0, 
              GridBagConstraints.WEST, GridBagConstraints.NONE, 
              5, 5, 5, 5);
        
        addc(longProblem, 0, 7, 1, 1, 0.0, 0.0, 
              GridBagConstraints.WEST, GridBagConstraints.NONE, 
              5, 5, 5, 5);
        
        addc(rightRotate, 2, 6, 1, 1, 0.0, 0.0, 
              GridBagConstraints.WEST, GridBagConstraints.NONE, 
              5, 5, 5, 5);
        
        addc(leftRotate, 2, 7, 1, 1, 0.0, 0.0, 
              GridBagConstraints.WEST, GridBagConstraints.NONE, 
              5, 5, 5, 5);
                
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
        if (input == null || input.isEmpty()) {
            return input;
        }

        int length = input.length();
        positions = positions % length; // Ensure positions is within the string length

        if (positions < 0) {
            positions = length + positions; // Handle negative positions
        }

        // Perform the rotation
        // The rotated string is formed by concatenating two substrings:
        // 1. The substring starting from (length - positions) to the end of the string.
        // 2. The substring from the beginning of the string to (length - positions).
        if(currentStep.getDirection() == RotateStep.Direction.RIGHT){
           answer = input.substring(length - positions) + input.substring(0, length - positions);
        }
        else {
           answer = input.substring(positions) + input.substring(0, positions);
        }

        return answer;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Verifies the user's answer by comparing it with the correct rotated string.
     */
    private void verifyAnswer() {
        String correctAnswer = rotateString(problemString, numRotations);
        // Get the text from the answerField when the checkButton is clicked
        String userAnswer = answerField.getText();

        if (userAnswer.equals(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is: " + correctAnswer);
        }
    }

    /**
     * Generates a NewExampleRequest to be sent to the tutor based on the 
     * conditions selected by the user when newRequest() is called.
     * @return The NewExampleRequest object to be sent to the tutor
     */
    @Override
    public NewExampleRequest newRequest(){
       Gson gson = new GsonBuilder().setPrettyPrinting().create();
       
       NewExampleRequest ex = new NewExampleRequest();
       
       //Set example type to the problem associated with the current view
       ex.setExampleType(ROTATE_BITS);
       
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
       
       //Set the data of the NewExampleRequest to the new RotateStep containing
       //the desired conditions
       ex.setData(gson.toJson(newStep));
       
       return ex;
       
    }
    
    /**
     * Update the view with the contents of a new step sent by the tutor
     */
    @Override
    public void updateView(){
       Gson gson = new GsonBuilder().setPrettyPrinting().create();
       
       //Get the current step from the model
       Step step = model.currentStep();
               
       //Get the data from the model as a RotateStep object
       RotateStep newStep = gson.fromJson(step.getData(), RotateStep.class);
       
       //Conditionals to determine the problem displayed by the view
       if(newStep.getDirection() == RotateStep.Direction.RIGHT){
          prompt.setText("Perform ROR(" + newStep.getAmount() + ") on the following String:");
       }
       else {
          prompt.setText("Perform ROL(" + newStep.getAmount() + ") on the following String:");
       }
       
       //Update the problem JLabel with the new bit String to be rotated
       problem.setText(formatProblemString(newStep.getData()));
       
    }
    
    /**
     * Displays a message dialog indicating the start of the next question.
     */
    private void onNextQuestion() {
       // prompt = new JLabel("Perform ROTR(" + generateRotations() + ") on: " + generateProblems());
        JOptionPane.showMessageDialog(this, "Next Question");  
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
            verifyAnswer();
        }
    }
    

    
   
    /**
     * UI formatting to separate the problem string by nibble
     * @param problemString the unspaced problem string to be solved
     * @return the spaced problem string for user convenience
     */
   private String formatProblemString(String problemString){
      if(problemString.length() > 16){
         return problemString.substring(0, 4) + ' ' + 
               problemString.substring(4, 8) + ' ' + 
               problemString.substring(8, 12) + ' ' + 
               problemString.substring(12, 16) + ' ' + 
               problemString.substring(16, 20) + ' ' + 
               problemString.substring(20, 24) + ' ' + 
               problemString.substring(24, 28) + ' ' + 
               problemString.substring(28);
      }
      else{
         return problemString.substring(0, 4) + ' ' + 
               problemString.substring(4, 8) + ' ' + 
               problemString.substring(8, 12) + ' ' + 
               problemString.substring(12);
      }
   }
   
   
   
}
