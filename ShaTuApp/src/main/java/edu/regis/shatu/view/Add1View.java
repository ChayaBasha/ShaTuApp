
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.regis.shatu.model.AddOneStep;
import edu.regis.shatu.model.Hint;
import edu.regis.shatu.model.Step;
import edu.regis.shatu.model.StepCompletion;
import edu.regis.shatu.model.Task;
import edu.regis.shatu.svc.ClientRequest;
import edu.regis.shatu.svc.ServerRequestType;
import java.util.ArrayList;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.model.aol.ExampleType;
import edu.regis.shatu.model.aol.NewExampleRequest;
import edu.regis.shatu.view.act.NewExampleAction;
import edu.regis.shatu.view.act.StepCompletionAction;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A view that requests the student to add a single '1' bit to the byte prompt.
 * 
 * @author rickb
 */
public class Add1View extends UserRequestView implements ActionListener {
    /**
     * The part of the tutoring session model that is displayed in this view.
     */
    private AddOneStep stepDataModel;
    
    /**
     * The source input string to which one bit is to be added.
     */
    private JLabel source;
    
    /**
     * The number of hint requests made by the student.
     */
    private int hintsRequested;  
    
    private JTextPane descriptionTextPane;
    private JLabel questionLabel, instructionsLabel, messageLengthLabel;
    private JTextField messageLengthField;
    private JTextArea responseArea;
    private JTextArea feedbackArea;
    //private JPanel buttonPanel;
    private JButton checkButton, nextButton, hintButton;
    private JTable asciiTable;
    private JScrollPane responseScrollPane, asciiTableScrollPane, feedbackScrollPane;
    private String question;

    private boolean wasHintRequested = false;
    // For random character generation
    private static final Random random = new Random();
    
    /**
     * Utility reference used to convert between Java and JSon. 
     */
    private Gson gson;
    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public Add1View() { 
        gson = new GsonBuilder().setPrettyPrinting().create();
        
        initializeComponents();
        initializeLayout();
        //prepareNextQuestion(); Blocked during sprint 1
    }
    
    /**
     * Responds to actions performed in the view, specifically button presses,
     * and delegates to appropriate methods for handling.
     * 
     * @param event the event that triggered the action listener
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == checkButton) {
            submitAnswer();           
        } else if (event.getSource() == nextButton) {
            prepareNextQuestion();
        } else if (event.getSource() == hintButton) {
            requestHint();
        }
    }

    /**
     * Initializes all GUI components, setting up their properties and configurations.
     */
    private void initializeComponents() {  
        setupDescriptionSection();
        setupQuestionLabel();
        setupInstructionLabel();
        setupMessageLengthInput();
        setupResponseArea();
        setupFeedbackArea();
        setupButtons();
        setupAsciiTable();
    }
    
    /**
     * Lays out the initialized components on the panel using GridBagLayout 
     * constraints.
     */
    private void initializeLayout() {
        
        JPanel buttonPanel = createButtonPanel();  
        JPanel messageLengthPanel = createMessageLengthPanel();

        // Add components to the layout
        addc(descriptionTextPane, 0, 0, 1, 1, 
                1.0, 0.0, GridBagConstraints.CENTER, 
                GridBagConstraints.HORIZONTAL, 5, 5, 5, 5);
        addc(messageLengthPanel, 0, 1, 1, 1, 
                1.0, 0.0, GridBagConstraints.CENTER, 
                GridBagConstraints.NONE, 5, 5, 5, 5);        
        addc(questionLabel, 0, 2, 1, 1, 
                1.0, 0.0, GridBagConstraints.CENTER, 
                GridBagConstraints.HORIZONTAL, 5, 5, 5, 5);
         addc(instructionsLabel, 0, 3, 1, 1, 
                1.0, 0.0, GridBagConstraints.CENTER, 
                GridBagConstraints.HORIZONTAL, 5, 5, 5, 5);
        addc(responseScrollPane, 0, 4, 1, 1, 
                1.0, 1.0, GridBagConstraints.CENTER, 
                GridBagConstraints.BOTH, 5, 5, 5, 5);
        addc(feedbackScrollPane, 0, 5, 1, 1, 
                1.0, 1.0, GridBagConstraints.CENTER, 
                GridBagConstraints.BOTH, 5, 5, 5, 5);
        addc(buttonPanel, 0, 6, 1, 1, 
                1.0, 1.0, GridBagConstraints.CENTER, 
                GridBagConstraints.NONE, 10, 0, 0, 0);

        /* Test Test Test sprint 1 in attempt to only display when needed.
        addc(asciiTableScrollPane, 3, 0, GridBagConstraints.REMAINDER,
                7, 2.0, 1.0, GridBagConstraints.CENTER, 
                GridBagConstraints.BOTH, 5, 5, 5, 5);*/

    }  
    
