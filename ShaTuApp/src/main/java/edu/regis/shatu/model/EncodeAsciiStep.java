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
 * Data corresponding to an ENCODE_ASCII step consisting of a source, "R", a
 * target "52", possibly not specified, and associated encoding CHAR and HEX,
 * respectively
 * 
 * @author rickb
 */
public class EncodeAsciiStep {
    /**
     * The valid encoding types for the source and target.
     */
    public enum ENCODING {BINARY, CHAR, DECIMAL, HEX, OCTAL};
    
    /**
     * The query data the student is being asked to encode (e.g., "R").
     */
    private String source;
    
    /**
     * The encoding format for the source (e.g., CHAR).
     */
    private ENCODING sourceEncoding;
    
    /**
     * The encoded answer of the target data (e.g., "52"), which may not
     * be specified when the Tutor presents the step to the GUI.
     */
    private String target;
    
    /**
     * The encoding format for the target (e.g., HEX).
     */
    private ENCODING targetEncoding;

    /**
     * Initialize this step with the given source and target data.
     * 
     * @param source the data the student is being asked to encoded
     * @param target the encoding of the source in the given encoding format.
     */
    public EncodeAsciiStep(String source, String target) {
        this.source = source;
        this.target = target;
        
        sourceEncoding = ENCODING.CHAR;
        targetEncoding = ENCODING.BINARY;
    }
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ENCODING getSourceEncoding() {
        return sourceEncoding;
    }

    public void setSourceEncoding(ENCODING sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ENCODING getTargetEncoding() {
        return targetEncoding;
    }

    public void setTargetEncoding(ENCODING targetEncoding) {
        this.targetEncoding = targetEncoding;
    }
}
