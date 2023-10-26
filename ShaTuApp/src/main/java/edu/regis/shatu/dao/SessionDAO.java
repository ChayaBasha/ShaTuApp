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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.TutoringSession;
import edu.regis.shatu.svc.SessionSvc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        Gson gson = new Gson();
       
        String userId = session.getAccount().getUserId();
        String fileName = "src/main/java/resources/Data/Session_" + userId.replace('@', '_').replace('.', '_') + ".json";
        
        File file = new File(fileName);
        
        File newFile = new File(file.getAbsolutePath());

        try {
            newFile.createNewFile();
      
            Path path = Paths.get(fileName);
        
            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                gson.toJson(session, writer);
                
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
     Gson gson = new Gson();
       
        String fileName = "src/main/java/resources/Data/Session_" + userId.replace('@', '_').replace('.', '_') + ".json";
        
        Path path = Paths.get(fileName);
     
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonObject jsonObj = JsonParser.parseReader(reader).getAsJsonObject();
            
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

