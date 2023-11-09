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
 * A knowledge component representing a piece of information known to be true.
 * 
 * @author rickb
 */
public class Fact extends KnowledgeComponent {
    public Fact() {
        this(DEFAULT_ID);
    }
    
    /**
     * Instantiate this fact with the given id.
     * 
     * @param id a unique id, as determined by the database used to
     *           persist this fact
     */
    public Fact(int id) {
        super(id);
    }
}