    /**
     * Submit the student's answer to the tutor.
     */
    public void submitAnswer() {
        // SOME OF THE CODE TO USE ONCE MODEL IS SETUP PROPERLY AND PROPOGATED DOWN
        //ClientRequest request = new ClientRequest(ServerRequestType.COMPLETED_STEP); 
        //request.setUserId(model.getAccount().getUserId());
        //request.setSessionId(model.getSecurityToken());    
        //request.setData(gson.toJson(stepDataModel));        
        //GuiController.instance().tutorRequest(request);
  
        // TEMPORARY UNTIL MODEL IS PROPOGATED DOWN 
        // Trim user input and split it into an array based on spaces.
        /* Original code blocked out during sprint 1
        String userInput = responseArea.getText().trim();
        String expectedAnswer = convertMessageToBinaryWithSpaces(question);        
        
        // Check if the number of user entries matches the number of expected answers.
        if (userInput.length() != expectedAnswer.length()) {
            feedbackArea.setText("Incorrect number of entries. Please ensure your answer matches the expected format.");
        } else {                                    
            // If all user entries matched the expected answers, provide positive feedback.
            if (userInput.equals(expectedAnswer)) {
                feedbackArea.setText("Correct!");
            } else {
                // Otherwise, inform the user that their entries were incorrect, and display the expected answers.
                feedbackArea.setText(String.format("Incorrect. Please check "
                        + "your entries. Expected: \n%s", expectedAnswer));
            }
        }
        
        // Disable the submit button to prevent re-submission, and enable the next question button.
        checkButton.setEnabled(false);
        hintButton.setEnabled(false);
        nextButton.setEnabled(true);*/
        
        if (this.responseArea.getText().equals("")) {
            this.feedbackArea.setText("Please provide an answer");
        }
        else {
            //do nothing right now (sprint 1, choice func has verifyAnswer(), but its empty)
        }
    }
    
    /**
     * Prepares the view for the next question by clearing previous inputs
     * and feedback and generating a new question.
     * 
     * THIS NEEDS UPDATED ONCE MODEL IS CONFIGURED AND EXAMPLE IS COMPLETED
     */
    private void prepareNextQuestion() {
        /* Code blocked during sprint 1
        // Clear any existing feedback and response from the previous question.
        feedbackArea.setText("");
        responseArea.setText("");
        
        try {      
            // Parse the desired message length from the input field.
            int messageLength = Integer.parseInt(messageLengthField.getText().trim());
            question = generateRandomString(messageLength);
                        
            // Update the question label with the new question.
            questionLabel.setText(String.format("Convert the following "
                    + "string to binary and append a 1 bit to it: %s", question));
            
            // Enable the buttons, ready for the user's response.
            // DO WE WANT TO DISABLE NEXT BUTTON ONCE MODEL COMPLETED AND LEARNING
            // AI GENERATES THE QUESTIONS?
            checkButton.setEnabled(true);
            hintButton.setEnabled(true);
            nextButton.setEnabled(true);
        } catch (NumberFormatException e) {
            // If the message length input is not a valid number, inform the user.
            feedbackArea.setText("Please enter a valid message length.");
        }*/
    }
    
