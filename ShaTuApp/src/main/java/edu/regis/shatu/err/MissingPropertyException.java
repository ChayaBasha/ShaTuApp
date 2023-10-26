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
 * Thrown when a property cannot be found in a "*.properties" file.
 * 
 * @author rickb
 */
public class MissingPropertyException extends ShaTuException {
    /**
     * Construct this new instance with the given message.
     *
     * @param property the property key (e.g. "edu.regis.shatu.DebugLevel")
     */
    public MissingPropertyException(String property) {
	super("Missing property " + property);
    }

    /**
     * Initialize this new instance with the given message and
     * and java exception that initially caused this exception.
     *
     * @param property the property key (e.g. "edu.regis.shatu.DebugLevel")
     * @param cause the Java exception that caused this exception.
     */
    public MissingPropertyException(String property, Throwable cause) {
	super(("Missing property " + property), cause);
    }
}

