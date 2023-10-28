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
package edu.regis.shatu.err;

/**
 * Thrown when there is an inconsistency in the database, which should never
 * happen (a programmer error).
 * 
 * @author rickb
 */
public class InconsistentDBException extends ShaTuException {
    public InconsistentDBException(String msg) {
        super(msg);
    }
    
    public InconsistentDBException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
