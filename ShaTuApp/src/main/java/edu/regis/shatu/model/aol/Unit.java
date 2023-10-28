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

import edu.regis.shatu.model.TitledModel;
import java.util.ArrayList;

/**
 * A semantically cohesive collection of tasks within a course that a student
 * is expected to master before moving to the next unit using Mastery Learning
 * per VanLehn (2006).
 * 
 * @author rickb
 */
public class Unit extends TitledModel {  
    private PedagogyKind pedagogy;
    
    /**
     * The sequence order in which this unit appears in its parent course or
     * unit.
     */
    private int sequenceId = DEFAULT_ID;
    
    /**
     * The learning outcomes associated with this Unit.
     */
    private ArrayList<Outcome> outcomes;
    
 
    
    /**
     * Instantiate this unit with default information
     */
    public Unit() {
        this(DEFAULT_ID);
    }
    
    /**
     * Instantiate this unit with the given id.
     * 
     * @param id a unique id, as determined by the database used to
     *           persist this unit.
     */
    public Unit(int id) {
        super(id);
        
        pedagogy = PedagogyKind.FIXED_SEQUENCE;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }
    
    public void addOutcome(Outcome outcome) {
        outcomes.add(outcome);
    }

    public ArrayList<Outcome> getOutcomes() {
        return outcomes;
    }
    
    /**
     * Return the problem outcomes, if any, for this module.
     * 
     * @return an ArrayList of ProblemOutcome
     */
    public ArrayList<ProblemOutcome> getProblemOutcomes() {
        ArrayList<ProblemOutcome> probOutcomes = new ArrayList<>();
        
        for (Outcome outcome : outcomes)
            if (outcome instanceof ProblemOutcome)
                probOutcomes.add((ProblemOutcome) outcome);
        
        return probOutcomes;
    }

    public void setOutcomes(ArrayList<Outcome> outcomes) {
        this.outcomes = outcomes;
    }
    
    public PedagogyKind getPedagogy() {
        return pedagogy;
    }

    public void setPedagogy(PedagogyKind pedagogy) {
        this.pedagogy = pedagogy;
    }
}
