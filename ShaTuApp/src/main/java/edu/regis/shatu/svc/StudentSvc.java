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
import edu.regis.shatu.model.Student;
import edu.regis.shatu.model.aol.StudentModel;

/**
 * Specifies the API for {@link Student} life-cycle maintenance (persistence).
 * 
 * @author rickb
 */
public interface StudentSvc  {
    /**
     * Insert the given {@link Student} into the database.
     * 
     * @param student
     * @throws IllegalArgException
     * @throws NonRecoverableException perhaps see getCause().getErrorCode()
     */
    void create(Student student) throws IllegalArgException, NonRecoverableException;
    
    /**
     * Return whether a student with the given user id exists. 
     * 
     * The idea is that this will execute quickly since it avoid loading the
     * student model.
     * 
     * @param userId
     * @return
     * @throws NonRecoverableException
     */
    boolean exists(String userId) throws NonRecoverableException;
    
    /**
     * Return the {@link Student} with the given user id.
     * 
     * See exists(String) for a faster check as to whether a student exists.
     * 
     * @param userId the email address of the student to find
     * @return the desired student
     * @throws ObjNotFoundException no student with the give user id exists
     * @throws NonRecoverableException perhaps see getCause().getErrorCode()
     */
    Student findStudentById(String userId) throws ObjNotFoundException, NonRecoverableException;
    
    /**
     * Return the {@link StudentModel} for the given user id.
     * 
     * @param userId the student's user id (email addr)
     * @return the StudentModel for the Student with the given user id
     * @throws ObjNotFoundException No student with the given user id exists

     * @throws NonRecoverableException perhaps see getCause().getErrorCode()
     */
    StudentModel findModelByUserId(String userId) throws ObjNotFoundException, 
            NonRecoverableException;
}

