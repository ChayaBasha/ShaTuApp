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

import edu.regis.shatu.svc.SHA_256;
import edu.regis.shatu.svc.SHA_256Listener;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * A view that supports the conversion of the input message to ASCII bytes.
 * 
 * @author rickb
 */
public class EncodeView extends GPanel implements ActionListener, KeyListener, SHA_256Listener {
        
    /**
     * The ASCII character the student is being asked to convert
     */
    private JLabel exampleCharacter;
    
    /**
     * The input entered by the student
     */
    private JTextField charInput;
    
    private JButton verifyBut;
    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public EncodeView() {       
        initializeComponents();
        initializeLayout();
    }
    
    public void notifyAsciiEncoding(byte[] bytes) {
        // ToDo: This is simply a temporary test
        System.out.println("MsgByte.len: " + bytes.length);
        System.out.println("Msg: ");
        for (int i = 0; i < bytes.length; i++)
            System.out.println("Byte " + i + ": " + bytes[i]);
    }
    
    public static String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   
                            .replaceAll(" ", "0")
            );
            result.append(' ');
        }
        return result.toString();

    }
   
    
    @Override
    public void actionPerformed(ActionEvent event) {
       if (event.getSource() == verifyBut) {
           String userInput = charInput.getText();
           
           String result = convertStringToBinary(userInput);
           
           JOptionPane.showMessageDialog(this, result);
           
       }
    }
    
    
    @Override
    public void keyTyped(KeyEvent event){
       if (event.getSource()== charInput){
          String userInput = charInput.getText();

       }
    }
    
    @Override 
    public void keyPressed(KeyEvent event){
       if(event.getSource()== charInput && event.getKeyCode()== KeyEvent.VK_ENTER) {
         String userInput = charInput.getText(); 
          
          
           // ToDo: this is simply a test of the SHA-256 algorithm
           SHA_256 alg = GuiController.instance().getSha256Alg();
           System.out.println("Alg: " + alg);
           alg.addListener(this);
         
           
           String digest = alg.sha256("Regis Computer Science Rocks!");
           System.out.println("Enter gitDigest: " + digest);
       }
    }
    
    @Override
    public void keyReleased(KeyEvent event) {
       
    }
    
 
    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        exampleCharacter = new JLabel("");
        
        charInput = new JTextField(20);
        charInput.addKeyListener(this);
        
        verifyBut = new JButton("Check");
        verifyBut.setToolTipText("Click to verify input");
        verifyBut.addActionListener(this);
        
    }
    
    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {
        JLabel label = new JLabel("Enter Character: ");
        label.setLabelFor(exampleCharacter);
        addc(label, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
     
        addc(exampleCharacter, 1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        
        label = new JLabel("Type Here:");
        label.setLabelFor(charInput);
        addc(label, 0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
      
        addc(charInput, 1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        addc(verifyBut, 0, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        // Fills the remaining space
        addc(new JLabel("Test"), 0, 3, 2, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);
        
    }
}
