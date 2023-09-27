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

/**
 * The primary steps in the SHA-256 algorithm, which correspond to views that
 * can be displayed by selecting the associated labels.
 * 
 * @author rickb
 */
public enum StepSelection {
    /**
     * Encode the input message as ASCII bytes step.
     */
    ENCODE(new HighlightLabel("1.Encode as ASCII")), 
    
    ADD1(new HighlightLabel("Add '1' bit")), 
    
    PAD(new HighlightLabel("Pad with '0's")), 
    
    LENGTH(new HighlightLabel("Add Msg Length")), 
    
    TEST(new HighlightLabel("Test")), 
    
    PREPARE(new HighlightLabel("Prepare Schedule")),
   
    INIT_VARS(new HighlightLabel("Initialize Variables")), 
    
    COMPRESS(new HighlightLabel("Compress Round")),

    ROTATE_BITS(new HighlightLabel("Rotate n bits")),

    SHIFT_RIGHT(new HighlightLabel("Shift right")),

    ADD_TWO_BIT(new HighlightLabel("Add two 𝑛 bit"));



    private HighlightLabel label;
    
    StepSelection(HighlightLabel label) {
        this.label = label;
        label.setStepSelection(this);
    }
    
    public HighlightLabel getLabel() {
        return label;
    }
}
