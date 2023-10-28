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

package edu.regis.shatu;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import edu.regis.shatu.model.aol.CompleteStepStep;
import edu.regis.shatu.model.aol.CompleteTaskStep;
import edu.regis.shatu.model.aol.EncodeAsciiStep;
import edu.regis.shatu.model.aol.Step;
import edu.regis.shatu.svc.ShaTuServer;
import edu.regis.shatu.util.ResourceMgr;
import edu.regis.shatu.view.MainFrame;
import edu.regis.shatu.view.SplashFrame;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * A standalone implementation of the SHA-256 intelligent tutoring application.
 * @author rickb
 */
public class ShaTuApp {
     /**
     * Property file located on the CLASSPATH, which is used to configure the LOGGER.
     */
    private static final String LOGGER_PROPERTIES = "/Logging.properties";
    // ./resources/logging.properties
    /**
     * Events of interest occurring in this class are logged to this logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ShaTuApp.class.getName());
    
     /**
     * When deserializing a polymorphic Step object, GSon requires the subclass
     * step type in order to create the correct subclass instance, this type
     * adapter factory provides this information (see its use in SignInAction).
     */
//    private static final RuntimeTypeAdapterFactory<Step> STEP_ADAPTER_FACTORY =
//            RuntimeTypeAdapterFactory.of(Step.class, "type")
//                .registerSubtype(EncodeAsciiStep.class, "EncodeAsciiStep")
//                .registerSubtype(CompleteTaskStep.class, "CompleteTaskStep")
//                .registerSubtype(CompleteStepStep.class, "CompleteStepStep");
    
    /**
     * Convenience providing access to the Step Type Adapter Factory (see
     * comments documenting the static STEP_ADAPTER_FACTORY field).
     * 
     * @return 
     */
//    public static RuntimeTypeAdapterFactory<Step> typeAdapterFactory() {
//        return STEP_ADAPTER_FACTORY;
//    }
    
    /**
     * Main entry point for the ShaTut application, which will display the UI.
     * 
     * @param args ignored
     */
    public static void main(String[] args) {
        LOGGER.info("ShaTuApp Initializing...:");
        
        try {
            final InputStream strm = ShaTuApp.class.getResourceAsStream(LOGGER_PROPERTIES);
        
            LogManager.getLogManager().readConfiguration(strm);
            
            LOGGER.info("Message logging initialization completed.");
        } catch (IOException e) {
        
            LOGGER.severe("Error loading ./logging.properties");
            LOGGER.severe(e.getMessage());
        }      
        
        // Initializes the properties from ShaTu.properties and sets the locale.
        ResourceMgr.instance();
        
        LOGGER.info("ShaTu properties initialization completed.");
        
        // ToDo: Remove this example of using the SHA-256 algorithm.
        //  SHA_256 alg = new SHA_256();
        //alg.doit("Regis Computer Science Rocks!");
        
        try {
            LOGGER.info(" Starting ShaTu Server (Tutoring Service)...");
            // ToDo: Separate the initialization of client and server
            // Start the socket server for the ShaTu tutor.
            (new Thread(new ShaTuServer())).start();
            
            // ToDo: This puts the main client UI thread to sleep to give the 
            // server a chance to finish starting. This won't be required once 
            // we separate the server into its own application that is separate
            // from the GUI client since the server should "always" be running.
            Thread.sleep(4000);
                
            LOGGER.info(" Server is running.");
                
            LOGGER.info(" Starting Client GUI...");
            
            // Force the creation of the MainFrame singleton, which is not
            // made visible to the user until after they sign-in.
            MainFrame.instance();

            // Force the creation of the SplashFrame, which is displayed and
            // allows the user to sign-in or create a new student account.
            // If sign-in is successful the MainFrame is displayed.
            SplashFrame.instance();            
                
            LOGGER.info("ShaTu Initialization successful.");
                
            } catch (InterruptedException ex) {
                Logger.getLogger(ShaTuApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException e) {
            LOGGER.severe("Couldn't create Data directory in NetBeans Project.");
            LOGGER.severe("Perhaps, try changing permissions.");
        }
    }
}
