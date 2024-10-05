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

import edu.regis.shatu.model.StepCompletion;
import edu.regis.shatu.model.aol.NewExampleRequest;
import edu.regis.shatu.svc.SHA_256;
import edu.regis.shatu.svc.SHA_256Listener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A view that adds one to the current binary bytes.
 * 
 * @author rickb
 */
public class PadView extends UserRequestView implements ActionListener, KeyListener, SHA_256Listener {
        
    /**
     * The ASCII character the student is being asked to convert
     */
    private JLabel exampleCharacter;
    private JTextField charInput;
    private JButton verifyButton;
    
    /**
     * Initialize this view including creating and laying out its child components.
     */
    public PadView() {       
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

        try {
            // Parse the input string as an integer
            int number = Integer.parseInt(input.trim());

            // Convert the integer to a binary string
            String binaryStr = Integer.toBinaryString(number);

            // Pad the binary string to make it 32 bits long
            String paddedBinary = String.format("%32s", binaryStr).replaceAll(" ", "0");

            // Append the padded binary string to the result
            result.append(paddedBinary);
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            System.err.println("Error: The input is not a valid integer.");
            return "Invalid input";
        }

        return result.toString();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == verifyButton) {
            verifyInput();
        }
    }
    
    
    @Override
    public void keyTyped(KeyEvent event){
       if (event.getSource()== charInput){
          String userInput = charInput.getText();

       }
    }
    
    @Override 
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verifyInput();
        }
    }
    
    private void verifyInput() {
        String userInput = charInput.getText();
        String binaryString = convertStringToBinary(userInput);
        String formattedBinary = formatBinaryString(binaryString); 

        // Adjusting bit positions string to match the formatted binary string
        String bitPositions = "  4|   8|  12|  16|  20|  24|  28|  32|";

        // Construct the message with HTML for better control over formatting
        String message = "<html><pre>Bit positions: " + bitPositions + "<br/>Binary:"
                + "        " + formattedBinary + "</pre></html>";
        JOptionPane.showMessageDialog(this, message, "Binary Representation"
                , JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Formats the binary string to insert spaces every 4 bits for alignment with bit positions.
     */
    private String formatBinaryString(String binary) {
        StringBuilder sb = new StringBuilder();
        // Pad the binary string to ensure it's 32 bits long
        binary = String.format("%32s", binary).replaceAll(" ", "0");

        // Insert spaces every 4 characters from the end to align with bit markers
        for (int i = 0; i < binary.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                sb.append(" ");  // Add a space before the start of each group of 4 bits
            }
            sb.append(binary.charAt(i));
        }
        return sb.toString();
    }


    
    @Override
    public void keyReleased(KeyEvent event) {
       
    }
    
 
    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        exampleCharacter = new JLabel("Perform Padding on the ASCII character to binary:");

        charInput = new JTextField(20);
        charInput.addKeyListener(this);

        verifyButton = new JButton("Verify");
        verifyButton.addActionListener(this);
        
    }
    
    /**
     * Layout the child components in this view.
     */
    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Title label
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        JLabel titleLabel = new JLabel("Binary Padding Tutor");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, gbc);

        // Description label
        gbc.gridy++;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        JLabel descriptionLabel = new JLabel("<html><p>Convert the provided ASCII character into a binary format and pad to simulate SHA-256 input preparation.</p></html>");
        add(descriptionLabel, gbc);

        // Input label and field
        gbc.gridy++;
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Enter Character: "), gbc);
        gbc.gridx = 1;
        add(charInput, gbc);

        // Verify button
        gbc.gridy++;
        gbc.gridx = 0; 
        gbc.gridwidth = 1; // Use gridwidth = 1 for less width
        gbc.fill = GridBagConstraints.NONE; // Remove the fill constraint to prevent horizontal stretching
        verifyButton.setPreferredSize(new Dimension(100, 25)); // Optionally set a preferred size
        add(verifyButton, gbc);

        // Filler panel to push everything up
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty = 1; gbc.weightx = 1;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        JPanel filler = new JPanel();
        add(filler, gbc);        
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
            System.out.println("PadView");
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
