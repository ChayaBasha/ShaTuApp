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
 * An unexpected exception that the user cannot recovered from.
 * 
 * Typically, this exception is caused by a low-level Java exception, which
 * case the getCause() method of the exception can be used to see the likely
 * cause. In the case of actual MySQL database exceptions, the getErrorCode() 
 * method often contains the database issue.
 * 
 * @author rickb
 */
public class NonRecoverableException extends ShaTuException {
    public NonRecoverableException(String msg) {
	super(msg);
      }

    public NonRecoverableException(String msg, Throwable cause) {
	super(msg, cause);
    }
}
