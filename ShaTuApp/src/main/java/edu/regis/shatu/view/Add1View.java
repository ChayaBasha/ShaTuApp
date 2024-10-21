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
import edu.regis.shatu.model.Step;
import edu.regis.shatu.model.StepCompletion;
import edu.regis.shatu.model.aol.ExampleType;
import edu.regis.shatu.model.aol.NewExampleRequest;
import edu.regis.shatu.model.aol.StepSubType;
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
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A view that requests the student to add a single '1' bit to the byte prompt.
 * 
 * @author rickb
 */
public class Add1View extends UserRequestView implements ActionListener {
  
    private JTextPane descriptionTextPane;
    private JLabel questionLabel, instructionsLabel, messageLengthLabel;
    private JTextField messageLengthField;
    private JTextArea responseArea;
    private JTextArea feedbackArea;
    private JButton checkButton, nextButton, hintButton;
    private JTable asciiTable;
    private JScrollPane responseScrollPane, asciiTableScrollPane, feedbackScrollPane;
    private String question;
    private boolean wasHintRequested = false;

    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public Add1View() { 
        
        initializeComponents();
        initializeLayout();
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
    }  
    
    /**
     * Submit the student's answer to the tutor.  Currently is suppose to just do
     * a error check, when the check button is clicked, its suppose to call
     * the stepCompletion method that calls to the tutor and lets the tutor
     * handle checking the answer.  This method may be removed later in development.
     */
    public void submitAnswer() {
        
        if (this.responseArea.getText().equals("")) {
            this.feedbackArea.setText("Please provide an answer");
        }
        else {
            // Nothing, maybe needed later in development, tutor should be handling things though.

        }
    }
    
    /**
     * This method use to be called when the new example button is clicked, 
     * the tutor is suppose to handle creating a new example/question so this
     * method may be outdated, leaving in-case a use can be found in development,
     * but may no longer be needed.
     */
    private void prepareNextQuestion() {
        // Do nothing, tutor should be handling things, but leaving incase a use
        // could be found later in development
    }
    
    /**
     * Gives the student a hint and adds the ASCII table to the view, rest 
     * should be handles by the tutor, maybe all of it should?  Adjust as development
     * continues.
     */
    public void requestHint() {
        
        this.feedbackArea.setText("Hint: Check the ASCII Table to the right for guidance.");
        
        if (!this.isAncestorOf(asciiTableScrollPane)) { // Adds the ASCII table if it doesnt exist.
            addc(asciiTableScrollPane, 3, 0, GridBagConstraints.REMAINDER,
                    7, 2.0, 1.0, GridBagConstraints.CENTER, 
                    GridBagConstraints.BOTH, 5, 5, 5, 5);
            
            this.wasHintRequested = true; // Used in the updateView function
        }
        
        this.revalidate(); // Refreshes the view
        this.repaint(); // Refreshes the view
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

        hintButton = new JButton("Hint"); // Needs to be adjusted once tutor can handle hints
        hintButton.addActionListener(this);

        nextButton = new JButton(NewExampleAction.instance());
        nextButton.addActionListener(this);


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
    
    /**
     * Creates a label to tell the user what to do, like a extra hint/description.
     */
    private void setupInstructionLabel() {
        instructionsLabel = new JLabel("Please separate your entries with spaces.");
        instructionsLabel.setHorizontalAlignment(JLabel.CENTER);
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
     * Display the current tutoring session model in this view.  Is called when
     * the step button is clicked automatically, and is called again from
     * the tutor when a new example is created or a step has been completed.
     */
    @Override
    protected void updateView() {
        
        /*
        When switching between steps, the current step will be the previous enum
        that a example was created for.  If that enums related stepobject has
        similar variables, their may be a conflict causing a error.
        */
        StepSubType type = StepSubType.ADD_ONE_BIT;
        
        System.out.println("Add One Bit update display called"); // Error checking
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // May not be needed here.
        
        Step step = model.currentTask().getCurrentStep(); // Will be the last subtype a example was created for or empty
        
        System.out.println("Add 1 View substep from current step: " + step.getSubType()); // Error checking
        System.out.println("add one bit type: " + type); // Error checking
        
        AddOneStep newAddOneBit = gson.fromJson(step.getData(), AddOneStep.class); // Takes data to the class object created from the new example.
        
        // Clear any existing feedback and response from the previous question.
        feedbackArea.setText("");
        responseArea.setText("");
        
        if ((step.getSubType() == type)) { // Subtype was correct
            System.out.println("If branch was taken, subtype was a addone bit"); // Error checking.
            
            this.question = newAddOneBit.getQuestion();
            
            if (this.question == null) { // new example hasnt been created yet
                questionLabel.setText("Please click new example button to get started");
                checkButton.setEnabled(false);
                hintButton.setEnabled(false);
            }
        
            else { // example has been created.
                questionLabel.setText(String.format("Convert the following "
                            + "string to binary and append a 1 bit to it: %s", question));
                checkButton.setEnabled(true);
                hintButton.setEnabled(true);
            }
        }
        
        else { // subtype didnt match, new example needs to be created.
            System.out.println("Else branch was taken, subtype not add one bit"); // Error checking.
            
            questionLabel.setText("Please click new example button to get started");
            checkButton.setEnabled(false);
            hintButton.setEnabled(false);
        }
        
        if (this.wasHintRequested) { // If ASCII table exists, remove it from the view.
                this.remove(this.asciiTableScrollPane);
                this.revalidate();
                this.repaint();
        }
    }

    /**
     * This method is suppose to be called when the new example button is clicked,
     * it will assign related data pertaining to this step to the related class,
     * then send that class to the tutor to handle generating a question and answer.
     * Once the example is created by the tutor, the update view for this step is
     * called by the tutor.
     * 
     * @return
     */
    @Override
    public NewExampleRequest newRequest() {
        
        NewExampleRequest ex = new NewExampleRequest();
        
        ex.setExampleType(ExampleType.ADD_ONE_BIT);
        
        AddOneStep newAddOneStep = new AddOneStep(); // New class object.
        
        newAddOneStep.setMessageLength(Integer.parseInt(messageLengthField.getText().trim())); // Number of characters the question needs to be
        
        System.out.println(newAddOneStep);
        
        ex.setData(gson.toJson(newAddOneStep));
        
        return ex;
    }
    
    /**
     * This method is suppose to be called when the check button is clicked,
     * it should take the users answer, assign it to the related class, then
     * send it to the tutor to handle checking the answer and then will handle
     * a new GUI for the user to view.
     * 
     * @return 
     */
    @Override
    public StepCompletion stepCompletion() {
        
        Step currentStep = model.currentTask().currentStep(); // step created from the new example.
        
        AddOneStep completedAddOneStep = gson.fromJson(currentStep.getData(), AddOneStep.class); // Class object created from the new example.
        
        String userResponse = this.responseArea.getText(); // Get the user's answer.
        
        completedAddOneStep.setUserAnswer(userResponse); // User answer in the response area
        
        StepCompletion step = new StepCompletion(currentStep, gson.toJson(completedAddOneStep));
        
        step.setStep(currentStep); // Will be sent to the tutor.
        
        return step;
    }
}