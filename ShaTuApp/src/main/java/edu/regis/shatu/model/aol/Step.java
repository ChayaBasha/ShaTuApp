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
package edu.regis.shatu.model.aol;

import edu.regis.shatu.model.TitledModel;
import java.util.ArrayList;

/**
 * A user interface gesture/event performed by a student, as part of a Task.
 *
 * Steps in tasks are based on tutoring behaviors in (VanLehn, 2006).
 *
 * As a Step appears within actions/expectations created by an agent, all fields
 * are final so that other malicious agents cannot change them.
 *
 * @author rickb
 */
public class Step extends TitledModel {

   private StepSubType subType;
   /**
    * Which step this is in the parent task.
    */
   protected int sequenceId = 1;

   /**
    * The hint currently available to the student (index into hints).
    */
   protected int currentHintIndex = 0;

   /**
    * The hints, if any, associated with this step (in order to be given).
    */
   protected final ArrayList<Hint> hints;

   /**
    * The amount of time the student can take on this step before the GUI
    * prompts the student concerning their inaction.
    */
   protected final Timeout timeout;

   /**
    * If true, the GUI immediately notifies the tutor when the student performs
    * this step.
    */
   protected boolean notifyTutor;

   /**
    * True, if the student has completed this step, otherwise false.
    *
    * Note, if this is true and notifyTutor is true, the tutor has been notified
    * that the student completed this step. Otherwise, the tutor will be
    * notified when the student completes the parent task associated with this
    * step.
    */
   protected boolean isCompleted;

   private String data;

   /**
    * Instantiate this step with the given information.
    *
    * @param id
    * @param sequenceId
    * @param subType
    */
   public Step(int id, int sequenceId, StepSubType subType) {
      super(id);

      this.sequenceId = sequenceId;

      timeout = null;

      hints = new ArrayList<>();

      isCompleted = false;

      this.subType = subType;
   }

   public StepSubType getSubType() {
      return subType;
   }

   public void setSubType(StepSubType subType) {
      this.subType = subType;
   }

   public void addHint(Hint hint) {
      hints.add(hint);
   }

   public ArrayList<Hint> getHints() {
      return hints;
   }

   public Hint findHintById(int id) {
      for (int i = 0; i < hints.size(); i++) {
         if (hints.get(i).getId() == id) {
            return hints.get(i);
         }
      }

      return null;
   }

   public int getSequenceId() {
      return sequenceId;
   }

   public Timeout getTimeout() {
      return timeout;
   }

   public Hint getCurrentHint() {
      return hints.get(currentHintIndex);
   }

   public int getCurrentHintIndex() {
      return currentHintIndex;
   }

   public void setCurrentHintIndex(int currentHint) {
      this.currentHintIndex = currentHint;
   }

   public boolean isNotifyTutor() {
      return notifyTutor;
   }

   public void setNotifyTutor(boolean notifyTutor) {
      this.notifyTutor = notifyTutor;
   }

   public boolean isCompleted() {
      return isCompleted;
   }

   public void setIsCompleted(boolean isCompleted) {
      this.isCompleted = isCompleted;
   }

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }

}
