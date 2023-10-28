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

import edu.regis.shatu.err.IllegalArgException;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.Account;
import edu.regis.shatu.model.User;
import java.util.ArrayList;

/**
 * Specifies the API for {@link User} life-cycle maintenance (persistence).
 * 
 * Note: this is only user and password information @see Student
 * 
 * @author rickb
 */
public interface UserSvc {
    /**
     * Insert the given User into the DB (cannot insert an admin).
     *
     * @param user a user containing the information to insert
     * @throws IllegalArgException a StudentUser with the given user id already exists
     * @throws NonRecoverableException also see getCause().getErrorCode().
     */
    void create(Account user) throws IllegalArgException, NonRecoverableException;
    
    /**
     * Delete the StudentUser with the given user id from the database.
     *
     * @param userId the unique key
     * @throws NonRecoverableException also see getCause().getErrorCode().
     */
    void delete(String userId) throws NonRecoverableException;
    
    /**
     * Return the StudentUser, if any, with the given user id.
     * 
     * @param userId the id of the StudentUser to return
     * @return a StudentUser with the given id
     * @throws ObjNotFoundException no StudentUser with the given id exists
     * @throws NonRecoverableException also see getCause().getErrorCode().
     */
    User findById(String userId) throws ObjNotFoundException, NonRecoverableException;
    
    /**
     * Update the given user's password (requires existing password).
     * 
     * @param user the userId and existing password
     * @param newPassword the new SHA-256 encrypted password
     * @throws ObjNotFoundException user doesn't exists in the database
     * @throws IllegalArgException
     * @throws NonRecoverableException also see getCause().getErrorCode().
     */
    void update(User user, String newPassword) 
            throws ObjNotFoundException, IllegalArgException, NonRecoverableException;
}


