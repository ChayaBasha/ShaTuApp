/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibted.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.model;

import edu.regis.shatu.model.aol.Task;
import java.util.GregorianCalendar;

/**
 * A SHA tutoring session, which is displayed in the tutor.
 * 
 * @author rickb
 */
public class TutoringSession {   
    private String securityToken = "";
    
    /**
     * The student being tutored in this session.
     * 
     * The user's password is not found in the account. 
     */
    private Account account;
    
     /**
     * True, if the session is currently active (though the student may not
     * be currently signed-in).
     */
    private boolean isActive = true;
    
    /**
     * The date and time when this session was initially created.
     */
    private GregorianCalendar startDate;
 
     /**
     * The index of the current event within this session (not the event id).
     * Defaults to 1 since all sessions have an associated creation event.
     */
    private Task task;

    /**
     * Initialize this session with default information.
     */
    public TutoringSession() {
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    /**
     * Return the student being tutored in this tutoring session.
     * 
     * @return a Student
     */
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
