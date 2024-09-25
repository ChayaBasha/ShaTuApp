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
package edu.regis.shatu.model;

/**
 *
 * @author rickb
 */
public class AddOneStep {
    /**
     * The query data the student is being asked to add '1' bit too.
     */
    private String source;
    
    /**
     * The answer resulting from adding '1' bit to the the source.
     */
    private String target;
    
    /**
     * Initialize this step with the given source and target data.
     * 
     * @param source the source data the student is being asked to modify
     * @param target the target data after the modification.
     */
    public AddOneStep(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