    /**
     * Process the students request for a hint.
     * 
     * ***NEED TO REVIEW THIS AND THINK ABOUT IT.
     */
    public void requestHint() {
        /*String hintText =""; // Code blocked during sprint 1
        
        try {
        Task task = model.currentTask();
        Step step = task.currentStep();
        
        ArrayList<Hint> hints = step.getHints();
        int maxHints = hints.size();
        
        if (hintsRequested < maxHints) {
            hintText = hints.get(hintsRequested).getText();
        } else {
            hintText = "Sorry, there are no additional hints for this step.";
        } 
        
        hintsRequested++;
        
        ClientRequest request = new ClientRequest(ServerRequestType.REQUEST_HINT); 
        request.setUserId(model.getAccount().getUserId());
        request.setSessionId(model.getSecurityToken());
        request.setData(":HintGiven");
        
        GuiController.instance().tutorRequest(request);
        
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            e.printStackTrace();
            // Consider logging the error or taking corrective action
        }
        
        // TEMPORARY UNTIL WE LOAD THE HINT FROM THE MODEL
        if (hintText.isEmpty()) {
        feedbackArea.setText("Hint: Check the ASCII table to the right for "
            + "the appropriate representation.");
<<<<<<< HEAD
        }
=======
        }*/
        
        this.feedbackArea.setText("Hint: Check the ASCII Table to the right for guidance.");
        
        if (!this.isAncestorOf(asciiTableScrollPane)) {
            addc(asciiTableScrollPane, 3, 0, GridBagConstraints.REMAINDER,
                    7, 2.0, 1.0, GridBagConstraints.CENTER, 
                    GridBagConstraints.BOTH, 5, 5, 5, 5);
            
            this.wasHintRequested = true;
        }
        
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Sets up the description section of the view, explaining the purpose of 
     * the encoding exercise.
     */
    private void setupDescriptionSection() {
        descriptionTextPane = new JTextPane();
        descriptionTextPane.setContentType("text/html");
        
        // TEMPORARY UNTIL WE LOAD THE MODEL DATA DESCRIPTION
        descriptionTextPane.setText(
                    "<html>" +
                    "<body>" +
                    "<h2>Appending the '1' Bit</h2>" +
                    "<p>The second step in SHA-256 preprocessing is appending a "
                            + "single '1' bit to the end of the original message"
                            + " in binary form. This step is crucial as it marks"
                            + " the boundary between the original message and the"
                            + " padding that follows. The '1' bit is added"
                            + " immediately after the last character of the message,"
                            + " before any zero padding. This ensures that the "
                            + " padded message remains unique and distinguishable"
                            + " from the original. Please use the format: "
                            + "######## # or if message length is two: "
                            + "######## ######## # and keep going depending on "
                            + "the message length. The single # would be "
                            + "the 1 you are suppose to add.</p>" +
                    "</body>" +
                    "</html>"
            );
        descriptionTextPane.setEditable(false);
        descriptionTextPane.setBackground(null);
        descriptionTextPane.setBorder(null);
    }
    
    /**
     * Initializes the question label
     */
    private void setupQuestionLabel() {
        question = "";
        questionLabel = new JLabel("");
    }
    
    /**
     * Initializes the response area and its scroll pane
     */
    private void setupResponseArea() {
        responseArea = new JTextArea(3, 20);
        responseArea.setLineWrap(true); // Enable line wrapping
        responseArea.setWrapStyleWord(true); // Wrap lines at word boundaries
        responseScrollPane = new JScrollPane(responseArea);
        responseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Enable vertical scrolling
    }

    /**
     * Initializes the feedback area and its scroll pane
     */
    private void setupFeedbackArea() {
        feedbackArea = new JTextArea(3, 20);
        feedbackArea.setEditable(false);
        feedbackArea.setBackground(null);
        feedbackArea.setLineWrap(true); // Enable line wrapping
        feedbackArea.setWrapStyleWord(true); // Wrap lines at word boundaries
        feedbackScrollPane = new JScrollPane(feedbackArea);
        feedbackScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Enable vertical scrolling
    }
    
    /**
     * Initializes the submit, next, and hint buttons and sets up action listeners
     * 
     */
    private void setupButtons() {
        checkButton = new JButton(StepCompletionAction.instance());
        checkButton.addActionListener(this);

        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);

        nextButton = new JButton(NewExampleAction.instance());
        nextButton.addActionListener(this);
/* choice uses this, but its implemented below
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(checkButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(hintButton);*/

        /*original code
        submitButton = new JButton("Submit");
        nextButton = new JButton("Next");
        hintButton = new JButton("Hint");
        submitButton.addActionListener(this);
        nextButton.addActionListener(this);
        hintButton.addActionListener(this);
        nextButton.setEnabled(true);
        */
    }
    
