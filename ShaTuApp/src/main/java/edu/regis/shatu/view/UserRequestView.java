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
package edu.regis.shatu.view;

import edu.regis.shatu.model.Task;
import edu.regis.shatu.model.aol.NewExampleRequest;

/**
 *
 * @author Oskar Thiede
 */
public abstract class UserRequestView extends GPanel{
   
   protected Task model;
   
   
   public abstract NewExampleRequest newRequest();
   
   public abstract void updateView();
   
   public void setModel(Task task){
       System.out.println("UserRequest.setModel");
      this.model = task;
      
      updateView();
       System.out.println("after update view in set model");
   }
   
}
