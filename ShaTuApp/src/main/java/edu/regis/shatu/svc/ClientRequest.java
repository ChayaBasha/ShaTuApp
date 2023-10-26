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
package edu.regis.shatu.svc;

/**
 * A decorator that wraps a user interface request being sent to the tutor
 * (sever), which includes the type of request being mad.
 * 
 * The request type specifies how to interpret the JSon encoded data.
 * 
 * @author rickb
 */
public class ClientRequest {    
    /**
     * The specific type of request being made by the client.
     */
    private ServerRequestType requestType;
    
    /**
     * A JSon encoded object whose format depends on the associated request.
     * (See the requestType documentation.)
     */
    private String data = "";
    
    /**
     * Initialize an empty client request.
     */
    public ClientRequest() {   
    }

    public ServerRequestType getRequestType() {
        return requestType;
    }

    public void setRequest(ServerRequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * Return the data associated with this request.
     * 
     * @return a JSon encoded object corresponding to the request type.
     *         See the documentation for the data field.
     */
    public String getData() {
        return data;
    }

    /**
     * Set the data.
     * 
     * @param data a JSon encoded object corresponding to the request type.
     *             See the documentation for the data field.
     */
    public void setData(String data) {
        this.data = data;
    }
}