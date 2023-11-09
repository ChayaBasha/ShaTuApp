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
import edu.regis.shatu.err.IllegalArgException;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.Account;
import edu.regis.shatu.model.User;
import edu.regis.shatu.svc.UserSvc;
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
 * A Data Access Object implementing {@link UserSvc} behaviors.
 *
 * @author rickb
 */
public class UserDAO implements UserSvc {
    /**
     * Initialize this DAO via the parent constructor.
     */
    public UserDAO() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Account acct) throws IllegalArgException, NonRecoverableException {      
        Gson gson = new Gson();
       
        String userId = acct.getUserId();
        String fileName = "src/main/java/resources/Data/User_" + userId.replace('@', '_').replace('.', '_') + ".json";
        
        File file = new File(fileName);

        //System.out.println("Abs: " + file.getAbsolutePath());
        
        File newFile = new File(file.getAbsolutePath());

        try {
            newFile.createNewFile();
      
            Path path = Paths.get(fileName);
        
            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                gson.toJson(acct, writer);
                

                
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
    public void delete(String userId) throws NonRecoverableException {
       throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(String userId) throws ObjNotFoundException, NonRecoverableException {
        Gson gson = new Gson();
       
        String fileName = "src/main/java/resources/Data/User_" + userId.replace('@', '_').replace('.', '_') + ".json";
        
        Path path = Paths.get(fileName);
     
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonObject jsonObj = JsonParser.parseReader(reader).getAsJsonObject();
            
            return gson.fromJson(jsonObj, User.class);

        } catch (FileNotFoundException ex) {
            // This not necessarily an error since the user may have a typo
            throw new ObjNotFoundException(String.valueOf(userId));
        } catch (IOException ex) {
            String errMsg = "find user: " + userId;
            throw new NonRecoverableException(errMsg, ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(User user, String newPassword) throws ObjNotFoundException, IllegalArgException, NonRecoverableException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
