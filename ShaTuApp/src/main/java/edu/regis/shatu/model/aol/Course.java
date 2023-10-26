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

import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.TitledModel;
import java.util.ArrayList;

/**
 * A course that may be taught by the ShaTu tutor.
 * 
 * @author rickb
 */
public class Course extends TitledModel {   
    /**
     * The top-level learning outcomes associated with this course.
     */
    private ArrayList<Outcome> outcomes;
    
    /**
     * If non-empty, mastery learning is used in this course to assign tasks
     * from the pool of tasks in these units, which must be completed in order.
     */
    private ArrayList<Unit> units;
    
    public Course() {
        this(DEFAULT_ID);
    }
    
    public Course(int id) {
        super(id);
        
        units = new ArrayList<>();
        outcomes = new ArrayList<>();
    }
    
    public void addUnit(Unit module) {
        units.add(module);
    }
    
    public Unit findUnit(int id) throws ObjNotFoundException {
        for (Unit unit : units)
            if (unit.getId() == id)
                return unit;
        
        throw new ObjNotFoundException(String.valueOf(id));
    }
    
    public Unit findUnitBySequenceId(int sequenceId) throws ObjNotFoundException {
        for (Unit unit : units) {
            if (unit.getSequenceId() == sequenceId) {
                return unit;
            }
        }
                
        throw new ObjNotFoundException("Sequence Id: " + sequenceId);
        
    }
    
    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }
    
    public void addOutcome(Outcome outcome) {
        outcomes.add(outcome);
    }

    public ArrayList<Outcome> getOutcomes() {
        return outcomes;
    }
    
    /**
     * Return the problem outcomes, if any, for this course.
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
}
