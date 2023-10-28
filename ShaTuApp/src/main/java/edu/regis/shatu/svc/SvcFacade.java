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

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Facade that is used to standardize all requests from GUI Client to the 
 * tutor as a single JSon encoded object String.
 *
 * Conceptually, this facade is part of the Swing-based GUI client.
 *
* This facade exists to facilitate a subsequent port from the Swing-based GUI
 * to a Restful HTML client. As such, it is currently a logical part of the 
 * Swing-based GUI client, but for a Web-Browser client, it would be implemented
 * as part of the Server Socket Connection, which would forward the HTTML JSon 
 * HttpReqeust to the tutor using POJO message passing (i.e. method invocation).
 * 
 * Currently, the following request types are supported:
 * {"request": "CreateStudentAccount",
 *  "data": {"userId":"userId@regis.edu", 
 *           "password":"sha256Password",
 *           "firstName":"Rick",
 *           "lastName":"Justice"
 *          }
 * }
 * 
 * {"request": "SignIn",
 *  "data":  {"userId": "user email addr"}
 * }
 *
 * @author rickb
 */
public class SvcFacade {
    /**
     * The single instance of the tutor facade.
     */
    private static final SvcFacade SINGLETON;

    /**
     * Create the singleton for this facade, which occurs when this class
     * is loaded by the Java class loaded, as a result of the class being 
     * referenced be the SvcFacade.instance() method occurring initially
     * in the SignInAction class.
     */
    static {
        SINGLETON = new SvcFacade();
    }
    
    /**
     * Return the singleton implementing the TutorFacade interface.
     * 
     * @return a TutorFacade
     */
    public static SvcFacade instance() {
        return SINGLETON;
    }
    
    /**
     * Handler for logging messages.
     */
    private static final Logger LOGGER = Logger.getLogger(SvcFacade.class.getName());
    
    /**
     * The computer host to which ShaTu tutor requests are delegated.
     * 
     * That is, the ShaTuServer is executing on this host.
     */
    private static final String SERVER = "localhost";
    
    /**
     * Socket port supporting the ShaTu tutoring service protocol
     */
    private static final int PORT = 53636;
    
    
    private SvcFacade() {  
    }

    
    public TutorReply tutorRequest(ClientRequest request) {
        Gson gson = new Gson();
        // ToDo: remove debugging stmt.
        String jsonRequest = gson.toJson(request);
        System.out.println("*** jasonRequest *" + jsonRequest + "*");
        
        //ClientRequest request = gson.fromJson(jsonRequest, ClientRequest.class);
  
        String jsonReply = send(jsonRequest);
        
        System.out.println("*** jsonReply: " + jsonReply);
        
        return gson.fromJson(jsonReply, TutorReply.class);
        

    }
    
     /**
     * Send a request to the server to encrypt (decrypt) the given message
     * 
     * @param cmd 
     * @param msg 
     * @return 
     */
    private String send(String request) {
        Socket client = null;
        BufferedReader in = null;
        PrintWriter out = null;
        
        try {
            client = new Socket(SERVER, PORT);
            
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            out = new PrintWriter(client.getOutputStream());
            out.println(request);
            //out.println(cmd + " " + msg);
            out.flush();
            
            return in.readLine();
            
        } catch (UnknownHostException e) {
            LOGGER.log(Level.SEVERE, "Unknown Host", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException client", e);
        } finally {
            // About as ugly as it gets, but the following code ensures that
            // we've at least tried to close an open socket and its associated
            // input and output streams in every possible error scenario
            // If we didn't, it's possible that we're leaking memory.
            try {
                if (out != null)
                    out.close();
            } finally {
                try {
                    if (in != null)
                        in.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Unable to close client socket in", e);
                } finally {
                    try {
                        if (client != null)
                            client.close();
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Unable to close client socket in", e);
                    }
                }
            }
        }
        
        return ":error";
    }
}

