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

import edu.regis.shatu.model.aol.NewExampleRequest;
import edu.regis.shatu.model.aol.EncodeAsciiExample;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.regis.shatu.err.IllegalArgException;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.Account;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.model.User;
import edu.regis.shatu.model.aol.EncodeAsciiStep;
import edu.regis.shatu.model.aol.Hint;
import edu.regis.shatu.model.aol.ScaffoldLevel;
import edu.regis.shatu.model.aol.Step;
import edu.regis.shatu.model.aol.StepSubType;
import edu.regis.shatu.model.aol.Task;
import edu.regis.shatu.model.aol.TaskKind;
import edu.regis.shatu.model.aol.TaskState;
import edu.regis.shatu.model.aol.Timeout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
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
     * The maximum number of characters allowed for encoding a example ASCII
     * encoding request from the student.
     */
    private static final int MAX_ASCII_SIZE = 20;

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
     * Convenience reference to the current gson object.
     */
    private Gson gson;

    TutoringSession session;

    /**
     * Initialize the tutor singleton (a NoOp).
     */
    public ShaTuTutor() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TutorReply request(ClientRequest request) {
        // Uses reflecto to invoke a method derived the the request name in
        // the client request (e.g., ":SignIn" invokes "signIn").
        Logger.getLogger(ShaTuTutor.class.getName()).log(Level.INFO, request.getRequestType().getRequestName());

        // Efficiently produce "signIn" from ":SignIn", for example.         
        char c[] = request.getRequestType().getRequestName().toCharArray();
        c[1] = Character.toLowerCase(c[1]);

        char m[] = new char[c.length - 1];
        for (int i = 1; i < c.length; i++) {
            m[i - 1] = c[i];
        }

        String methodName = new String(m);
        System.out.println("Method: *" + methodName + "*");

        // Most methods require verify the given security token with the known one.
        switch (methodName) {
            case "completedStep":
            case "completedTask":
            case "newExample":
            case "requestHint":
                try {
                session = verifySession(request.getUserId(), request.getSessionId());

            } catch (ObjNotFoundException ex) {
                return createError("No session exists for user: " + request.getUserId(), ex);
            } catch (IllegalArgException ex) {
                return createError("Illegal session for user: " + request.getUserId(), ex);
            } catch (NonRecoverableException ex) {
                return createError(ex.toString(), ex);
            }
            String msg = "Session verified for " + request.getUserId();
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.INFO, msg);
            break;

            default:
                Logger.getLogger(ShaTuTutor.class.getName()).log(Level.INFO, "No token verification required");
        }

        // Security token has been verified or not required (e.g., signIn).
        try {
            Method method = getClass().getMethod(methodName, String.class);

            return (TutorReply) method.invoke(this, request.getData());

        } catch (NoSuchMethodException ex) {
            return createError("Tutor received an unknown request type: " + request.getRequestType().getRequestName(), ex);
        } catch (SecurityException ex) {
            return createError("ShaTuTutor_ERR_2", ex);
        } catch (IllegalAccessException ex) {
            return createError("ShaTuTutor_ERR_3", ex);
        } catch (IllegalArgumentException ex) {
            return createError("ShaTuTutor_ERR_4", ex);
        } catch (InvocationTargetException ex) {
            return createError("ShaTuTutor_ERR_5", ex);
        }
    }

    /**
     * Creates a new student account
     *
     * This method handles ":CreateAccount" requests from the GUI client.
     *
     * @param jsonAcct a JSon encoded Account object
     * @return a TutorReply if successful the status is "Created", otherwise the
     * status is "ERR".
     */
    public TutorReply createAccount(String jsonAcct) {
        // Gson gson = new GsonBuilder()
        //     .registerTypeAdapterFactory(ShaTuApp.typeAdapterFactory())
        //   .create();
        gson = new GsonBuilder().setPrettyPrinting().create();

        Account acct = gson.fromJson(jsonAcct, Account.class);

        try {
            ServiceFactory.findUserSvc().create(acct);

            try {
                createSession(acct);
            } catch (NonRecoverableException e) {
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
     * This method handles ":SignIn" requests from the GUI client.
     *
     * @param jsonUser a JSon encoded User object
     * @return a TutorReply, if successful, the status is "Authenticated" with
     * data being a JSon encoded TutoringSession object.
     */
    public TutorReply signIn(String jsonUser) {
        System.out.println("Received sign in: " + jsonUser);
        gson = new GsonBuilder()
                // .registerTypeAdapterFactory(ShaTuApp.typeAdapterFactory())
                .setPrettyPrinting()
                .create();

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
     * This method handles ":RequestHint" requests from the GUI client.
     *
     * @param sessionInfo a
     * @return a TutorReply, if successful, the status is "Hint" with data being
     * a displayable hint text string.
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
     * Handles :NewExample requests from the client.
     *
     * @param json a JSon String encoding a NewExampleRequest object
     * @return TutorReply
     */
    public TutorReply newExample(String json) {
        gson = new GsonBuilder().setPrettyPrinting().create();

        NewExampleRequest request = gson.fromJson(json, NewExampleRequest.class);

        switch (request.getExampleType()) {
            case ASCII_ENCODE:
                return newAsciiEncodeExample(session, request.getData());

            case ADD_ONE_BIT:
                return newAddOneBitExample(session, request.getData());

            case PAD_ZEROS:
                return newPadZerosExample(session, request.getData());

            case ADD_MSG_LENGTH:
                return newAddMsgLenExample(session, request.getData());

            case PREPARE_SCHEDULE:
                return newPrepareScheduleExample(session, request.getData());

            case INITIALIZE_VARS:
                return newInitializeVarsExample(session, request.getData());

            case COMPRESS_ROUND:
                return newCompressRoundExample(session, request.getData());

            case ROTATE_BITS:
                return newRotateBitsExample(session, request.getData());

            case SHIFT_BITS:
                return newShiftBitsExample(session, request.getData());

            case XOR_BITS:
                return newXorBitsExample(session, request.getData());

            case ADD_BITS:
                return newAddBitsExample(session, request.getData());

            case MAJORITY_FUNCTION:
                return newMajorityFunctionExample(session, request.getData());

            case CHOICE_FUNCTION:
                return newChoiceFunctionExample(session, request.getData());

            default:
                return createError("Unknown example request: " + request.getExampleType(), null);
        }
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
        Step step = new Step(0, 0, StepSubType.COMPLETE_STEP);
        step.setScaffolding(ScaffoldLevel.EXTREME);
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
        task.setDescription("The first task in the SHA-256 algorithm is to encode the input string as an ASCII byte string. As an example considerthe string \"Regis Computer Science Rocks!\"");

        session.setTask(task);

        ServiceFactory.findSessionSvc().create(session);
    }

    /**
     * Verify that the user with the given id has a session with the given
     * session id.
     *
     * @param userId String "user@regis.edu"
     * @param sessionId String identifying a previously generated session id.
     * @return the current TutoringSession associated with the given user id and
     * session id
     */
    private TutoringSession verifySession(String userId, String sessionId)
            throws ObjNotFoundException, IllegalArgException, NonRecoverableException {

        SessionSvc svc = ServiceFactory.findSessionSvc();
        TutoringSession session = svc.findById(userId);

        if (session.getSecurityToken().equals(sessionId)) {
            return session;
        } else {
            throw new IllegalArgException("Illegal session id for user: " + userId);
        }
    }

    /**
     * Handles client requests for a new ASCII encode example.
     *
     * @return a TutorReply whose data contains a JSon EncodeAsciiExample
     * object.
     */
    private TutorReply newAsciiEncodeExample(TutoringSession session, String jsonData) {
        Random rnd = new Random();

        EncodeAsciiExample example = gson.fromJson(jsonData, EncodeAsciiExample.class);

        int strLen = example.getStringLength();

        if (strLen == 0) {
            // ToDo: The tutor should generate the string length and timeout
            // based on the the current student model.
            strLen = rnd.nextInt(MAX_ASCII_SIZE - 1) + 1;
            example.setTimeOut(600);

        } else if (strLen > MAX_ASCII_SIZE) {
            // The student is requesting practice for a specific string length.
            strLen = MAX_ASCII_SIZE;
            example.setTimeOut(0);
        }

        int[] encoding = new int[strLen];

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strLen; i++) {
            // Printable ASCII in range 32 ' ' to 126 '-'
            encoding[i] = rnd.nextInt((126 - 32) + 1) + 32;
            builder.append((char) encoding[i]);
        }

        example.setStringLength(strLen);
        example.setExampleString(builder.toString());
        example.setAsciiEncoding(encoding);

        EncodeAsciiStep subStep = new EncodeAsciiStep();
        subStep.setExample(example);

        //ToDo: multistep should be determined by the student model.
        subStep.setMultiStep(rnd.nextBoolean());

        Step step = new Step(1, 0, StepSubType.ENCODE_ASCII);
        step.setCurrentHintIndex(0);
        step.setNotifyTutor(true);
        step.setIsCompleted(false);
        // ToDo: fix timeouts
        Timeout timeout = new Timeout("Complete Step", 0, ":No-Op", "Exceed time");
        step.setTimeout(timeout);

        step.setData(gson.toJson(subStep));

        // TaskState state = new TaskState();
        // state.set
        Task task = new Task();
        task.setTaskType(TaskKind.PROBLEM);
        task.setDescription("Encode a string as ASCII values");
        task.addStep(step);

        // ToDo: Add the task to the session and update it.
        TutorReply reply = new TutorReply(":Success");
        reply.setData(gson.toJson(task));

        return reply;
    }

    /**
     * Handles client requests for a new add one bit example.
     *
     * @return a TutorReply
     */
    private TutorReply newAddOneBitExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new pad with zeros example.
     *
     * @return a TutorReply
     */
    private TutorReply newPadZerosExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new add message length example.
     *
     * @return a TutorReply
     */
    private TutorReply newAddMsgLenExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new prepare schedule example.
     *
     * @return a TutorReply
     */
    private TutorReply newPrepareScheduleExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new initialize vars example.
     *
     * @return a TutorReply
     */
    private TutorReply newInitializeVarsExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new compress round example.
     *
     * @return a TutorReply
     */
    private TutorReply newCompressRoundExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new rotate bits example.
     *
     * @return a TutorReply
     */
    private TutorReply newRotateBitsExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new shift bits zeros example.
     *
     * @return a TutorReply
     */
    private TutorReply newShiftBitsExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new XOR bits example.
     *
     * @return a TutorReply
     */
    private TutorReply newXorBitsExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new add bits example.
     *
     * @return a TutorReply
     */
    private TutorReply newAddBitsExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new majority function example.
     *
     * @return a TutorReply
     */
    private TutorReply newMajorityFunctionExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Handles client requests for a new choice function example.
     *
     * @return a TutorReply
     */
    private TutorReply newChoiceFunctionExample(TutoringSession session, String jsonData) {
        TutorReply reply = new TutorReply(":Success");

        return reply;
    }

    /**
     * Utility for logging an error and an creating a tutoring reply error with
     * the given message, and optional originating exception.
     *
     * @param errMsg a displayable error message
     * @param ex the original exception, if any, that caused the error,
     * otherwise null.
     * @return a TutorReply with an ":ERR" status
     */
    private TutorReply createError(String errMsg, Exception ex) {
        if (ex == null) {
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.SEVERE, errMsg);
        } else {
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.SEVERE, errMsg, ex);
        }

        return new TutorReply(":ERR", errMsg);
    }
}
