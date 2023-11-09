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
package edu.regis.shatu.dao;

import edu.regis.shatu.err.IllegalArgException;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.Student;
import edu.regis.shatu.model.aol.StudentModel;
import edu.regis.shatu.svc.StudentSvc;

/**
 * A MySql-based Data Access Object implementing {@link StudentSvc} behaviors.
 * 
 * @author rickb
 */
public class StudentDAO implements StudentSvc {
    
    @Override
    public void create(Student student) throws IllegalArgException, NonRecoverableException {
	

       
    }
    
    @Override
    public boolean exists(String userId) throws NonRecoverableException {
       return false;
    }
    
    @Override
    public Student findStudentById(String userId) throws ObjNotFoundException, NonRecoverableException {
       return null;
    }
    
    
    
    public StudentModel findModelByUserId(String userId) throws ObjNotFoundException, NonRecoverableException {
      return null;
    }
}


