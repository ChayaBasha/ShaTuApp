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
 * Per VanLehn (2006), a component of knowledge (concept, rule, procedure, fact)
 * possessed by a student representing a fragment of task-specific information.
 * 
 * @author rickb
 */
public abstract class KnowledgeComponent extends Outcome {
    /**
     * Instantiate this knowledge component with a default id.
     */
    public KnowledgeComponent() {
        this(DEFAULT_ID);
    }
    
    /**
     * Instantiate this knowledge component with the given id.
     * 
     * @param id a unique id, as determined by the database used to
     *           persist this knowledge component.
     */
    public KnowledgeComponent(int id) {
        super(id);
    }
    
    @Override
    public OutcomeGranularity getGranularity() {
        return OutcomeGranularity.KNOWLEDGE_COMPONENT;
    }
    
    public void setOutcomeGranularity(OutcomeGranularity ignore) {
        granularity = OutcomeGranularity.KNOWLEDGE_COMPONENT;
    }
}
