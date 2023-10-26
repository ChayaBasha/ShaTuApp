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
package edu.regis.shatu.model;

import edu.regis.shatu.model.aol.StudentModel;

/**
 * A Student user who is being tutored by the ShaTu tutor.
 * 
 * @author rickb
 */
public class Student extends User {    
    /**
     * The first name of this user.
     */
    protected String firstName;
    
    /**
     * The last name of this StudentUser.
     */
    protected String lastName;
    
    /**
     * Convenience reference to this Student's student model.
     */
    private StudentModel studentModel;
    
    public Student() {
        this("test@regis.edu", "HelloWorld");
    }
  
    /**
     * Initialize this Student with a default student model and the given user
     * id and password.
     * 
     * @param userId a String (e.g. "name@university.edu").
     * @param password an SHA-256 encrypted password.
     */
    public Student(String userId, String password) { 
        studentModel = new StudentModel();
    }

    /**
     * Return this StudentUser's first name.
     * @return the name String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Assign this StudentUser's first name.
     * @param firstName the name String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return this StudentUser's last name
     * @return the name String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Assign this StudentUser's last name.
     * @param lastName the name String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Return this student's student model
     * 
     * @return a StudentModel
     */
    public StudentModel getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    @Override
    public String toString() {
        return "Student: " + userId;
    }
}