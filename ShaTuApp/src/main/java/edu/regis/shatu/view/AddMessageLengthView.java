package edu.regis.shatu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * View for demonstrating the "Add Message Length" step in SHA-256.
 */
public class AddMessageLengthView extends JPanel {

    private JTextArea encodedMessage;
    private JTextField inputMessageLength;
    private JTextField inputBinaryLength;
    private JTextField inputFinalMessage;
    private JLabel messageLengthPromptLabel;
    private JLabel binaryRepresentationLabel;
    private JTextArea feedbackArea;

    private int selectedBitLength = 32;
    private int actualMessageLength;
    private String correctBinaryLength;
    private String correctFinalMessage;

    public AddMessageLengthView() {
        setLayout(new BorderLayout(10, 10));

        // Explanation panel
        JPanel explanationPanel = new JPanel();
        explanationPanel.setBorder(BorderFactory.createTitledBorder("Explanation"));
        explanationPanel.setLayout(new BorderLayout(10, 10));

        JTextArea explanationArea = new JTextArea(4, 40);
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setFont(new Font("Serif", Font.PLAIN, 14));
        explanationArea.setText("In this step, we will explore how the message length is appended to the padded message in the SHA-256 algorithm. " +
                "The original algorithm specifies a 64-bit length; however, we have reduced this for simplicity. " +
                "This step ensures that the hash function is aware of the original message's length, which is essential for the integrity of the cryptographic process.");

        explanationPanel.add(new JScrollPane(explanationArea), BorderLayout.CENTER);
        add(explanationPanel, BorderLayout.NORTH);

        // Input and settings panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Message Length Encoding"));

        // Encoded message with full horizontal width
        encodedMessage = new JTextArea(3, 60);
        encodedMessage.setEditable(false);
        encodedMessage.setFont(new Font("Monospaced", Font.PLAIN, 12));
        encodedMessage.setLineWrap(true);
        encodedMessage.setWrapStyleWord(true);
        encodedMessage.setMaximumSize(new Dimension(Integer.MAX_VALUE, encodedMessage.getPreferredSize().height));
        inputPanel.add(new JLabel("Encoded Message:"));
        inputPanel.add(encodedMessage);

        // Message length prompt and input
        messageLengthPromptLabel = new JLabel("Enter Original Message Length (in bits):");
        inputMessageLength = new JTextField(20);
        inputMessageLength.setFont(new Font("Monospaced", Font.PLAIN, 12));
        inputMessageLength.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputMessageLength.getPreferredSize().height));
        inputPanel.add(messageLengthPromptLabel);
        inputPanel.add(inputMessageLength);

        // Binary length prompt and input
        binaryRepresentationLabel = new JLabel("Enter the Length in Binary (8-bit, Bigendian):");
        inputBinaryLength = new JTextField(20);
        inputBinaryLength.setFont(new Font("Monospaced", Font.PLAIN, 12));
        inputBinaryLength.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputBinaryLength.getPreferredSize().height));
        inputPanel.add(binaryRepresentationLabel);
        inputPanel.add(inputBinaryLength);

        // Final message prompt and input
        JLabel finalMessageLabel = new JLabel("Enter Final Message (with length appended):");
        inputFinalMessage = new JTextField(40);
        inputFinalMessage.setFont(new Font("Monospaced", Font.PLAIN, 12));
        inputFinalMessage.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputFinalMessage.getPreferredSize().height));
        inputPanel.add(finalMessageLabel);
        inputPanel.add(inputFinalMessage);

        // Buttons and feedback area
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton checkLengthButton = new JButton("Check Length");
        checkLengthButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        checkLengthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkMessageLength();
            }
        });

        JButton checkBinaryButton = new JButton("Check Binary Length");
        checkBinaryButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        checkBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBinaryLength();
            }
        });

        JButton checkFinalMessageButton = new JButton("Check Final Message");
        checkFinalMessageButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        checkFinalMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkFinalMessage();
            }
        });

        buttonsPanel.add(checkLengthButton);
        buttonsPanel.add(checkBinaryButton);
        buttonsPanel.add(checkFinalMessageButton);
        inputPanel.add(buttonsPanel);

        add(inputPanel, BorderLayout.CENTER);

        // Feedback panel
        feedbackArea = new JTextArea(3, 60);
        feedbackArea.setEditable(false);
        feedbackArea.setFont(new Font("SansSerif", Font.ITALIC, 12));
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackArea);
        feedbackScrollPane.setBorder(BorderFactory.createTitledBorder("Feedback"));
        add(feedbackScrollPane, BorderLayout.SOUTH);

        // Generate the initial message
        generateAndDisplayRandomMessage();
    }

    private void generateAndDisplayRandomMessage() {
        Random random = new Random();
        actualMessageLength = random.nextInt((selectedBitLength / 2) - 4 + 1) + 4; // Random length between 4 and half the bit length
        StringBuilder randomMessage = new StringBuilder();
        for (int i = 0; i < actualMessageLength; i++) {
            randomMessage.append(random.nextBoolean() ? "1" : "0");
        }
        randomMessage.append("1"); // Append a 1-bit
        int paddingLength = selectedBitLength - randomMessage.length();
        for (int i = 0; i < paddingLength; i++) {
            randomMessage.append("0");
        }
        encodedMessage.setText(randomMessage.toString());
        messageLengthPromptLabel.setText("Enter Original Message Length (in bits):");

        correctBinaryLength = String.format("%8s", Integer.toBinaryString(actualMessageLength)).replace(' ', '0'); // Use 8-bit binary representation
        correctFinalMessage = randomMessage.toString() + correctBinaryLength;
    }

    private void checkMessageLength() {
        try {
            int enteredLength = Integer.parseInt(inputMessageLength.getText().trim());
            if (enteredLength == actualMessageLength) {
                binaryRepresentationLabel.setText("Correct! Now enter the length in binary (8-bit):");
                inputBinaryLength.setEditable(true);
                feedbackArea.setText("Message length is correct.");
            } else {
                feedbackArea.setText("Incorrect message length. Please try again.");
            }
        } catch (NumberFormatException ex) {
            feedbackArea.setText("Please enter a valid number for the message length.");
        }
    }

    private void checkBinaryLength() {
        String binaryLength = inputBinaryLength.getText().trim();
        if (binaryLength.equals(correctBinaryLength)) {
            feedbackArea.setText("Correct binary length! Now append it to the message.");
            inputFinalMessage.setEditable(true);
        } else {
            feedbackArea.setText("Incorrect binary length. Please try again.");
        }
    }

    private void checkFinalMessage() {
        String finalMessage = inputFinalMessage.getText().trim();
        if (finalMessage.equals(correctFinalMessage)) {
            feedbackArea.setText("Correct! The final message is valid.");
        } else {
            feedbackArea.setText("Incorrect final message. Ensure you have appended the binary length correctly.");
        }
    }
}
