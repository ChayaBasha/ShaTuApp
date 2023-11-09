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
 * An assessment by the tutor of an outcome.
 * 
 * @author rickb
 */
public class Assessment {
    /**
     * The outcome assessed in this assessment
     */
    private Outcome outcome;
    
    /**
     * The student assessment of the outcome being assessed.
     */
    private AssessmentLevel assessment;
    
    public Assessment(Outcome outcome, AssessmentLevel assessment) {
        this.outcome = outcome;
        this.assessment = assessment;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    /**
     * Return the student assessment for this outcome.
     * 
     * @return an AssessmentLevel
     */
    public AssessmentLevel getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentLevel assessment) {
        this.assessment = assessment;
    }
}