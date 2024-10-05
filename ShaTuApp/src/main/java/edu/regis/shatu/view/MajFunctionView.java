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
import java.util.Random;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class represents the GUI for the Majority (Maj) function exercise.
 * Given three 𝑛-bit binary numbers, the user is asked to output the value of the Majority (Maj) function.
 * <p>
 * The ASCII character the student is being asked to convert.
 * <p>
 * Binary numbers used for the exercise:
 * - Binary number 1: 101100
 * - Binary number 2: 011011
 * - Binary number 3: 110011
 * <p>
 * The user can input their answer and check it against the correct result.
 * Additionally, hints and next questions are available to guide the user.
 * <p>
 * Inline comments have been added throughout the code to explain specific sections and methods.
 *
 * @author rickb
 */
public class MajFunctionView extends UserRequestView implements ActionListener, KeyListener {
    private String stringX, stringY, stringZ;
    private int problemSize; 
    private JTextArea descTextArea, feedbackTextArea, responseTextArea;
    private JScrollPane feedbackPane, responsePane, majTruthTablePane;
    private GPanel truthTablePanel, questionPanel, descriptionPanel, qrPanel;
    private JPanel buttonPanel, radioButtonPanel; 
    private JTable majTruthTable;
    private JButton checkButton, nextButton, hintButton;
    private ButtonGroup problemSizeGroup;
    private JRadioButton fourRadioButton, eightRadioButton, sixteenRadioButton, 
                         thirtytwoRadioButton;
    private JLabel viewNameLabel, truthTableLabel, majFunctionLabel, 
                   stringXLabel, stringYLabel, stringZLabel, answerLabel, 
                   problemSizeLabel, instructionLabel;
    
    private static final Random random = new Random();

