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

import edu.regis.shatu.model.Model;
import edu.regis.shatu.model.TitledModel;
import java.util.ArrayList;

/**
 * A multi-minute activity that can be skipped or interchanged with other tasks,
 * whose steps the student is expected to perform.
 * 
 * Tasks are based on tutoring behaviors in (VanLehn, 2006).
 * 
 * As a Task appears within actions/expectations created by an agent,
 * all fields are final so that other malicious agents cannot change them.
 * 
 * @author rickb
 */
public class Task extends TitledModel {
    /**
     * Indicates the type of task the student trying to complete.
     */
    private TaskKind type = TaskKind.PROBLEM;
    
    /**
     * Indicates the student's overall progress on this task.
     * (For IN_PROGRESS tasks, the student model has the current step.)
     */
    //private TaskState state = TaskState.PENDING;
    
    /**
     * The sequence in which this task is performed in its problem.
     */
    private int sequenceId;
    
    /**
     * The current step (in index into steps).
     */
    private int currentStepIndex = 0;
    
    /**
     * The steps that must be completed in this task.
     */
    private ArrayList<Step> steps;
    
    /**
     * Convenience reference to the Problem to which this task belongs
     */
    private Problem problem;
    
    /**
     * ToDo: the tasks already completed in this task???
     */
    private TaskState state;
  public Task() {
        this(Model.DEFAULT_ID);
  }
  
    /**
     * Instantiate this task with the given id.
     * 
     * @param sequenceId
     * @param steps
     */
    public Task(int Id) {
        super(Id);
        
        this.steps = new ArrayList<>();
        
        state = new TaskState();
    }
    
    public TaskKind getType() {
        return type;
    }

    public void setType(TaskKind type) {
        this.type = type;
    }
    
    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    } 

    public ArrayList<Step> getSteps() {
        return steps;
    }
    public void setSteps(ArrayList<Step> steps) {
       this.steps = steps;
    }
    public Step getStep(int index) {
        return steps.get(index);
    }
    
    public Step lastStep() {
        return steps.get(steps.size() - 1);
    }
    
    public Step currentStep() {        
        for (Step step : steps)
            if (step.getSequenceId() == state.getCurrentStep())
                return step;

        return null;
    }
    
    /**
     * Locally update the task state to note that the given step has been 
     * performed by the student (this doesn't notify the tutor).
     * 
     * @param step the Step that was completed by the student.
     */
    public void completedStep(Step step) {
        StepCompletion completion = new StepCompletion();
        completion.setStepId(step.getId());
        
        step.setIsCompleted(true);
        
        state.addStepCompletion(completion);
    }
    
    /**
     * Return whether this task is completed.
     * 
     * @return true if all of the steps in this task have been completed, 
     *         otherwise false
     */
    public boolean isTaskCompleted() {
        for (Step step : steps)
            if (!step.isCompleted())
                return false;
        
        return true;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }  

    public int getSequenceId() {
        return sequenceId;
    }
    
    public Step getCurrentStep() {
        return steps.get(currentStepIndex);
    }

    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    public void setCurrentStepIndex(int currentStepIndex) {
        this.currentStepIndex = currentStepIndex;
    }
    
    
}

