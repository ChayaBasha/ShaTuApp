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
 * A problem that a student must complete, which is assessed as either 
 * completed or not; though the problem probably addresses other outcomes.
 * 
 * @author rickb
 */
public class ProblemOutcome extends Outcome {
    /**
     * The database id of the problem associated with this outcome
     */
    private int problemId;
    
    private int sequenceId;
    
    public ProblemOutcome(int id) {
        super(id);
        
        problemId = DEFAULT_ID;
        sequenceId = DEFAULT_ID;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }
}