    /**
     * Creates and returns a JPanel containing the action buttons with a FlowLayout
     * @return JPanel containing the action buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(checkButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(hintButton);
        return buttonPanel;
    }
    
    /**
     * Initializes the components for inputting the message length. This method creates and configures
     * a JLabel and a JTextField where users can specify the length of the message they want to encode.
     * The JTextField is initialized with a default value of "1" and is set to align text centrally.
     */
    private void setupMessageLengthInput() {
        messageLengthLabel = new JLabel("Message Length:");
        messageLengthField = new JTextField("1", 5);  // Default length 1, adjust size as needed
        messageLengthField.setHorizontalAlignment(JTextField.CENTER);
    }
    
    /**
     * Creates and returns a JPanel dedicated to setting the message length. 
     *
     * @return A JPanel containing components for message length input, arranged vertically.
     */
    private JPanel createMessageLengthPanel() {
        JPanel messageLengthPanel = new JPanel();
        messageLengthPanel.add(messageLengthLabel);
        messageLengthPanel.add(messageLengthField);
        messageLengthPanel.setLayout(new BoxLayout(messageLengthPanel, BoxLayout.Y_AXIS));
        return messageLengthPanel;
    }
    
    private void setupInstructionLabel() {
        instructionsLabel = new JLabel("Please separate your entries with spaces.");
        instructionsLabel.setHorizontalAlignment(JLabel.CENTER);
    }
    
    /**
    * Converts a string message into its binary representation with spaces and
    * adds a 1 bit.
    * Each character in the message is represented by its corresponding 8-bit 
    * binary ASCII value separated by spaces with the addition of a 1 bit.
    *
    * @param message The text message to be converted.
    * @return The binary representation of the message with spaces.
    */
    public String convertMessageToBinaryWithSpaces(String message) {
        StringBuilder binaryStringBuilder = new StringBuilder();

        for (char character : message.toCharArray()) {
            String binaryString = String.format("%8s", Integer.toBinaryString(character))
                                    .replaceAll(" ", "0");
            if (binaryStringBuilder.length() > 0) {
                binaryStringBuilder.append(" ");
            }
            binaryStringBuilder.append(binaryString);
        }
        binaryStringBuilder.append(" 1");
        return binaryStringBuilder.toString();
    }

