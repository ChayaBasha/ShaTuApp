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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Root of all checked ShaTu application exceptions, which are logged when
 * instantiated.
 * 
 * @author Rickb
 */
public class ShaTuException extends Exception {
    /**
     * Handler for logging messages.
     */
    private static final Logger LOGGER = 
            Logger.getLogger(ShaTuException.class.getName());

    /**
     * Initialize this new instance with the given message.
     *
     * @param msg a string describing the cause of this exception.
     */
    public ShaTuException(String msg) {
	super(msg);
        
        LOGGER.log(Level.SEVERE, "ShaTuException: {0}", msg);
    }

    /**
     * Initialize this new instance with the given message and the underlying
     * Java exception that caused this ShaTu exception.
     *
     * @param msg a string describing the cause of this exception.
     * @param cause the Java exception that caused this ShaTu exception.
     */
    public ShaTuException(String msg, Throwable cause) {
	super(msg, cause);

        LOGGER.log(Level.SEVERE, msg, cause);
    }
}
