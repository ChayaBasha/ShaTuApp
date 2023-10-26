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
 * Bloom's cognitive levels where each level includes the level before it.
 * 
 * ToDo: Work these out?
 *
 * @author rickb
 */
public enum BloomLevel {
    /**
     * The student is expected to know,
     */
     KNOWLEDGE("Knowledge"),

     /**
      *  Student controls a scenario and may ask for unpenalized hints.
      */
     COMPREHENSION("Comprehension"),

     /**
      *  Student may ask clarifying contextual questions.
      */
     APPLICATION("Application"),

     /**
      * 
      */
     ANALYSIS("Analysis"),

     /**
      * 
      */
     SYNTHESIS("Synthesis"),

     /**
      * 
      */
     EVALUATION("Evaluation");
     
    /**
     * A GUI displayable string identifying this taxonomy level.
     */
    private final String title;
    
    BloomLevel(String title) {
        this.title = title;
    }
     
    /**
     * Return the taxonomy level
     * 
     * @return aString
     */
    public String title() {
        return title;
    }
}