    /**
     * Generates and returns a random character from ASCII table values 
     * 
     * @return int the random integer to be returned
     */
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // Generates a random integer between 32 (inclusive) and 126 (inclusive)
            int randomChar = 32 + random.nextInt(95); // 126 - 32 + 1 = 95
            sb.append((char) randomChar);
        }

        return sb.toString();
    }
    
    /**
     * Initializes the ASCII table and its scroll pane
     */
    private void setupAsciiTable() {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Decimal", "Binary", "Symbol"}, 0);
        fillAsciiTable(tableModel); // Method to fill table data
        asciiTable = new JTable(tableModel);
        configureAsciiTable(); // Method to configure table appearance
        asciiTableScrollPane = new JScrollPane(asciiTable);
        asciiTableScrollPane.setPreferredSize(new Dimension(350, 400));
    }
    
    /**
     * Fills the ASCII table with the decimal, binary, hexadecimal and symbol representation
     * of printable ASCII characters
     * 
     * @param tableModel the filled ASCII table
     */
    private void fillAsciiTable(DefaultTableModel tableModel) {
        for (char i = 32; i < 127; i++) {
            tableModel.addRow(new Object[]{
                Integer.toString(i), // Decimal representation
                String.format("%8s", Integer.toBinaryString(i)).replaceAll(" ", "0"), // Binary representation
                i == 32 ? "<SPACE>" : String.valueOf(i) // Symbol representation, with special handling for the space character
            });
        }
    }
    
    /**
     * Configures the appearance of the ASCII table 
     */
    private void configureAsciiTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int columnIndex = 0; columnIndex < asciiTable.getColumnCount(); columnIndex++) {
            asciiTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }
    }
    
    /**
     * Sets the TutoringSession model for this view and updates the view based 
     * on the model's data.
     * 
     * @param model the TutoringSession model to set
     */
    public void setModel(TutoringSession model) {
        this.model = model;
        updateView();
    }
    
    /**
     * Display the current tutoring session model in this view.
     * 
     */
    @Override
    protected void updateView() {
        
        System.out.println("Add One Bit update display called");
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        Step step = model.currentTask().currentStep();
        
        AddOneStep newAddOneBit = gson.fromJson(step.getData(), AddOneStep.class);
        
        //System.out.println(newAddOneBit.)
        
        // Clear any existing feedback and response from the previous question.
        feedbackArea.setText("");
        responseArea.setText("");
        
        this.question = newAddOneBit.getQuestion();
        
        if (this.question == null) {
            questionLabel.setText("Please click new example button to get started");
        }
        
        else {
            questionLabel.setText(String.format("Convert the following "
                        + "string to binary and append a 1 bit to it: %s", question));
        }
        
        if (this.wasHintRequested) {
            this.remove(this.asciiTableScrollPane);
            this.revalidate();
            this.repaint();
        }
        
       /* try {      
            // Parse the desired message length from the input field.
            int messageLength = Integer.parseInt(messageLengthField.getText().trim());
            question = generateRandomString(messageLength);
                        
            // Update the question label with the new question.
            questionLabel.setText(String.format("Convert the following "
                    + "string to binary and append a 1 bit to it: %s", question));
            
            // Enable the buttons, ready for the user's response.
            // DO WE WANT TO DISABLE NEXT BUTTON ONCE MODEL COMPLETED AND LEARNING
            // AI GENERATES THE QUESTIONS?
            checkButton.setEnabled(true);
            hintButton.setEnabled(true);
            nextButton.setEnabled(true);
        } catch (NumberFormatException e) {
            // If the message length input is not a valid number, inform the user.
            feedbackArea.setText("Please enter a valid message length.");
        }*/
        
        //source.setText(newAddOneBit.getSource()); Commented out during sprint 1, dont think I need it.
        
        /* // Blocked during sprint 1
        Task task = model.currentTask();
        Step step = task.currentStep();
        
        String data = step.getData();
        
        stepDataModel = gson.fromJson(data, AddOneStep.class);
        
        source.setText(stepDataModel.getSource());*/
    }

    @Override
    public NewExampleRequest newRequest() {
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        NewExampleRequest ex = new NewExampleRequest();
        
        ex.setExampleType(ExampleType.ADD_ONE_BIT);
        
        AddOneStep newStep = new AddOneStep();
        
        newStep.setMessageLength(Integer.parseInt(messageLengthField.getText().trim()));
        
        
        
        //possibly missing something here, may need to look at AddOneStep constructor again
        System.out.println("Before newstep");
        System.out.println(newStep);
        System.out.println("After newstep");
        
        ex.setData(gson.toJson(newStep));
        
        return ex;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public StepCompletion stepCompletion() {
        
        Step currentStep = model.currentTask().currentStep();
        
        AddOneStep completedAddOneStep = gson.fromJson(currentStep.getData(), AddOneStep.class);
        
        String userResponse = this.responseArea.getText().replaceAll("\\s", "");
        
        completedAddOneStep.setUserAnswer(userResponse);
        
        StepCompletion step = new StepCompletion(currentStep, gson.toJson(completedAddOneStep));
        
        step.setStep(currentStep);
        
        return step;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
