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

/**
 * A convenience class that is the result of decoding a JSon object passed
 * in a create student account message sent to the tutor.
 * 
 * This allows us to handle simplified students without loading the associated 
 * student model.
 * 
 * @author rickb
 */
public class StudentUser extends User {
    /**
     * The first name of the student
     */
    private String firstName;
    
    /**
     * The last name of the student.
     */
    private String lastName;
    
    public StudentUser() {  // needed for fromJson
        this("");
    }
    
    public StudentUser(String userId) {
        super(userId);
    }
    
    public StudentUser(String userId, String password) {
        super(userId, password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
}
