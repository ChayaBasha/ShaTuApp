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
import edu.regis.shatu.model.Course;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.model.User;
import edu.regis.shatu.model.aol.BitOpExample;
import edu.regis.shatu.model.aol.BitOpStep;
import edu.regis.shatu.model.aol.EncodeAsciiStep;
import edu.regis.shatu.model.Hint;
import edu.regis.shatu.model.KnowledgeComponent;
import edu.regis.shatu.model.aol.ScaffoldLevel;
import edu.regis.shatu.model.Step;
import edu.regis.shatu.model.Student;
import edu.regis.shatu.model.aol.StepSubType;
import edu.regis.shatu.model.Task;
import edu.regis.shatu.model.aol.TaskKind;
import edu.regis.shatu.model.aol.Timeout;
import edu.regis.shatu.model.Unit;
import edu.regis.shatu.model.aol.Assessment;
import edu.regis.shatu.model.aol.AssessmentLevel;
import edu.regis.shatu.model.aol.StudentModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
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
     * The id of the default course taught by the this tutor.
     */
    private static final int DEFAULT_COURSE_ID = 1;
    /**
     * The maximum number of characters allowed for encoding a example ASCII
     * encoding request from the student.
     */
    private static final int MAX_ASCII_SIZE = 20;

    private static final int MAX_BITS_SIZE = 32;

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
    private TutoringSession session;

    /**
     * Convenience reference to the current gson object.
     */
    private Gson gson;

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
        // Uses reflection to invoke a method derived from the request name in
        // the client request (e.g., ":SignIn" invokes "signIn(...)").
        Logger.getLogger(ShaTuTutor.class.getName()).log(Level.INFO, request.getRequestType().getRequestName());

        // Efficiently produce "signIn" from ":SignIn", for example.         
        char c[] = request.getRequestType().getRequestName().toCharArray();
        c[1] = Character.toLowerCase(c[1]);

        char m[] = new char[c.length - 1];
        for (int i = 1; i < c.length; i++) {
            m[i - 1] = c[i];
        }

        String methodName = new String(m);

        // Most methods require verifying the given security token with the known one.
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
                return createError("Illegal session token sent for user: " + request.getUserId(), ex);
            } catch (NonRecoverableException ex) {
                return createError(ex.toString(), ex);
            }

            String msg = "Session verified for " + request.getUserId();
            Logger.getLogger(ShaTuTutor.class.getName()).log(Level.INFO, msg);
            break;

            default: // e.g., signIn itself, newAccount
                Logger.getLogger(ShaTuTutor.class.getName()).log(Level.INFO, "No token verification required");
        }

        // Security token has been verified or not required (e.g., signIn, createAccount).
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
    public TutorReply createAccount(String jsonAcct) throws NonRecoverableException {
        gson = new GsonBuilder().setPrettyPrinting().create();

        Account acct = gson.fromJson(jsonAcct, Account.class);

        int courseId = DEFAULT_COURSE_ID; // Currently only one course

        StudentSvc stuSvc = ServiceFactory.findStudentSvc();

        if (stuSvc.exists(acct.getUserId()))
            return new TutorReply("IllegalUserId");

        try {
            ServiceFactory.findUserSvc().create(acct);

            try {
                CourseSvc courseSvc = ServiceFactory.findCourseSvc();

                Course course = courseSvc.retrieve(courseId);

                session = createSession(acct, course);

                createStudent(acct, course, session);

                return new TutorReply("Created");

            } catch (ObjNotFoundException ex) {
                return createError("Unknown course: " + courseId, null);
            }

        } catch (IllegalArgException ex) {
            // Should never get here since we tested whether the account exists
            return new TutorReply("IllegalUserId");
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
                .setPrettyPrinting()
                .create();

        User user = gson.fromJson(jsonUser, User.class
        );

        try {
            User dbUser = ServiceFactory.findUserSvc().retrieve(user.getUserId());

            if (dbUser.getPassword().equals(user.getPassword())) {
                SessionSvc svc = ServiceFactory.findSessionSvc();
                TutoringSession session = svc.retrieve(user.getUserId());

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
     * @return the new TutoringSession
     */
    private TutoringSession createSession(Account account, Course course) throws NonRecoverableException {
        try {
            Task task = getFirstTask(course);

            TutoringSession tSession = new TutoringSession();
            tSession.setAccount(account);

            Random rnd = new Random();
            String clearToken = "Session" + account.getUserId() + Integer.toString(rnd.nextInt());
            tSession.setSecurityToken(SHA_256.instance().sha256(clearToken));

            tSession.setCourse(course.getDigest());

            Unit unit = course.currentUnit();
            if (unit != null)
                tSession.setUnit(unit.getDigest());
         
            tSession.addTask(task);

            ServiceFactory.findSessionSvc().create(tSession);

            return tSession;

        } catch (IllegalArgException ex) {
            // Should never get here
            throw new NonRecoverableException("Session already exists " + account.getUserId());
        }
    }

    /**
     * Create and save the student and their initial student model.
     *
     * @param acct
     * @param course
     * @return
     */
    private Student createStudent(Account acct, Course course, TutoringSession session)
            throws NonRecoverableException {
        
        Student student = new Student(acct.getUserId(), acct.getPassword());
        StudentModel model = student.getStudentModel();

        try {
            // As the student has at least one task and step to complete,
            // add the associated knowledge component assessment(s) to the 
            // student model of the student.
            HashSet<Integer> componentIds = new HashSet<>();
            for (Task task : session.getTasks()) {
                for (int componentId : task.getExercisedComponentIds()) {
                    componentIds.add(componentId);
                }

                for (Step step : task.getSteps()) {
                    for (int cid : step.getExercisedComponentIds()) {
                        componentIds.add(cid);
                    }
                }
            }

            for (int id : componentIds) {
                if (!model.containsAssessment(id)) {
                    KnowledgeComponent comp = course.findKnowledgeComponent(id);
                    model.addAssessment(id, new Assessment(comp, AssessmentLevel.VERY_LOW));
                    
                    
                }
            }

            StudentSvc svc = ServiceFactory.findStudentSvc();
            svc.create(student);

            return student;

        } catch (IllegalArgException e) {
            // We should never get here since 
            throw new NonRecoverableException("Student already exists " + acct.getUserId());

        } catch (ObjNotFoundException e) {
            throw new NonRecoverableException("Inconsistent Course in DB knowledge component" + course.getId());
        }

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
        TutoringSession locSession = svc.retrieve(userId);

        if (locSession.getSecurityToken().equals(sessionId)) {
            return locSession;
        } else {
            throw new IllegalArgException("Illegal session id for user: " + userId);
        }
    }

    /**
     * Return the first task that should be performed in the given course.
     *
     * @param course
     * @return a Task that should be completed first.
     * @throws IllegalArgException see the message text.
     */
    private Task getFirstTask(Course course) throws IllegalArgException {
        switch (course.getPrimaryPedagogy()) {
            case STUDENT_CHOICE:
                return null; // ToDo

            case FIXED_SEQUENCE:
                Unit unit = course.findUnitBySequenceId(0);

                if (unit == null) {
                    throw new IllegalArgException("Unit 0 not found in course: " + course.getId());
                }

                Task task = unit.findTaskBySequence(0);

                if (task == null) {
                    throw new IllegalArgException("Task 0 not found in Unit 0 of course: " + course.getId());
                }

                return task;

            case MASTERY_LEARNING:
                return null; // ToDo

            case MICROADAPTATION:
                return null; // ToDo

            default:
                throw new IllegalArgException("Unknwon task selection in course: " + course.getId());
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
        Random rnd = new Random();

        BitOpExample example = gson.fromJson(jsonData, BitOpExample.class);

        int size = example.getPreSize();

        Account account = session.getAccount();

        if (size == 0) {
            // ToDo: The tutor should generate the string length and timeout
            // based on the the current student model.

            size = rnd.nextInt(MAX_ASCII_SIZE - 1) + 1;
            example.setTimeOut(600);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(String.valueOf(rnd.nextBoolean() ? 0 : 1));
        }

        example.setPreSize(size);
        example.setOperand1(builder.toString());
        example.setOperand2("");

        builder.append("1");

        example.setResult(builder.toString());
        example.setPostSize(size + 1);

        BitOpStep subStep = new BitOpStep();
        subStep.setExample(example);
        subStep.setMultiStep(false);

        Hint hint = new Hint();
        hint.setSequenceId(0);
        hint.setText("Add one bit with a value to the given bits.");

        Step step = new Step(1, 0, StepSubType.ADD_ONE_BIT);
        step.setCurrentHintIndex(0);
        step.addHint(hint);
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
        task.setDescription("Add one bit to the given bit string");
        task.addStep(step);

        // ToDo: Add the task to the session and update it.
        TutorReply reply = new TutorReply(":Success");
        reply.setData(gson.toJson(task));

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

        Random rnd = new Random();

        BitOpExample example = gson.fromJson(jsonData, BitOpExample.class);

        int size = example.getPreSize();

        if (size == 0) {
            // ToDo: The tutor should generate the string length and timeout
            // based on the the current student model.
            size = rnd.nextInt(MAX_BITS_SIZE - 1) + 1;
            example.setTimeOut(600);

        } else if (size > MAX_BITS_SIZE) {
            // The student is requesting practice for a specific string length.
            size = MAX_ASCII_SIZE;
            example.setTimeOut(0);
        }

        example.generatedRandomOperands(size);

        //example.setPreSize(size);
        //example.setPostSize(size);
        //int maxOperandVal = (int) Math.pow(2.0d, size) - 1; // e.g., 2^8 - 1 = 255
        // int operand1 = rnd.nextInt((maxOperandVal - 1) + 1);
        //example.setOperand1Val(operand1);
        //int operand2 = rnd.nextInt((maxOperandVal - 1) + 1);
        //example.setOperand2Val(operand2);
        //int xor = operand1 ^ operand2;
        int xor = (int) example.getOperand1Val() ^ (int) example.getOperand2Val();
        example.setResultVal(xor);

        /*
        builder.setLength(0); // clear the builder
        for (int i = 0; i < size; i++) {
            char char1 = operand1.charAt(i);
            char char2 = operand2.charAt(i);
            if (((char1 == '0') && (char2 == '0')) || ((char1 == '1') && (char2 == '1'))) {
                builder.append('0');
            } else {
                builder.append('1');
            }
        }
        
        example.setResult(builder.toString());
         */
        BitOpStep subStep = new BitOpStep();
        subStep.setExample(example);
        //ToDo: multistep should be determined by the student model.
        subStep.setMultiStep(rnd.nextBoolean());

        Step step = new Step(1, 0, StepSubType.XOR_BITS);
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
        task.setDescription("Xor the bits in the two operands");
        task.addStep(step);

        // ToDo: Add the task to the session and update it.
        TutorReply reply = new TutorReply(":Success");
        reply.setData(gson.toJson(task));

        return reply;
    }

    /**
     * Handles client requests for a new add bits example.
     *
     * Generates two operands with pre-size n bits. These are added together
     * using modulo-preSize arithmetic. For example, with preSize 8 bits:</br>
     * Operand1: 11100111:231 </br>
     * Operand2: 10101111:175 </br>
     * Intermediate: 110010110:406 </br>
     * Result: 10010110:150 (e.g., 406 % 256)
     *
     * @param session the current tutoring session.
     * @param jsonData a BitOpExample encoded object
     * @return a TutorReply
     */
    private TutorReply newAddBitsExample(TutoringSession session, String jsonData) {
        Random rnd = new Random();

        BitOpExample example = gson.fromJson(jsonData, BitOpExample.class);

        int size = example.getPreSize(); // number of bits

        if (size == 0) {
            // ToDo: The tutor should generate the string length and timeout
            // based on the the current student model.
            size = rnd.nextInt(MAX_BITS_SIZE - 1) + 1;
            example.setTimeOut(600);

        } else if (size > MAX_BITS_SIZE) {
            // The student is requesting practice for a specific string length.
            size = MAX_BITS_SIZE;
            example.setTimeOut(0);
        }

        example.setPreSize(size);

        int maxOperandVal = (int) Math.pow(2.0d, size) - 1; // e.g., 2^8 - 1 = 255

        long operand1 = rnd.nextLong((maxOperandVal - 1) + 1);
        example.setOperand1Val(operand1);

        long operand2 = rnd.nextLong((maxOperandVal - 1) + 1);
        example.setOperand2Val(operand2);

        // This is Mod size arithmetic
        example.setPostSize(size);
        long result = ((long) operand1) + ((long) operand2);

        if (result > maxOperandVal) {
            result = result % (maxOperandVal + 1);
        }

        example.setResultVal(result);

        BitOpStep subStep = new BitOpStep();
        subStep.setExample(example);
        //ToDo: multistep should be determined by the student model.
        subStep.setMultiStep(rnd.nextBoolean());

        Step step = new Step(1, 0, StepSubType.ADD_BITS);
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
        task.setDescription("Xor the bits in the two operands");
        task.addStep(step);

        // ToDo: Add the task to the session and update it.
        TutorReply reply = new TutorReply(":Success");
        reply.setData(gson.toJson(task));

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
