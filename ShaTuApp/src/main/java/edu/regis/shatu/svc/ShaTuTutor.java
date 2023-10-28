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
import com.google.gson.GsonBuilder;
import edu.regis.shatu.err.IllegalArgException;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.Account;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.model.User;
import edu.regis.shatu.model.aol.Hint;
import edu.regis.shatu.model.aol.Step;
import edu.regis.shatu.model.aol.StepSubType;
import edu.regis.shatu.model.aol.Task;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ShaTu tutor, which implements the tutoring service.
 *
 * @author rickb
 */
public class ShaTuTutor implements TutorSvc {

    /**
     * During a sign in, indicates whether the user is new or beginning a new
     * session versus restoring an existing session.
     */
    private static final int NEW_USER_INDICATOR = -1;
    private static final int NEW_SESSION_INDICATOR = 0;

    /**
     * Handler for logging non-exception messages from this class versus thrown
     * exception, which are logged by the exception.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ShaTuTutor.class.getName());

    /**
     * The current tutoring session, which contains information on the current
     * Student, StudentModel, Course, Task, Step, etc.
     */
    private TutoringSession currentSession;

    /**
     * Currently there is only one course and its id is hardcoded here
     */
    private int courseId = 1;

    /**
     * Initialize the tutor singleton (a NoOp).
     */
    public ShaTuTutor() {
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public TutorReply request(ClientRequest clientRequest) {
        // Uses reflecto to invoke a method derived the the request name in
        // the client request (e.g., ":SignIn" invokes "signIn").
        Logger.getLogger(ShaTuTutor.class.getName()).log(Level.INFO, clientRequest.getRequestType().getRequestName());
        
        String errMsg;
        
        // Efficiently produce "signIn" from ":SignIn", for example.         
        char c[] = clientRequest.getRequestType().getRequestName().toCharArray();
        c[1] = Character.toLowerCase(c[1]);
        
        char m[] = new char[c.length - 1];
        for (int i = 1; i < c.length; i++)
            m[i - 1] = c[i];
        
        String methodName = new String(m);
        System.out.println("Method: *" + methodName + "*");
        try {
            Method method = getClass().getMethod(methodName, String.class);
            
            return (TutorReply) method.invoke(this, clientRequest.getData()); 
            
        } catch (NoSuchMethodException ex) {
            errMsg = "Tutor received an unknown request type: " + clientRequest.getRequestType().getRequestName();
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.SEVERE, errMsg, ex);
        } catch (SecurityException ex) {
            errMsg = "ShaTuTutor_ERR_2";
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.SEVERE, errMsg, ex);
        } catch (IllegalAccessException ex) {
            errMsg = "ShaTuTutor_ERR_3";
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.SEVERE, errMsg, ex);
        } catch (IllegalArgumentException ex) {
            errMsg = "ShaTuTutor_ERR_4";
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.SEVERE, errMsg, ex);
        } catch (InvocationTargetException ex) {
            errMsg = "ShaTuTutor_ERR_5";
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.SEVERE, errMsg, ex);
        }
        
        return new TutorReply(":ERR", errMsg);
    }

    /**
     * Creates a new student account
     * 
     * This method handles ":CreateAccount" requests from the GUI client.
     * 
     * @param jsonAcct a JSon encoded Account object
     * @return a TutorReply if successful the status is "Created", otherwise
     *                      the status is "ERR".
     */
    public TutorReply createAccount(String jsonAcct) {
        // Gson gson = new GsonBuilder()
          //     .registerTypeAdapterFactory(ShaTuApp.typeAdapterFactory())
            //   .create();
            Gson gson = new Gson();
        
        Account acct = gson.fromJson(jsonAcct, Account.class);
        
        try {
            ServiceFactory.findUserSvc().create(acct);

             try {
                    createSession(acct);
                } catch(NonRecoverableException e) {
                    // ToDo: Delete the user's account file since no associated
                    // session was created.
                    throw e;
                }
            
            return new TutorReply("Created");

        } catch (IllegalArgException ex) {
            // The user id already exists for another student.
            return new TutorReply("IllegalUserId");
        

        } catch (NonRecoverableException e) {
            return new TutorReply();
        }
    }

    /**
     * Attempts to sign a student in.
     * 
     *  This method handles ":SignIn" requests from the GUI client.
     * 
     * @param jsonUser a JSon encoded User object
     * @return a TutorReply, if successful, the status is "Authenticated" with
     *           data being a JSon encoded TutoringSession object.
     */
    public TutorReply signIn(String jsonUser) {
        System.out.println("Received sign in: " + jsonUser);
         Gson gson = new GsonBuilder()
              // .registerTypeAdapterFactory(ShaTuApp.typeAdapterFactory())
                 .setPrettyPrinting()
              .create();
        // Gson gson = new Gson();
        
        User user = gson.fromJson(jsonUser, User.class
        );

        try {
            User dbUser = ServiceFactory.findUserSvc().findById(user.getUserId());
 
            if (dbUser.getPassword().equals(user.getPassword())) {
                SessionSvc svc = ServiceFactory.findSessionSvc();
                TutoringSession session = svc.findById(user.getUserId());

                TutorReply reply = new TutorReply("Authenticated");
           
                reply.setData(gson.toJson(session));

                return reply;

            } else {
                return new TutorReply("InvalidPassword");
          
            }

        } catch (ObjNotFoundException e) {
            return new TutorReply("UnknownUser");
           

        } catch (NonRecoverableException ex) {
            Logger.getLogger(ShaTuTutor.class
                    .getName()).log(Level.SEVERE, null, ex);
            return new TutorReply();
         
        }
    }

    /**
     * Returns a hint to the GUI client, if any
     * 
     *  This method handles ":RequestHint" requests from the GUI client.
     * 
     * @param sessionInfo a 
     * @return a TutorReply, if successful, the status is "Hint" with
     *           data being a displayable hint text string.
     */
    public TutorReply requestHint(String sessionInfo) {
        // ToDo: this is simply a hard coded test case
        TutorReply reply = new TutorReply("Hint");
        reply.setData("This is a hint from the tutor.");
        
        return new TutorReply();
    }


    public TutorReply completedStep(String stepInfo) {
        return new TutorReply();
    }

    public TutorReply completedTask(String taskInfo) {
       return new TutorReply();
    }
    
        /**
     * Create and save a new tutoring session associated with the given account.
     * 
     * @param account the student user
     * @throws NonRecoverableException 
     */
    private void createSession(Account account) throws NonRecoverableException {
        TutoringSession session = new TutoringSession();
        session.setAccount(account);
                
        Random rnd = new Random();
        String clearToken = "Session" + account.getUserId() + Integer.toString(rnd.nextInt());
        
        session.setSecurityToken(SHA_256.instance().sha256(clearToken));
        
        //ToDo: hardcoded instead read from DB
        ArrayList<Step> steps = new ArrayList<>();
        Step step = new Step(0,0,StepSubType.COMPLETE_STEP);
        step.setDescription("Encode the string in Ascii");
        
        //System.out.println("In tutor: step: " + step.getMyTypeName());
        
        Hint hint = new Hint();
        hint.setSequenceId(1);
        hint.setText("Encode the first Character of the String");
        step.addHint(hint);
        
        hint = new Hint();
        hint.setSequenceId(2);
        hint.setText("Use the ASCII lookup table For example 'A' -> 65");
        step.addHint(hint);
        
        steps.add(step);
        
        Task task = new Task(1);
        task.setSteps(steps);
        task.setDescription("Encode the String as Ascii");
        
        
        session.setTask(task);

        ServiceFactory.findSessionSvc().create(session);
    }
}