    /**
     * Initialize this view including creating and laying out its child components.
     */
    public MajFunctionView() {
        initializeComponents();
        initializeLayout();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == checkButton) {
            onCheckButton();
        } else if (event.getSource() == hintButton) {
            onNextHint();
        } else if (event.getSource() == nextButton) {
            onNextQuestion();
        }
    }
    
    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        setUpDescription();
        setUpRadioButtons();
        setUpQuestionArea();
        setUpResponseArea();
        setUpFeedbackArea();
        setUpButtons();
        setUpTruthTable();
        setUpDescriptionPanel();
        setUpQRPanel();   
    }

    /**
     * Layout the child components in this view.
    */ 
    private void initializeLayout() { 
        addc(descriptionPanel, 0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
      
        addc(answerLabel, 0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);
        
        addc(qrPanel, 0, 2, 3, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
    }
    
    /**
     * Sets up the description area
     */
    private void setUpDescription() {
        viewNameLabel = new JLabel("The Majority Function");
        viewNameLabel.setFont(new Font("", Font.BOLD, 20));
        
        descTextArea = new JTextArea();
        descTextArea.setEditable(false);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setOpaque(false);
        descTextArea.append("""
                            The Majority function takes three 32-bit words as input and outputs one 32-bit word. When comparing the three inputs,
                            this function outputs the bit that shows up the most between x, y, and z."""); //Works for now. Describe better later    
    }
    
    /**
     * Creates the description panel
     */
    private void setUpDescriptionPanel() {
        descriptionPanel = new GPanel();

        descriptionPanel.addc(viewNameLabel, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);

        descriptionPanel.addc(descTextArea, 0, 1, 3, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        descriptionPanel.addc(questionPanel, 0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
       
        descriptionPanel.addc(truthTablePanel, 1, 2, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);
    }
    
    /**
     * Sets up the radio buttons and action listener
     */
    private void setUpRadioButtons() {
        fourRadioButton = new JRadioButton("4 bits");
        eightRadioButton = new JRadioButton("8 bits");
        sixteenRadioButton = new JRadioButton("16 bits");
        thirtytwoRadioButton = new JRadioButton("32 bits");
        
        ActionListener selection = e -> {
            JRadioButton source = (JRadioButton) e.getSource();
            updateProblemSize(source);
            generateNewQuestion();
        };
        
        fourRadioButton.addActionListener(selection);
        eightRadioButton.addActionListener(selection);
        sixteenRadioButton.addActionListener(selection);
        thirtytwoRadioButton.addActionListener(selection);
        
        problemSizeGroup = new ButtonGroup();
        problemSizeGroup.add(fourRadioButton);
        problemSizeGroup.add(eightRadioButton);
        problemSizeGroup.add(sixteenRadioButton);
        problemSizeGroup.add(thirtytwoRadioButton);
        
        fourRadioButton.setSelected(true); //Set default radio button to true
        
        radioButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        radioButtonPanel.add(fourRadioButton);
        radioButtonPanel.add(eightRadioButton);
        radioButtonPanel.add(sixteenRadioButton);
        radioButtonPanel.add(thirtytwoRadioButton);    
    }
    
    /**
     * Updates the size of the problem to display.
     * 
     * @param source The radio button that triggered the even.
     */
    private void updateProblemSize(JRadioButton source){
        if (source == fourRadioButton) {
            problemSize = 4;
        } else if (source == eightRadioButton) {
            problemSize = 8;
        } else if (source == sixteenRadioButton) {
            problemSize = 16;
        } else if (source == thirtytwoRadioButton){
            problemSize = 32;
        }
    }
    
    /**
     * Initializes the question components and adds them to the question panel. 
     */
    private void setUpQuestionArea() {
        problemSize = 4;
        stringX = generateInputString();
        stringY = generateInputString();
        stringZ = generateInputString();
        
        stringXLabel = new JLabel("x: " + stringX);
        stringYLabel = new JLabel("y: " + stringY);
        stringZLabel = new JLabel("z: " + stringZ);
        
        problemSizeLabel = new JLabel("Select Problem Size:");
        instructionLabel = new JLabel("Solve the majority function using the three "
                + "inputs given below:");
        
        questionPanel = new GPanel();
        
        questionPanel.addc(problemSizeLabel, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
        questionPanel.addc(radioButtonPanel, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        questionPanel.addc(instructionLabel, 0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
        questionPanel.addc(stringXLabel, 0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        questionPanel.addc(stringYLabel, 0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        questionPanel.addc(stringZLabel, 0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
    }
    
    /**
     * Initializes the response area
     */
    private void setUpResponseArea() {
        answerLabel = new JLabel("Enter your answer below:");
        responseTextArea = new JTextArea(3, 20);
        responseTextArea.setLineWrap(true);
        responseTextArea.setWrapStyleWord(true);
        
        responsePane = new JScrollPane(responseTextArea);
        responsePane.setPreferredSize(new Dimension(800, 200));
    }
    
     /**
     * Initialized the feedback area
     */
    private void setUpFeedbackArea() {
        feedbackTextArea = new JTextArea(3, 20);
        feedbackTextArea.setEditable(false);
        feedbackTextArea.setLineWrap(true);
        feedbackTextArea.setWrapStyleWord(true);
        feedbackTextArea.setBackground(null);
        
        feedbackPane = new JScrollPane(feedbackTextArea);
        feedbackPane.setPreferredSize(new Dimension(800, 200));
    }
    
    /**
     * Sets up the Check, Next, and Hint buttons and their action listeners
     */
    private void setUpButtons() {
        checkButton = new JButton("Check");
        checkButton.addActionListener(this);
        
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);
        
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(checkButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(hintButton);   
    }
    
    /**
     * Creates a GPanel containing the response and feedback JScrollPanes and 
     * the button panel. 
     */
    private void setUpQRPanel(){ //Rename function (frPanel?)
        qrPanel = new GPanel();
        
        qrPanel.addc(responsePane, 0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);
        
        qrPanel.addc(feedbackPane, 0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);

        qrPanel.addc(buttonPanel, 0, 2, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
    }
    
    /**
     * Sets up the truth table associated with the Majority Function.
     */
    private void setUpTruthTable() {

        truthTableLabel = new JLabel("Maj Function Truth Table");
        truthTableLabel.setFont(new Font("", Font.BOLD, 14));
        majFunctionLabel = new JLabel("𝑀𝑎𝑗(𝑥,𝑦,𝑧)=(𝑥∧𝑦)⨁(𝑥∧𝑧)⨁(𝑦∧𝑧)");
        
        Object[] columnNames = {"x", "y", "z", "(𝑥∧𝑦)", "(𝑥∧𝑧)", "(𝑦∧𝑧)", "(𝑥∧𝑦)⨁(𝑥∧𝑧)⨁(𝑦∧𝑧)"};
        Object[][] data = {{0, 0, 0, 0, 0, 0, 0}, 
                           {0, 0, 1, 0, 0, 0, 0}, 
                           {0, 1, 0, 0, 0, 0, 0},
                           {0, 1, 1, 0, 0, 1, 1}, 
                           {1, 0, 0, 0, 0, 0, 0}, 
                           {1, 0, 1, 0, 1, 0, 1}, 
                           {1, 1, 0, 1, 0, 0, 1}, 
                           {1, 1, 1, 1, 1, 1, 1}};
        
        majTruthTable = new JTable(data, columnNames);
        configureChTruthTable();
        
        majTruthTablePane = new JScrollPane(majTruthTable);
        majTruthTablePane.setPreferredSize(new Dimension(400, 151));
        majTruthTablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        majTruthTablePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        truthTablePanel = new GPanel(); //Separate GPanel info to new function
        
        truthTablePanel.addc(truthTableLabel, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        truthTablePanel.addc(majFunctionLabel, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        truthTablePanel.addc(majTruthTablePane, 0, 2, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                5, 5, 5, 5);
    }
    
    /**
     * Configures the appearance of the truth table.
     */
    private void configureChTruthTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int columnIndex = 0; columnIndex < majTruthTable.getColumnCount(); columnIndex++) {
            majTruthTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }
        majTruthTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        majTruthTable.getColumnModel().getColumn(1).setPreferredWidth(25);
        majTruthTable.getColumnModel().getColumn(2).setPreferredWidth(25);
        majTruthTable.getColumnModel().getColumn(6).setPreferredWidth(130);
    }
    
     /**
     * Generates an n-bit binary string (length 4, 8, 16, or 32) to be used as an input into the 
     * Maj function. Every four bits are separated by a space to improve readability.
     * 
     * @return A string to be used as an input into the function.
     */
    private String generateInputString() { //Try to find a better way to do this?
        String inputString;
        String tempString;
        StringBuilder inputStringBuilder = new StringBuilder();
        int num;

        switch (problemSize) {
            case 4:
                num = random.nextInt();
                tempString = String.format("%4s", Integer.toBinaryString(num & 0xF)).replace(' ', '0');
                inputStringBuilder.append(tempString);
                break;
            case 8:
                num = random.nextInt();
                tempString = String.format("%4s", Integer.toBinaryString(num & 0xF)).replace(' ', '0');
                inputStringBuilder.append(tempString);
                
                inputStringBuilder.append(" ");
                num = random.nextInt();
                tempString = String.format("%4s", Integer.toBinaryString(num & 0xF)).replace(' ', '0');
                inputStringBuilder.append(tempString);
                break;
            case 16:
                num = random.nextInt();
                tempString = String.format("%4s", Integer.toBinaryString(num & 0xF)).replace(' ', '0');
                inputStringBuilder.append(tempString);
                
                for (int i = 0; i < 3; i++) {
                    inputStringBuilder.append(" ");
                    num = random.nextInt();
                    tempString = String.format("%4s", Integer.toBinaryString(num & 0xF)).replace(' ', '0');
                    inputStringBuilder.append(tempString);
                }   break;
            case 32:
                num = random.nextInt();
                tempString = String.format("%4s", Integer.toBinaryString(num & 0xF)).replace(' ', '0');
                inputStringBuilder.append(tempString);
                
                for (int i = 0; i < 7; i++) {
                    inputStringBuilder.append(" ");
                    num = random.nextInt();
                    tempString = String.format("%4s", Integer.toBinaryString(num & 0xF)).replace(' ', '0');
                    inputStringBuilder.append(tempString);
                }   break;
            default:
                break;
        }
        
        inputString = inputStringBuilder.toString();
        
        return inputString;
    }
    
    /**
     * Formats the result output by the choice function based on the size of the 
     * problem.
     * @param answer the output of the choice function
     * 
     * @return the binary string representation of the answer
     */
    private String formatResult(long answer) {
        String finalResult = "";
        
        switch (problemSize) {
            case 4: 
                finalResult = String.format("%4s", Long.toBinaryString(answer)).replace(' ', '0');
                break;
            case 8:
                finalResult = String.format("%8s", Long.toBinaryString(answer)).replace(' ', '0');
                break;
            case 16:
                finalResult = String.format("%16s", Long.toBinaryString(answer)).replace(' ', '0');
                break;
            case 32:
                finalResult = String.format("%32s", Long.toBinaryString(answer)).replace(' ', '0');   
                break;
            default:
                break;
        }
        return finalResult;
    }
    
    /**
     * Generates and displays three new input strings.
     */
    private void generateNewQuestion() {   
        responseTextArea.setText("");
        feedbackTextArea.setText("");
        
        stringX = generateInputString();
        stringY = generateInputString();
        stringZ = generateInputString();
        
        stringXLabel.setText("x: " + stringX);
        stringYLabel.setText("y: " + stringY);
        stringZLabel.setText("z: " + stringZ);
    }
    
    /**
     * Evaluates the maj function maj(x, y, z).
     *
     * @param x Binary string representation of x.
     * @param y Binary string representation of y.
     * @param z Binary string representation of z.
     * @return Binary string result of maj(x, y, z).
     */
    private String majorityFunction(String x, String y, String z) {
        // Convert the binary strings to integer values
        String tempX = x.replaceAll("\\s", "");
        String tempY = y.replaceAll("\\s", "");
        String tempZ = z.replaceAll("\\s", "");
               
        long intX = Long.parseLong(tempX, 2);
        long intY = Long.parseLong(tempY, 2);
        long intZ = Long.parseLong(tempZ, 2);

        long xy = intX & intY;

        long xz = intX & intZ;
        
        long yz = intY & intZ;

        long result = xy ^ xz ^ yz;

        // Convert the result back to binary string
        String binaryResult = formatResult(result);

        return binaryResult;
    }

    /**
     * Verifies the user's answer against the correct result and shows a message dialog.
     */
    private void verifyAnswer() {
        String correctAnswer = majorityFunction(stringX, stringY, stringZ);
        String userResponse = responseTextArea.getText();
        
        userResponse = userResponse.replaceAll("\\s", "");
        
        if (correctAnswer.equals(userResponse)) {
            feedbackTextArea.setText("Correct!");
            nextButton.setEnabled(true);
            checkButton.setEnabled(false);
        } else {
            feedbackTextArea.setText("Incorrect! Please check your entry and "
                    + "try again or use the hint feature for help. Correct answer: " + correctAnswer);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && responseTextArea.getText().equals("")) {
            feedbackTextArea.setText("Please provide an answer");
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verifyAnswer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Displays a message dialog indicating the start of the next question.
     */
    private void onNextQuestion() {
        responseTextArea.setText("");
        feedbackTextArea.setText("");
        
        generateNewQuestion();
        
        nextButton.setEnabled(false);
        checkButton.setEnabled(true);
    }

    /**
     * Displays a message dialog indicating the provision of a hint.
    */
    private void onNextHint() {
        feedbackTextArea.setText("Hint: Check the truth table above for the "
                + "appropriate values.");
    }

    /**
     * Handles the click event of the check button, verifying the user's answer.
    */
    private void onCheckButton() {
        if (responseTextArea.getText().equals("")) {
            feedbackTextArea.setText("Please provide an answer");
        } else {
            verifyAnswer();
        }
    }
    
    @Override
    /**
     * Updates the description, question, and hints from the model
     * 
     * TODO: THIS IS A PLACEHOLDER UNTIl WE HAVE HAVE THE MODEL CODE COMPLETED
     */
    protected void updateView() {
        if (model != null) {
            // ****TO-DO*****
            // Update the view's information from the model
            // Debugging dynamic updates to the model can be done here.
            System.out.println("MajFunctionView");
        }
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
