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
    private int currentIndex = 0;
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
        this.currentIndex = 0;
    }

    public boolean isMultiStep() {
        return multiStep;
    }

    public void setMultiStep(boolean multiStep) {
        this.multiStep = multiStep;
    }
    
    public interface OutputListener {
    void appendText(String text);
    }
    
    public void encode() {
        String inputString = example.getExampleString();
        int[] asciiValues = new int[inputString.length()];

        for (int i = 0; i < inputString.length(); i++) {
            asciiValues[i] = (int) inputString.charAt(i);
        }
        example.setAsciiEncoding(asciiValues);

        // If multi-step is true, encode character by character
         if (multiStep) {
            if (currentIndex < asciiValues.length) {
                System.out.println("Encoding each character one by one:");
                System.out.println(inputString.charAt(currentIndex) + " -> " + asciiValues[currentIndex]);
                currentIndex++;
                System.out.println("current index: " + currentIndex);
            } else {
                System.out.println("Completed stepping through all characters.");
                currentIndex = 0;
            }
        } else {
            System.out.println("Encoding the entire string at once:");
            System.out.println(inputString + " -> " + java.util.Arrays.toString(asciiValues));
        }
    }
}

