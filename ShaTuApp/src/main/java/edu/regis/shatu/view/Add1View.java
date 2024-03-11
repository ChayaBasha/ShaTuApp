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
import edu.regis.shatu.model.Task;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.svc.ClientRequest;
import edu.regis.shatu.svc.ServerRequestType;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * A view that requests the student to add a single '1' bit to the byte prompt.
 * 
 * @author rickb
 */
public class Add1View extends GPanel implements ActionListener {
    /**
     * The tutoring session displayed in this view.
     */
    private TutoringSession model;
    
    /**
     * The part of the tutoring session model that is displayed in this view.
     */
    private AddOneStep stepDataModel;
    
    /**
     * The source input string to which one bit is to be added.
     */
    private JLabel source;
    
    /**
     * The input entered by the student
     */
    private JTextField target;
    
    private JButton submitBut, hintBut;
    
    /**
     * The number of hint requests made by the student.
     */
    private int hintsRequested;
    
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
    
    public void setModel(TutoringSession model) {
        this.model = model;
        
        updateDisplay();
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == submitBut) {
            submitAnswer();
           
        } else if (event.getSource() == hintBut) {
            requestHint();
        }
    }
    

    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        source = new JLabel("");
        
        target = new JTextField(65);
        
        submitBut = new JButton("Submit");
        submitBut.setToolTipText("Click to submit your answer to the tutor");
        submitBut.addActionListener(this);
        
        hintBut = new JButton("Hint");
        hintBut.setToolTipText("Click to request a hint");
        hintBut.addActionListener(this);
    }
    
    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {
        JLabel prompt = new JLabel("In this step, you must add a single '1' bit to the end of the following binary data:");
        addc(prompt, 0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 20, 5);
  
        JLabel sourceLabel = new JLabel("Input: ");
        sourceLabel.setLabelFor(source);
        addc(sourceLabel, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 20, 5);

        addc(source, 1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                5, 5, 20, 5);
     
        
        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setLabelFor(target);
        addc(outputLabel, 0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
      
        addc(target, 1, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
        
        addc(submitBut, 0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                25, 5, 5, 15);
        
        addc(hintBut, 1, 3, 2, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                25, 5, 5, 5);
        
        // Fills the remaining space
        addc(new JLabel(" "), 0, 4, 2, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);
    }
    
    /**
     * Display the current tutoring session model in this view.
     */
    private void updateDisplay() {
        Task task = model.currentTask();
        Step step = task.currentStep();
        
        String data = step.getData();
        
        stepDataModel = gson.fromJson(data, AddOneStep.class);
        
        source.setText(stepDataModel.getSource());
    }
    
    /**
     * Submit the student's answer to the tutor.
     */
    public void submitAnswer() {
        ClientRequest request = new ClientRequest(ServerRequestType.COMPLETED_STEP);     
        request.setUserId(model.getAccount().getUserId());
        request.setSessionId(model.getSecurityToken());    
        request.setData(gson.toJson(stepDataModel));
        
        GuiController.instance().tutorRequest(request);
    }
    
    /**
     * Process the students request for a hint.
     */
    public void requestHint() {
        Task task = model.currentTask();
        Step step = task.currentStep();
        
        ArrayList<Hint> hints = step.getHints();
        int maxHints = hints.size();
        

        String hintText;
        if (hintsRequested < maxHints) {
            hintText = hints.get(hintsRequested).getText();
        } else {
            hintText = "Sorry, there are no additional hints for this step.";
        }
        
        JOptionPane.showMessageDialog(this, hintText, "Hint", JOptionPane.OK_OPTION);
        
        hintsRequested++;
        
        ClientRequest request = new ClientRequest(ServerRequestType.REQUEST_HINT); 
        request.setUserId(model.getAccount().getUserId());
        request.setSessionId(model.getSecurityToken());
        request.setData(":HintGiven");
        
        GuiController.instance().tutorRequest(request);
    }
}
