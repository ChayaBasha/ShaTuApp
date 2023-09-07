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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author rickb
 */
public class PrepareScheduleView extends GPanel implements ActionListener {

    
    private JButton testBut;
    
    public PrepareScheduleView() {

        initializeComponents();
        initializeLayout();
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
       if (event.getSource() == testBut) {
           System.out.println("Test BUtton");
       }
    }
 
    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
     
        
        testBut = new JButton("Verify");
        testBut.setToolTipText("Click to verify input");
    }
    
    private void initializeLayout() {
 
        
        addc(testBut, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        // Fills the remaining space
        addc(new JLabel(""), 0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);
    }
}
