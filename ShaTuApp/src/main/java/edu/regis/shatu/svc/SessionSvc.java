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
package edu.regis.shatu.svc;

import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.TutoringSession;

/**
 * Specifies the API for Session life-cycle maintenance (persistence).
 * 
 * @author rickb
 */
public interface SessionSvc {
    /**
     * Insert the given session into the database.
     * 
     * @param session the TutoringSession to create.
     * @throws NonRecoverableException perhaps see getCause().getErrorCode().
     */
    void create(TutoringSession session) throws NonRecoverableException;
    
    /**
     * Return the session with the specified id (this is a full session with all
     * events versus a digest).
     * 
     * @param userId the user id of the session to return
     * @return the Session for the given user id
     * @throws ObjNotFoundException no trial with the given id exists
     * @throws NonRecoverableException perhaps see getCause().getErrorCode().
     */
    TutoringSession findById(String userId) throws ObjNotFoundException, NonRecoverableException;
}

