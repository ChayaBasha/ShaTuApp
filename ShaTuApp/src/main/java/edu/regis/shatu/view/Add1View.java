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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author rickb
 */
public class Add1View extends GPanel implements ActionListener {
     /**
     * The ASCII character the student is being asked to convert
     */
    private JLabel test;
    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public Add1View() {       
        initializeComponents();
        initializeLayout();
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
       
    }
 
    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        test = new JLabel("Add 1 Test");
        

    }
    
    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {
        JLabel label = new JLabel("Example Character: ");
        label.setLabelFor(test);
        addc(label, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
     
        addc(test, 1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
    }
}
