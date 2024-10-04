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
import edu.regis.shatu.model.Course;

/**
 * Specifies the API for Course life-cycle maintenance (database persistence).
 * Defines the methods required for retrieving and managing course data.
 * 
 * @author rickb
 */
public interface CourseSvc {

    /**
     * Locate and return the course with the given id.
     *
     * @param id  The unique identifier of the course to load.
     * @return The Course object corresponding to the given id.
     * @throws ObjNotFoundException if no course with the given id exists.
     * @throws NonRecoverableException if a SQL or database-related error occurs.
     */
    Course retrieve(int id) throws ObjNotFoundException, NonRecoverableException;
}
