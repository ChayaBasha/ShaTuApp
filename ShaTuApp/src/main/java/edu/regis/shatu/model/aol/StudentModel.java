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

import edu.regis.shatu.model.Model;
import java.util.HashMap;

/**
 * Captures the current assessment for each learning outcome in a course, as 
 * well as, all tutoring sessions in which the student participated.
 * 
 * @author rickb
 */
public class StudentModel extends Model {
    /**
     * Convenience reference to the user id (email) of the student associated
     * with this student model.
     */
    private String userId;
    
    /**
     * The assessments of outcomes for the student associated with this model.
     */
    private HashMap<Outcome, Assessment> assessments;
    
    /**
     * The current scaffolding being used to support the student.
     */
    private ScaffoldLevel scaffoldLevel = ScaffoldLevel.EXTREME;
    
    /**
     * Instantiate this student model with default information.
     */
    public StudentModel() {
        super(DEFAULT_ID);
        
        assessments = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Return whether this student has an assessment for the given outcome.
     * 
     * @param outcome
     * @return true if the student has an assessment for the given outcome.
     */
    public boolean containsAssessment(Outcome outcome) {
        return assessments.containsKey(outcome);
    }
    
    /**
     * Return the student assessment, if any, for the given outcome.
     * 
     * @param outcome the Outcome being accessed
     * 
     * @return an Assessment of the student.
     */
    public Assessment findAssessment(Outcome outcome) {
        return assessments.get(outcome);
    }
    
    /**
     * Return the current scaffolding level being used to support the student.
     * 
     * @return 
     */
    public ScaffoldLevel getScaffoldLevel() {
        return scaffoldLevel;
    }

    public void setScaffoldLevel(ScaffoldLevel scaffoldLevel) {
        this.scaffoldLevel = scaffoldLevel;
    }
}