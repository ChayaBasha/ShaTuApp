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

import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * A view displaying the primary steps in the SHA-256 algorithm, which can
 * be selected to change the current step view.
 * 
 * @author rickb
 */
public class StepSelectorView extends GPanel {
    /**
     * The currently selected step name.
     */
    private StepSelection selectedStep = StepSelection.ENCODE;
    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public StepSelectorView() {
        GuiController.instance().setStepSelectorView(this);
 
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        initializeComponents();
        layoutComponents();
        
        StepSelection.ENCODE.getLabel().select(); // Will callback displayStep
    }
    
    /**
     * Display the given selection's view in the StepView handling appropriate
     * highlighting of the associated JLabel selector (see StepSelection enum).
     * 
     * @param selection 
     */
    public void displayStep(StepSelection selection) {
        if (selectedStep != selection)
            selectedStep.getLabel().deselect();
        
        GuiController.instance().getStepView().selectPanel(selection);
    
        selectedStep = selection;
    }
    
    /**
     * Create the child components used in this frame.
     */
    private void initializeComponents() {  
        // Note: the child components are found in the StepSelection enum.
        //       For example, StepSelection.ENCODE.getLabel()
    }
    
     /**
     * Layout the child components used in this frame.
     */
    private void layoutComponents() {
        JLabel label = new JLabel("Step Selection");
        addc(label, 0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        addc(createPreprocessingPanel(), 0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
        
        addc(createHashCompPanel(), 0, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
    }
    
    /**
     * Create and layout the pre-processing panel.
     * 
     * @return the preprocessing GPanel
     */
    private GPanel createPreprocessingPanel() {
        GPanel panel = new GPanel();
        
        panel.setBackground(new Color(207, 245, 215)); // A light green
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JLabel label = new JLabel("Message Preprocessing");
        panel.addc(label, 0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
        
        panel.addc(StepSelection.ENCODE.getLabel(), 0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        panel.addc(StepSelection.ADD1.getLabel(), 0, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        panel.addc(StepSelection.PAD.getLabel(), 0, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        panel.addc(StepSelection.LENGTH.getLabel(), 0, 4, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
        
        panel.addc(StepSelection.ROTATE.getLabel(), 0, 5, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
        return panel;
    }
    
    /**
     * Create and layout the hash computation panel.
     * 
     * @return the hash computation GPanel
     */
    private GPanel createHashCompPanel() {
        GPanel panel = new GPanel();
        
        panel.setBackground(new Color(250, 236, 213)); // A light green
        panel. setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JLabel label = new JLabel("Hash Computation");
        panel.addc(label, 0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
        
        panel.addc(StepSelection.PREPARE.getLabel(), 0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        panel.addc(StepSelection.INIT_VARS.getLabel(), 0, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);

        panel.addc(StepSelection.ROTATE.getLabel(), 0, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                5, 5, 5, 5);
        
        return panel;
    }
}
