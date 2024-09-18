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
package edu.regis.shatu.model.aol;

/**
 * The legal step types
 * 
 * This class is essentially kludge since I couldn't get subclassing of
 * Gson/Json subtypes to work. So, I now manually handle it. 
 * 
 * @author rickb
 */
public enum StepSubType {
    /**
     * The user must acknowledge a message (i.e., perhaps via a pop-up dialog)
     */
    INFO_MESSAGE("Information Message"),
    
    /**
    * A step in which the student must perform some GUI action, which is 
    * typically performed outside of actual tutoring for purposes of learning 
    * the GUI, such as learning to request a hint.
     */
    GUI_ACTION("GUI Action"),
    
    /**
     * A request to encode a number as binary digits
     */
    ENCODE_BINARY("Encode Binary"),
    
    /**
     * A request to encode a number as hexadecimal digits.
     */
    ENCODE_HEX("Encode Hex"),
    
    /**
     * Represents a request to 
     * 
     * Data: An EncodeAsciiExample specifying the length of the string to be
     *       encoded.
     */
    ENCODE_ASCII("ASCII Encode"),
    
    ADD_ONE_BIT("Add One Bit"),
    
    PAD_ZEROS("Pad with Zeros"),
    
    ADD_MSG_LENGTH("Add Message Length"),
    
    PREPARE_SCHEDULE("Prepare Schedule"),
    
    INITIALIZE_VARS("Initialize Variables"),
    
    COMPRESS_ROUND("Compress Round"),
    
    ROTATE_BITS("Rotate n BITS"),
    
    SHIFT_BITS("Shift Bits"),
    
    XOR_BITS("XOR Bits"),
    
    ADD_BITS("Add Bits"),
    
    MAJORITY_FUNCTION("Majority Function"),
    
    CHOICE_FUNCTION("Choice Function"),
    
    STEP_COMPLETION_REPLY("Step Completion Reply"),
    
    /**
     * The initial default value in a NewExampleRequest
     */
    DEFAULT("Unknown");
    
    /**
     * The name used by the server to identify this request.
     */
    private final String subType;
    
    /**
     * Initialize this enum object with the given title.
     * 
     * @param subType 
     */
    StepSubType(String subType) {
        this.subType = subType;
    }
    
    /**
     * Return the request name that is used by the server.
     * 
     * @return a String 
     */
    public String getSubType() {
        return subType;
    }
    
    /**
     * Return the subType name that is used by the server
     * 
     * @return a String
     */
    @Override
    public String toString() {
        return subType;
    }
}
