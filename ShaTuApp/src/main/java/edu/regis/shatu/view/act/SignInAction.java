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
package edu.regis.shatu.view.act;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.regis.shatu.model.User;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.svc.ClientRequest;
import edu.regis.shatu.svc.ServerRequestType;
import edu.regis.shatu.svc.SvcFacade;
import edu.regis.shatu.svc.TutorReply;
import edu.regis.shatu.view.MainFrame;
import edu.regis.shatu.view.SplashFrame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import static javax.swing.Action.MNEMONIC_KEY;
import static javax.swing.Action.SHORT_DESCRIPTION;

/**
 * An (MVC) controller handling a GUI gesture representing a user's request to
 * login to the tutor via the WelcomePanel.
 *
 * If successful, a trial will be started or resumed for the student via
 * launch session.
 * 
 * @author rickb
 */
public class SignInAction extends ShaTuGuiAction {
    /**
     * Exceptions occurring in this class are also logged to this logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(SignInAction.class.getName());

    /**
     * The single instance of this sign-in action.
     */
    private static final SignInAction SINGLETON;

    /**
     * Create the singleton for this action, which occurs when this class
     * is loaded by the Java class loaded, as a result of the class being 
     * referenced by executing SignInAction.instance() in the 
     * initializeComponents() method of the SplashPanel class.
     */
    static {
        SINGLETON = new SignInAction();
    }

    /**
     * Return the singleton instance of this sign-in action.
     *
     * @return
     */
    public static SignInAction instance() {
        return SINGLETON;
    }
    
    private Gson gson;

    /**
     * Initialize action with the "Sign In" text and set its text.
     */
    private SignInAction() {
        super("Sign In");

        putValue(SHORT_DESCRIPTION, "Sign-in to the tutor");
        
        
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        //putValue(ACCELERATOR_KEY, getAcceleratorKeyStroke());
        
        gson = new Gson();
    }

    /**
     * Handle the user's request to sign-in by sending it to the DICE tutor.
     * 
     * If successful, the MaingFrame with the Courtroom View is displayed.
     *
     * @param evt ignored
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        Gson gson = new GsonBuilder()
              .setPrettyPrinting()
              .create();
        
        User user = SplashFrame.instance().getUser();

        ClientRequest request = new ClientRequest();
        request.setRequest(ServerRequestType.SIGN_IN);
        request.setData(gson.toJson(user));
        
        TutorReply reply = SvcFacade.instance().tutorRequest(request);

        String status = reply.getStatus();
            
        if (status.equals("Authenticated")) {            
           MainFrame frame = MainFrame.instance();
           TutoringSession session = gson.fromJson(reply.getData(), TutoringSession.class);     
           
           SplashFrame.instance().setVisible(false);
                
           frame.setVisible(true);
  
           frame.setModel(session);

        } else if (status.equals("InvalidPassword")) {
            SplashFrame.instance().invalidPass();

        } else if (status.equals("UnknownUser")) {
            SplashFrame.instance().unknownUser();

        } else {
            // If we get here, there is a coding error in the tutor svc
            //frame.displayError("Ooops, an unexpected error occurred: SI_1");
            System.out.println("Coding error  status: " + status);
        }
        
    }

}