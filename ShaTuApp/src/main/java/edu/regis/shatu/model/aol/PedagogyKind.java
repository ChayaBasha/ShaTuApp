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
 * The pedagogical approach used by the tutor or student to select next tasks
 * as given in VanLehn (2006); from Bloom(1984), and (Corbett 7 Anderson 1995).
 * 
 * @author rickb
 */
public enum PedagogyKind {
    /**
     * Tasks are assigned in a fixed sequence and the student is required
     * to finish a task before beginning the next task.
     */
    FIXED_SEQUENCE("Fixed Sequence"),
    
    /**
     * The tutor selects tasks based on an estimate of the student's mastery
     * of a knowledge component.
     */
    MACROADAPTION("Macroadaption"),
    
    /**
     * Curriculum is structured by a sequence of units or difficulty levels, 
     * where the tutor assesses mastery of the knowledge in the tasks associated
     * with the unit/level.
     */
    MASTERY_LEARNING("Mastery Learning"),
    
    /**
     * The student selects the next task.
     */
    STUDENT_SELECT("Student Select");
    
     /**
     * A GUI displayable string identifying this taxonomy level.
     */
    private final String title;
    
    PedagogyKind(String title) {
        this.title = title;
    }
    
    /**
     * Return the pedagogy type.
     * 
     * @return a String
     */
    public String title() {
        return title;
    }
}
