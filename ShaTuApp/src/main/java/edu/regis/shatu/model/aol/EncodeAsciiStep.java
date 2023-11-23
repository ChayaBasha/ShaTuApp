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
}
