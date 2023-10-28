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
package edu.regis.shatu.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import edu.regis.shatu.ShaTuApp;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.model.aol.Step;
import edu.regis.shatu.model.aol.Task;
import edu.regis.shatu.svc.SessionSvc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * ToDo: Set this up as a socket server that receives requests from the Tutor
 * 
 * @author rickb
 */
public class SessionDAO implements SessionSvc {
    /**
     * {@inheritDoc}
     */
    @Override
    public void create(TutoringSession session) throws NonRecoverableException {
       //Gson gson = new Gson();
      // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson = new GsonBuilder()
             //  .registerTypeAdapterFactory(ShaTuApp.typeAdapterFactory())
              .setPrettyPrinting()
               .create();
       
        String userId = session.getAccount().getUserId();
        String fileName = "src/main/java/resources/Data/Session_" + userId.replace('@', '_').replace('.', '_') + ".json";
        
        File file = new File(fileName);
        
        File newFile = new File(file.getAbsolutePath());

        try {
            newFile.createNewFile();
      
            Path path = Paths.get(fileName);
        
            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                //gson.toJson(session, writer);
                String jsonStr = gson.toJson(session);
                writer.write(jsonStr);
                writer.flush();
                
            } catch (IOException ex) {
                throw new NonRecoverableException("Create Acct Writer Error", ex);
            }
        }   catch (IOException ex) {
            throw new NonRecoverableException("Create Acct File Error", ex);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TutoringSession findById(String userId) throws ObjNotFoundException, NonRecoverableException {
        Gson gson = new GsonBuilder()
              // .registerTypeAdapterFactory(ShaTuApp.typeAdapterFactory())
                .setPrettyPrinting()
               .create();
       
        String fileName = "src/main/java/resources/Data/Session_" + userId.replace('@', '_').replace('.', '_') + ".json";
        
        Path path = Paths.get(fileName);
     
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonObject jsonObj = JsonParser.parseReader(reader).getAsJsonObject();
            
            /*
            // Debug
            System.out.println("Session.findById");
            Set<String> keys = jsonObj.keySet();
            for (String key : keys) {
                System.out.println("Json member: " + key + " : " + jsonObj.get(key));
            }
            
            JsonObject taskEl = (JsonObject) jsonObj.get("task");
            System.out.println("Json Task El: " + taskEl);
            Task taskPrime = gson.fromJson(taskEl, Task.class);
            System.out.println("taskPRime curre: " + taskPrime.getCurrentStep());
            
            JsonElement stepsArrStr = taskEl.get("steps");
            System.out.println("Json stpews arr str: " + stepsArrStr);
            
            JsonArray arr = taskEl.getAsJsonArray("steps");
            System.out.println("Json array: " + arr);
            
            JsonElement stepEl = arr.get(0);
            System.out.println("Step El: " + stepEl);
            
           // TypeToken token = new TypeToken<Step>() {};
           // Step astep = gson.fromJson(stepEl, token.getType());
           
           // THis works , but is using the subclass, which defeates the idea
           // of the adapter.
            Step aStep = gson.fromJson(stepEl, CompleteStepStep.class);
            System.out.println("aStep: " + aStep.getMyTypeName());
            
            
            TutoringSession session = gson.fromJson(jsonObj, TutoringSession.class);
            System.out.println("Session in findBy step name: " + session.getTask().getCurrentStep().getMyTypeName());
            
            ArrayList<Step> steps = session.getTask().getSteps();
            for (Step step : steps) {
                System.out.println("StepId: " + step.getSequenceId());
                System.out.println("Step: " + step.getMyTypeName());
            }
            
            return session;
            
            
*/
            return gson.fromJson(jsonObj, TutoringSession.class);

        } catch (FileNotFoundException ex) {
            // This not necessarily an error since the user may have a typo
            throw new ObjNotFoundException(String.valueOf(userId));
        } catch (IOException ex) {
            String errMsg = "find user: " + userId;
            throw new NonRecoverableException(errMsg, ex);
        }
    }
    
}

