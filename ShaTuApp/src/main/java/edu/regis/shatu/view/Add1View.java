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
     * Not currently used, but may be later in development, keep it in mind.
     */
    private AddOneStep stepDataModel;
    
    /**
     * The number of hint requests made by the student.
     */
    private int hintsRequested;  // Will need to be added to hint method for tutor at some point.
    private JTextPane descriptionTextPane;
    private JLabel questionLabel, instructionsLabel, messageLengthLabel;
    private JTextField messageLengthField;
    private JTextArea responseArea;
    private JTextArea feedbackArea;
    private JButton checkButton, nextButton, hintButton;
    private JTable asciiTable;
    private JScrollPane responseScrollPane, asciiTableScrollPane, feedbackScrollPane;
    private String question;
    private boolean wasHintRequested = false; // Used to display,undisplay the ASCII Table.
    
    
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
     * Submit the student's answer to the tutor.
     */
    public void submitAnswer() {
        
        if (this.responseArea.getText().equals("")) {
            this.feedbackArea.setText("Please provide an answer");
        }
        else {
            // do nothing right now, aiming to have the tutor handle hint related items
            // The step completion function handles the answer.
        }
    }
    
    /**
     * Prepares the view for the next question by clearing previous inputs
     * and feedback and generating a new question.
     * 
     * THIS NEEDS UPDATED ONCE MODEL IS CONFIGURED AND EXAMPLE IS COMPLETED.
     * AT THIS POINT IN DEVELOPMENT, MAY BE DELETED IF NO OTHER USE IS FOUND
     */
    private void prepareNextQuestion() {
        // The tutor now handles the next question utilizing the newExample function.
        // This may no longer be needed, but left incase it could be used later.
    }
    
    /**
     * Process the students request for a hint. Suppose to load the ACII table,
     * no need to get rid of it once loaded, suppose to be done in the updateView function.
     * 
     * ***NEED TO REVIEW THIS AND THINK ABOUT IT.
     */
    public void requestHint() {
        this.feedbackArea.setText("Hint: Check the ASCII Table to the right for guidance.");
        
        // Suppose to load the ASCII table into the view.
        if (!this.isAncestorOf(asciiTableScrollPane)) {
            addc(asciiTableScrollPane, 3, 0, GridBagConstraints.REMAINDER,
                    7, 2.0, 1.0, GridBagConstraints.CENTER, 
                    GridBagConstraints.BOTH, 5, 5, 5, 5);
            
            this.wasHintRequested = true; // Used in the updateView function
        }
        
        this.revalidate(); // Ensures the view is updated
        this.repaint(); // Ensures the view is updated
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
     * Display the current tutoring session model in this view.  Also removes 
     * the ASCII table if present.  Is called when a newExample function is called.
     * 
     */
    @Override
    protected void updateView() {
        
        System.out.println("Add One Bit update display called"); // Console Error Checking.
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        Step step = model.currentTask().currentStep();
        
        AddOneStep newAddOneBit = gson.fromJson(step.getData(), AddOneStep.class);
        
        // Clear any existing feedback and response from the previous question.
        feedbackArea.setText("");
        responseArea.setText("");
        
        this.question = newAddOneBit.getQuestion();
        
        if (this.question == null) { // When Add One is first selected, will be null until a new example is generated.
            questionLabel.setText("Please click new example button to get started");
        }
        
        else {
            questionLabel.setText(String.format("Convert the following "
                        + "string to binary and append a 1 bit to it: %s", question));
        }
        
        if (this.wasHintRequested) {
            this.remove(this.asciiTableScrollPane); // Removes the ASCII table
            this.revalidate(); // Ensures the view is updated
            this.repaint(); // Ensures the view is updated
        }
    }

    /**
     * When the new example button is clicked, this function is called,
     * will create a AddOneStep object, set the message length, and send it
     * to the shaTuTutor file to finish creating the new example.
     * 
     * @return
     */
    @Override
    public NewExampleRequest newRequest() {
        
        NewExampleRequest ex = new NewExampleRequest();
        
        ex.setExampleType(ExampleType.ADD_ONE_BIT);
        
        AddOneStep newStep = new AddOneStep();
        
        newStep.setMessageLength(Integer.parseInt(messageLengthField.getText().trim()));
        
        System.out.println("Before newstep"); // Console Error Checking
        System.out.println(newStep); // Console Error Checking
        System.out.println("After newstep"); // Console Error Checking
        
        ex.setData(gson.toJson(newStep));
        
        return ex;
    }

    /**
     * When the check button is clicked, this function is called.  Will take the 
     * AddOneStep object that was created in the newRequest function, and set the
     * users answer in the response area to the object, and then will go to
     * the ShaTuTutor file to finish comparing the answer.
     * 
     * @return 
     */
    @Override
    public StepCompletion stepCompletion() {
        
        Step currentStep = model.currentTask().currentStep();
        
        AddOneStep completedAddOneStep = gson.fromJson(currentStep.getData(), AddOneStep.class); // AddOneStep created in the newRequest function
        
        String userResponse = this.responseArea.getText().replaceAll("\\s", "");
        
        completedAddOneStep.setUserAnswer(userResponse); // User answer in the response area
        
        StepCompletion step = new StepCompletion(currentStep, gson.toJson(completedAddOneStep));
        
        step.setStep(currentStep);
        
        return step;
    }
}