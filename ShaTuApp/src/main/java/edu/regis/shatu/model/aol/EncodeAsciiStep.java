/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.model.aol;

/**
 * A tutoring step that requires the student to encode a string as a sequence
 * of ASCII values in one or more steps.
 * 
 * @author rickb
 */
public class EncodeAsciiStep  {
    /**
     * The example that is associated with this encoding step.
     */
    private EncodeAsciiExample example;
    
    /**
     * Whether the encoding of the string in the example should be performed
     * as one or multiple steps.
     * 
     * If true, the coding of each character in the example should be performed
     * as its own step. Otherwise, the entire string should be encoded in one
     * step.
     */
    private boolean multiStep;
    
    public EncodeAsciiStep() {
    this.multiStep = false;  // Set default to single-step encoding
    }

    public EncodeAsciiExample getExample() {
        return example;
    }

    public void setExample(EncodeAsciiExample example) {
        this.example = example;
    }

    public boolean isMultiStep() {
        return multiStep;
    }

    public void setMultiStep(boolean multiStep) {
        this.multiStep = multiStep;
    }
    
    public void encode() {
        String inputString = example.getExampleString();

        // Convert each character to its ASCII value
        int[] asciiValues = new int[inputString.length()];

        for (int i = 0; i < inputString.length(); i++) {
            asciiValues[i] = (int) inputString.charAt(i);
        }

        // Set the encoding in the example object
        example.setAsciiEncoding(asciiValues);

        // If multi-step is true, encode character by character
        if (multiStep) {
            System.out.println("Encoding each character one by one:");
            for (int i = 0; i < asciiValues.length; i++) {
                System.out.println(inputString.charAt(i) + " -> " + asciiValues[i]);
            }
        } else {
            // Encode the entire string in one step
            System.out.println("Encoding the entire string at once:");
            System.out.println(inputString + " -> " + java.util.Arrays.toString(asciiValues));
        }
    }
}

