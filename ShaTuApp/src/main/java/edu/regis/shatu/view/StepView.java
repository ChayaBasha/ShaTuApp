/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibted.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.view;

import edu.regis.shatu.err.IllegalArgException;
import edu.regis.shatu.view.act.NewExampleAction;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A view displaying a tutoring session.
 * 
 * @author rickb
 */
public class StepView extends JPanel {       
    /**
     * The name of the card (child view) currently displayed in this view.
     */
    private StepSelection selectedPanel;
    
    
    
    /**
     * The child ASCII child view (card) that can be displayed in this view 
     */
    private EncodeView encodeView;
    
    private CompressionCanvasView compressionView;
    
    private PrepareScheduleView prepareScheduleView;
    
    private Add1View add1View;
    
    private PadView padView;
    
    private RotateView rotateView;
    
    private InitVarView initVarView;

    private ShiftRightView shiftRightView;
    
    private ExclusiveOrView exclusiveOrView;

    private AddTwoBitView addTwoBitView;
    
    private MajFunction majFunction;
    
    private ShaZero shaZero;
    
    private ShaOne shaOne;
    
    private ChoiceFunctionView choiceFunctionView;
    
    private StepCompletionReplyView stepReplyView;

    private AddMessageLengthView addMessageLengthView;
    
    /**
     * Initialize and layout the child components (cards) displayed in this view.
     */
    public StepView() {
        GuiController.instance().setStepView(this);
        
        setLayout(new CardLayout());
        
        initializeComponents();
        initializeLayout();
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        selectPanel(StepSelection.ENCODE);   
    }

    
    /**
     * Display the child view with the given name. Request a task from the 
     * tutor for views that display a problem to be solved for the student
     * 
     * @param name a StepSelection
     */
    public void selectPanel(StepSelection name){
        CardLayout cl = (CardLayout) getLayout();
       
        cl.show(this, name.toString());
        
        selectedPanel = name;
        
        //Unfinished switch statement for views that need to get a task from 
        //the tutor when selected
        switch(selectedPanel){
           case ROTATE_BITS:
              NewExampleAction.instance();
           default:
        }
    }
    
    
    /**
     * Method invoked when a new task is requested of the tutor. If the current
     * selected panel is a view that can make a request to the tutor, return 
     * the view. Otherwise, throws an Illegal Argument Exception
     * 
     * @return a UserRequestView object 
     * @throws IllegalArgException when the selected panel is not part of the 
     * UserRequestView Class
     */
    public UserRequestView getUserRequestView() throws IllegalArgException{
       switch(selectedPanel){
          case ROTATE_BITS:
             return rotateView;
          default:
             String msg = "Illegal Selected Panel " + selectedPanel;
             throw new IllegalArgException(msg);
       }
    }
       
    
    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() { 
        encodeView = new EncodeView();
        prepareScheduleView = new PrepareScheduleView();
        compressionView = new CompressionCanvasView();
        rotateView = new RotateView();
        shiftRightView = new ShiftRightView();
        exclusiveOrView = new ExclusiveOrView();
        addTwoBitView = new AddTwoBitView();
        majFunction = new MajFunction();
        shaZero = new ShaZero();
        shaOne = new ShaOne();
        add1View = new Add1View();
        padView = new PadView();
        rotateView = new RotateView();
        initVarView = new InitVarView();
        exclusiveOrView = new ExclusiveOrView();
        choiceFunctionView = new ChoiceFunctionView();
        stepReplyView = new StepCompletionReplyView();
        addMessageLengthView = new AddMessageLengthView();
    }
    
    /**
     * Layout the child components in this view
     */
    private void initializeLayout() {
        add(encodeView, StepSelection.ENCODE.toString());
        add(prepareScheduleView, StepSelection.PREPARE.toString());
        add(compressionView, StepSelection.COMPRESS.toString());
        add(shiftRightView, StepSelection.SHIFT_RIGHT.toString());
        add(exclusiveOrView, StepSelection.XOR.toString());
        add(addTwoBitView, StepSelection.ADD_TWO_BIT.toString());
        add(majFunction, StepSelection.MAJ_FUNCTION.toString());
        add(shaZero, StepSelection.SHA_ZERO.toString());
        add(shaOne, StepSelection.SHA_ONE.toString());
        add(add1View, StepSelection.ADD1.toString());
        add(padView, StepSelection.PAD.toString());
        add(rotateView, StepSelection.ROTATE_BITS.toString());
        add(initVarView, StepSelection.INIT_VARS.toString());
        add(exclusiveOrView, StepSelection.XOR.toString());
        add(choiceFunctionView, StepSelection.CHOICE_FUNCTION.toString());
        add(addMessageLengthView, StepSelection.LENGTH.toString());
        
        add(stepReplyView, StepSelection.STEP_REPLY.toString());
    }
}
