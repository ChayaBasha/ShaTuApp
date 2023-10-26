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
import edu.regis.shatu.err.ShaTuException;
import edu.regis.shatu.model.aol.Course;

/**
 * Specifies the API for Course life-cycle maintenance (database persistence).
 * 
 * @author rickb
 */
public interface CourseSvc {
    /**
     * Locate and return the course with the given id.
     *
     * @param id  integer key of the course to load.
     * @return The course with the given id.
     * @exception ObjNotFoundException No course with the given id exists.
     * @throws NonRecoverableException see the documentation for this exception.
     */
    Course findById(int id) throws ObjNotFoundException, NonRecoverableException;
}
