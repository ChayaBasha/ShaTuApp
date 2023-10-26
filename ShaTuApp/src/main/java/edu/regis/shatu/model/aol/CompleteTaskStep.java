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
 *
 * @author rickb
 */
public class CompleteTaskStep extends Step {

    public CompleteTaskStep(int id, int sequenceId) {
        super(id, sequenceId);
    }

    @Override
    public String getType() {
        return "CompleteTaskStep";
    }
}

